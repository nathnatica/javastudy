package test.xml;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


public class XMLCreatorDOM {

	public static <T extends XMLObject> void turnPOJOIntoXML(T obj, OutputStream out) {
		try {
			Document dom = XMLCreatorDOM.getDocument();

			Element root = make(dom, obj, null);
			dom.appendChild(root);

			XMLCreatorDOM.serialize(dom, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Document getDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.newDocument();
	}

	private static void serialize(Document dom, OutputStream out) throws IOException {
		OutputFormat format = new OutputFormat(dom);
		format.setIndenting(true);
		//XMLSerializer serializer = new XMLSerializer(response.getOutputStream(), format);
		XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.serialize(dom);
	}

    private static <T extends XMLObject> Element make(Document dom, T obj, Element parent) throws IOException, SAXException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
    	Element eleWithAttributes = makeAttributes(dom, obj);
    	Element eleWithAttAndChild = makeChildren(dom, obj, eleWithAttributes);

    	return XMLCreatorDOM.returnParent(parent, eleWithAttAndChild);
    }

    private static Element returnParent(Element parent, Element eleWithAttAndChild) {
    	if (parent != null) {
    		parent.appendChild(eleWithAttAndChild);
    	} else {
    		parent = eleWithAttAndChild;
    	}

    	return parent;
    }







    private static <T extends XMLObject> Element makeAttributes(Document dom, T obj) throws SAXException, IllegalArgumentException, IllegalAccessException {
    	Element ele = dom.createElement(XMLCreatorDOM.getElementName(obj)); // create element

    	Field[] fieldList = obj.getClass().getFields();
    	for (Field field : fieldList) {
    		XMLCreatorDOM.makeAttribute(field, obj, ele);
    	}
    	return ele;
    }

    private static <T extends XMLObject> String getElementName(T obj) {
    	XMLElement element = obj.getClass().getAnnotation(XMLElement.class);
    	return element.value();
    }

    private static <T extends XMLObject> void makeAttribute (Field field, T obj, Element ele) throws IllegalArgumentException, DOMException, IllegalAccessException {
		XMLAttribute xmlAttribute = field.getAnnotation(XMLAttribute.class);
		if (xmlAttribute != null) {
			if (StringUtils.isNotEmpty((String)field.get(obj))) {
				ele.setAttribute(xmlAttribute.value(), (String)field.get(obj));
			}
		}
    }








    private static <T extends XMLObject> Element makeChildren(Document dom, T obj, Element element) throws SAXException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, IOException {
    	Field[] fieldList = obj.getClass().getFields();
    	for (Field field : fieldList) {
    		XMLCreatorDOM.makeChild(dom, obj, field, element);
    	}
    	return element;
    }

    @SuppressWarnings("unchecked")
    private static <T extends XMLObject> void makeChild (Document dom, T obj, Field field, Element element) throws IllegalArgumentException, IllegalAccessException, IOException, SAXException, ClassNotFoundException {
		if (XMLCreatorDOM.hasMoreChild(field)) {
			List<Object> list = (ArrayList<Object>)field.get(obj);
			if (list != null) {
				for (int i=0; i<list.size(); i++) {
					T child = (T)list.get(i);
					make(dom, child, element);
				}
			}
		} else if (XMLCreatorDOM.hasOnlyTextValue(field)){
			if (field.get(obj) != null) {
				Text textNode =dom.createTextNode(((String)field.get(obj)));
				element.appendChild(textNode);
			}
		}
    }

    private static boolean hasMoreChild (Field field) {
		XMLAttribute xmlAttribute = field.getAnnotation(XMLAttribute.class);
		XMLElement xmlElement = field.getAnnotation(XMLElement.class);
		if (xmlAttribute == null && xmlElement != null) {
			return true;
		} else {
			return false;
		}
    }

    private static boolean hasOnlyTextValue(Field field) {
		XMLAttribute xmlAttribute = field.getAnnotation(XMLAttribute.class);
		XMLElement xmlElement = field.getAnnotation(XMLElement.class);
		if (xmlAttribute == null && xmlElement == null) {
			return true;
		} else {
			return false;
		}
    }
}
