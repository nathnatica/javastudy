package test.xml;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XMLParser {

	@SuppressWarnings("hiding")
	public static <T extends XMLObject> T turnStringIntoPOJO(String str, Class<T> type) throws ParserConfigurationException, SAXException, IOException, IllegalArgumentException, DOMException, SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
		Element root = XMLParser.getRootElementFromString(str);
		T pojo = XMLParser.turnElementIntoPOJO(root, type);
		return pojo;
	}

    private static Element getRootElementFromString(String str) throws ParserConfigurationException, SAXException, IOException {
    	final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	dbf.setValidating(false);
    	final DocumentBuilder db = dbf.newDocumentBuilder();

    	StringReader reader = new StringReader(str);
    	InputSource is = new InputSource();
    	is.setCharacterStream(reader);
    	Document document = db.parse(is);

    	Element documentElement = document.getDocumentElement();
    	return documentElement;
    }

    @SuppressWarnings("hiding")
	private static <T extends XMLObject> T turnElementIntoPOJO(Node node, Class<T> type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, DOMException, SecurityException, NoSuchFieldException, ClassNotFoundException {
    	T obj = null;
    	obj = type.newInstance();

    	if (isNodeMatchesObject(node, obj)) {

    		fillAttributes(node, obj);
    		fillChildren(node, obj);
    	}

    	return obj;
    }

    @SuppressWarnings("hiding")
	private static <T extends XMLObject> boolean isNodeMatchesObject(Node node, T obj) {
    	String nodeName = node.getNodeName();
    	XMLElement element = (XMLElement)obj.getClass().getAnnotation(XMLElement.class);
    	String elementValue = element.value();

    	if (nodeName.equals(elementValue)) {
    		return true;
    	} else {
    		return false;
    	}
    }

    @SuppressWarnings("hiding")
	private static <T extends XMLObject> void fillAttributes(Node node, T obj) throws IllegalArgumentException, DOMException, IllegalAccessException {
    	if (node.hasAttributes()) {
    		NamedNodeMap attributeMap = node.getAttributes();
    		for (int i=0; i < attributeMap.getLength(); i++) {
    			Node attr = attributeMap.item(i);
    			fillAttribute(attr, obj);
    		}
    	}
    }

    @SuppressWarnings("hiding")
	private static <T extends XMLObject> void fillAttribute(Node attr, T obj) throws IllegalArgumentException, DOMException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			setAttributeData(attr, obj, field);
		}
    }

    @SuppressWarnings("hiding")
	private static <T extends XMLObject> void setAttributeData(Node attr, T obj, Field field) throws IllegalArgumentException, DOMException, IllegalAccessException {
		String attrName = attr.getNodeName();
		XMLAttribute xmlAttribute = field.getAnnotation(XMLAttribute.class);
		if (xmlAttribute != null) {
			if ("".equals(xmlAttribute.value()) && field.getName().equals(attrName)
					|| xmlAttribute.value().equals(attrName)) {
				field.set(obj, attr.getNodeValue());
			}
		}
    }



    @SuppressWarnings("hiding")
	private static <T extends XMLObject> void fillChildren(Node node, T obj) throws IllegalArgumentException, DOMException, IllegalAccessException, SecurityException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
    	if (node.hasChildNodes()) {
    		NodeList childNodeList = node.getChildNodes();
    		for (int i=0; i < childNodeList.getLength(); i++) {
    			Node child = childNodeList.item(i);
    			fillChild(child, obj);
    		}
    	}
    }

    @SuppressWarnings("hiding")
	private static <T extends XMLObject> void fillChild(Node child, T obj) throws IllegalArgumentException, DOMException, SecurityException, IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
		if (child.hasChildNodes()) {
			fillChildWithMoreChild(child, obj);
		} else {
			fillChildWithTextValue(child, obj);
		}
    }

    @SuppressWarnings("hiding")
	private static <T extends XMLObject> void fillChildWithTextValue(Node child, T obj) throws SecurityException, NoSuchFieldException, IllegalArgumentException, DOMException, IllegalAccessException {
    	if (StringUtils.isNotEmpty(child.getTextContent())) {
    		Field field = obj.getClass().getField("textValue");
    		field.set(obj, child.getTextContent());
    	}
    }

    @SuppressWarnings("hiding")
	private static <T extends XMLObject> void fillChildWithMoreChild(Node child, T obj) throws IllegalArgumentException, DOMException, IllegalAccessException, SecurityException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			setChildData(child, obj, field);
		}
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    private static <T extends XMLObject> void setChildData(Node child, T obj, Field field) throws IllegalArgumentException, DOMException, SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
		String childName = child.getNodeName();
		XMLElement xmlElement = field.getAnnotation(XMLElement.class);
		if (xmlElement != null) {
			if ("".equals(xmlElement.value()) && field.getName().equals(childName)
					|| xmlElement.value().equals(childName)) {

				// IMPORTANT
				// parent object's package must equal to child object's package
				childName = obj.getClass().getPackage().getName() + "." + childName; // add package name

				// Recursively call turnElementIntoPOJO() to set child nodes
				Class<T> clazz = (Class<T>) Class.forName(childName);
				Object filledChild = turnElementIntoPOJO(child, clazz);

				checkEmptyList(obj, field);

				((List<Object>)field.get(obj)).add(filledChild);
			}
		}
    }

    @SuppressWarnings({ "unchecked", "hiding" })
	private static <T extends XMLObject> void checkEmptyList(T obj, Field field) throws IllegalArgumentException, IllegalAccessException {
		List <Object> list = (List<Object>)field.get(obj);
		if (list == null) {
			List<Object> newList = new ArrayList<Object>();
			field.set(obj, newList);
		}
    }

}



