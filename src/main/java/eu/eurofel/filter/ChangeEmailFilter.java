package eu.eurofel.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import eu.eurofel.Messages;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.services.EAAService;
import eu.eurofel.util.EAAHash;

public class ChangeEmailFilter
    implements Filter
{

    protected FilterConfig fcon = null;

    
    public void destroy()
    {
        fcon = null;
    }

    
    public void doFilter( ServletRequest req, ServletResponse res, FilterChain fc )
        throws IOException, ServletException
    {

        String uuid = req.getParameter( "uuid" );
        String email = req.getParameter( "email" );
        String checksum = req.getParameter( "checksum" );
        boolean found = false;
//        System.out.println( "Filter UUID:" + uuid );
//        System.out.println( "Filter EMAIL:" + email );
//        System.out.println( "Filter CHECK:" + checksum );
        // cast the ServletRequest to a HttpServletRequest

        HttpServletResponse srvResp = (HttpServletResponse) res;

        if ( uuid != null && email != null && checksum != null )
        {

//            System.out.println( "Load resource..." );
            Resource resource = new UrlResource( "file://" + Messages.getString( "application.context.path" ) );
//            System.out.println( "Load beanfactory..." );
            BeanFactory factory = new XmlBeanFactory( resource );
//            System.out.println( "Load service..." );
            EAAService service = (EAAService) factory.getBean( "eAAService" );
//            System.out.println( "found Eaaservice" );
            try
            {
                EAAAccount acc = new EAAAccount( service.findAccountByHash( uuid ) );
//                System.out.println( "found EaaAccount" );
                String eaakey = acc.getEaakey();

                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime( date );

                int year = calendar.get( Calendar.YEAR );
                int month = calendar.get( Calendar.MONTH );
                int day = calendar.get( Calendar.DAY_OF_MONTH );
                int hour1 = calendar.get( Calendar.HOUR_OF_DAY );
                int hour2 = hour1 - 1;

                // reconstruct checksum
                String checksum1 = EAAHash.getSHA1Hash( email + eaakey + year + month + day + hour1 );
                String checksum2 = EAAHash.getSHA1Hash( email + eaakey + year + month + day + hour2 );
//                System.out.println( "Filter CHECK1:" + checksum1 );

                // do a check if the UUID is right and if yes, activate the
                // account
                if ( checksum1.equals( checksum ) || checksum2.equals( checksum ) )
                {
                    service.changeEmail( acc.getUid(), email );
                    found = true;

                    srvResp.sendRedirect( "/euu/account/emailchanged" );

                }

            }
            catch ( NamingException e )
            {
                found = false;
                e.printStackTrace();
            }
        }
        if ( !found )
        {
            srvResp.sendRedirect( "/euu/account/notactivated" );
        }
    }

    
    public void init( FilterConfig fcon )
        throws ServletException
    {
        this.fcon = fcon;
    }

}
