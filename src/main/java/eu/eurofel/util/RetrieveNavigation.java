package eu.eurofel.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import eu.eurofel.Messages;

public final class RetrieveNavigation
{

    private DefaultHttpClient httpclient;

    private HttpGet httpget;

    private StringBuffer completeHtml;

    private StringBuffer extractedDiv;

    private static volatile RetrieveNavigation instance = null;

    private RetrieveNavigation()
    {
    }

    public static void main( String[] args )
        throws IllegalStateException, IOException
    {
        RetrieveNavigation.run();
    }

    public static void run()
        throws IllegalStateException, IOException
    {
        if ( instance == null )
        {
            synchronized ( RetrieveNavigation.class )
            {
                if ( instance == null )
                {
                    instance = new RetrieveNavigation();
                }
            }
        }
        instance.init();
        instance.callURL();
        instance.extractDiv();
        instance.tidyContent();
        instance.writeToFile();
    }

    private void init()
    {
        httpclient = new DefaultHttpClient();
        httpget = new HttpGet( "http://info.umbrellaid.org/index.php" );
        completeHtml = new StringBuffer();
        extractedDiv = new StringBuffer();
    }

    private void callURL()
        throws IllegalStateException, IOException
    {
        if ( httpclient != null && httpget != null )
            System.out.println( "executing request" + httpget.getRequestLine() );
        HttpResponse response = httpclient.execute( httpget );
        HttpEntity entity = response.getEntity();

        System.out.println( "----------------------------------------" );
        System.out.println( response.getStatusLine() );
        if ( entity != null )
        {
            System.out.println( "Response content length: " + entity.getContentLength() );
        }

        if ( entity != null )
        {
            BufferedReader in = new BufferedReader( new InputStreamReader( entity.getContent() ) );
            String line = null;
            while ( ( line = in.readLine() ) != null )
            {
                completeHtml.append( line );
                // System.out.println( line );
            }
        }

        httpclient.getConnectionManager().shutdown();
    }

    private void extractDiv()
    {
        if ( completeHtml != null && completeHtml.length() > 0 )
        {

            String inputStr = completeHtml.toString();
            String patternStr = "<div class=\"TopNav\">(.*?)</div>";
            // Compile and use regular expression

            Pattern pattern = Pattern.compile( patternStr );
            Matcher matcher = pattern.matcher( inputStr );
            extractedDiv.append( "<div class=\"TopNav\">" );
            while ( matcher.find() )
            {
                extractedDiv.append( matcher.group( 1 ) );
            }
            extractedDiv.append( "</div>" );
        }
    }

    private void tidyContent()
    {
        if ( extractedDiv != null && extractedDiv.length() > 0 )
        {
            String content = extractedDiv.toString().replaceAll( "index.php", "http://info.umbrellaid.org/index.php" );
            extractedDiv = new StringBuffer( content );
        }
    }

    private void writeToFile()
        throws IOException
    {
        if ( extractedDiv != null && extractedDiv.length() > 0 )
        {
            File file = new File( Messages.getString( "eaa.path.file" ) + "/navTop.html" );

            // if file doesnt exists, then create it
            if ( !file.exists() )
            {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter( file.getAbsoluteFile() );
            BufferedWriter bw = new BufferedWriter( fw );
            bw.write( extractedDiv.toString() );
            bw.close();

            System.out.println();
        }
    }
}
