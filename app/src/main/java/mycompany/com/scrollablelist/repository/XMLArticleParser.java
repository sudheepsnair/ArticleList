package mycompany.com.scrollablelist.repository;

import android.util.Log;
import mycompany.com.scrollablelist.model.Article;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class XMLArticleParser extends DefaultHandler {

    private final String URL_ARTICLE = "https://www.personalcapital.com/blog/feed/?cat=3,891,890,68,284";
    private String TAG = "XMLArticleParser";
    private Article mArticle;
    private ArrayList<Article> mArticleArrayList = new ArrayList<>();

    boolean mBooleanTitle;
    boolean mBooleanDescription;
    boolean mBooleanLink;

    public ArrayList<Article> getArticlesList() {
        return mArticleArrayList;
    }

    public void getAndParseArticle() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser mSaxParser = factory.newSAXParser();
            XMLReader mXmlReader = mSaxParser.getXMLReader();
            mXmlReader.setContentHandler(this);
            InputStream mInputStream = new URL(URL_ARTICLE).openStream();
            mXmlReader.parse(new InputSource(mInputStream));
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (mBooleanTitle && mArticle != null) {
            mArticle.setTitle(new String(ch, start, length));
            mBooleanTitle = false;
        }

        if (mBooleanLink && mArticle != null) {
            mArticle.setLink(new String(ch, start, length));
            mBooleanLink = false;
        }

        if (mBooleanDescription && mArticle != null) {
            mArticle.setDescription(new String(ch, start, length));
            mBooleanDescription = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (localName.equalsIgnoreCase("item") && mArticle != null)
            mArticleArrayList.add(mArticle);
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (localName.equals("item"))
            mArticle = new Article();
        else if (localName.equalsIgnoreCase("title") && mArticle != null)
            mBooleanTitle = true;
        else if (localName.equalsIgnoreCase("link") && mArticle != null)
            mBooleanLink = true;

        else if (localName.equalsIgnoreCase("description") && mArticle != null)
            mBooleanDescription = true;
        else if (localName.equalsIgnoreCase("enclosure") && mArticle != null) {
            mArticle.setImageURL(attributes.getValue("url"));
        }
    }
}
