/**
 * 
 */
package boextraction;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author Panagis
 *
 */
public class XMLReader {

	public Document parseXmlDocument(final String xml) {
		Document document;
		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final InputStream xmlText = new ByteArrayInputStream(xml.getBytes("utf-8")); 
			document = builder.parse(xmlText);
			System.out.println("Success in parsing!!");
			return document;
		} catch (final Exception e) {
			// File does not exist
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public String readXmlSQL(final Document doc) {

		String text = "";
		try {
			final Element root = doc.getDocumentElement();
			final NodeList nodeList = root.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				final Node currentNode = nodeList.item(i);
				final NodeList subNodeList = currentNode.getChildNodes();
				for (int j = 0; j < subNodeList.getLength(); j++) {
					final Node currentSubNode = subNodeList.item(j);
					if (currentSubNode.getNodeName().equalsIgnoreCase("property")) {
						final NamedNodeMap map = currentSubNode.getAttributes();
						if (map.getNamedItem("key").getNodeValue().equals("sql")) {
							text = currentSubNode.getTextContent();	
						}
					}
				}
			} 
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	public String readXmlName(final Document doc) {

		String text = "";
		try {
			final Element root = doc.getDocumentElement();
			final NodeList nodeList = root.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				final Node currentNode = nodeList.item(i);
				if (currentNode.getNodeName().equals("name")){
					text = currentNode.getTextContent();
					return text;
				}
			} 
		}
		catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String readXmlConnID(final Document doc) {

		String text = "";
		try {
			final Element root = doc.getDocumentElement();
			final NodeList nodeList = root.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				final Node currentNode = nodeList.item(i);
				if (currentNode.getNodeName().equals("dataSourceId")){
					text = currentNode.getTextContent();
					return text;
				}
			} 
		}
		catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
