
package com.mpit.pristine.utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author najim
 */
public class XMLReader {

    private static final String ResourcePackagePath = "/com/mpit/pristine/resources/";

    public  ArrayList<String> readXml(String xmlFile) throws MalformedURLException, ParserConfigurationException, SAXException, IOException {
        String path = ResourcePackagePath + xmlFile;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = getClass().getResourceAsStream(path);
        Document doc = db.parse(is);
        Node rootNode = doc.getDocumentElement();
        NodeList childrenList = rootNode.getChildNodes();

        ArrayList<String> stringCollection = new ArrayList<String>();
        
        for (int i = 0; i < childrenList.getLength(); i++) {
              if(childrenList.item(i).getNodeType() == Node.ELEMENT_NODE){
                  stringCollection.add( childrenList.item(i).getFirstChild().getNodeValue());
                  //System.out.print(childrenList.item(i).getFirstChild().getNodeValue());
                  
        }
        }

        
        return stringCollection;
    }
}
