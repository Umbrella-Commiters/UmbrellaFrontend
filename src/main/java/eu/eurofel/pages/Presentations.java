package eu.eurofel.pages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.xml.namespace.QName;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.parser.ParseException;
import org.apache.abdera.parser.Parser;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;

import eu.eurofel.entities.UserSession;

@Secure
public class Presentations {

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;

	@SuppressWarnings("unused")
	@Property
	private eu.eurofel.entities.Link _link;

	@SuppressWarnings("unused")
	@Property
	// Persist the list to support inplace="true" on the Grid
	@Persist
	private List<eu.eurofel.entities.Link> _links;

	void onActivate() throws NamingException {
		try {
			_links = getCMISLinks();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private List<eu.eurofel.entities.Link> getCMISLinks() throws ParseException, IOException {

		List<eu.eurofel.entities.Link> links = new ArrayList<eu.eurofel.entities.Link>();

		Abdera abdera = new Abdera();
		Parser parser = abdera.getParser();

		URL url = new URL("https://umbrella.psi.ch/alfresco/wcs/cmis/s/workspace:SpacesStore/i/4d833e03-64cd-4304-8e8f-b4dcfc53c275/children?guest=true");
		Document<Feed> doc = parser.parse(url.openStream(), url.toString());
		Feed feed = doc.getRoot();
		for (Entry entry : feed.getEntries()) {
			eu.eurofel.entities.Link link = new eu.eurofel.entities.Link();
			link.setAuthor(entry.getAuthor().getName());
			link.setTitle(entry.getTitle());
			link.setDescription(entry.getSummary());
			link.setLink(entry.getLink("enclosure").getAttributeValue(new QName("href")) + "?guest=true");
			link.setDate(entry.getUpdated());
			links.add(link);
		}
		return links;
	}

}
