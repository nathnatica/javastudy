package test.xml;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


public class XMLCreatorSAX {

	public static <T> void turnPOJOIntoXML(T obj, OutputStream out) {
		try {
			ContentHandler hd = XMLCreatorSAX.getContentHandler(out);
			XMLCreatorSAX.makeDocument(hd, obj);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ContentHandler getContentHandler(OutputStream out) throws IOException {
		OutputFormat of = new OutputFormat("XML", "UTF-8", true);
		of.setIndent(1);
		of.setIndenting(true);
		//of.setDoctype(null, "userd.dtd");
		XMLSerializer serializer = new XMLSerializer(out, of);
		return serializer.asContentHandler();
	}

	private static <T> void makeDocument(ContentHandler hd, T obj) throws SAXException, IllegalArgumentException, IOException, IllegalAccessException, ClassNotFoundException {
		hd.startDocument();
		makeAttrAndChildren(hd, obj);
		hd.endDocument();
	}

    private static <T> void makeAttrAndChildren(ContentHandler hd, T element) throws IOException, SAXException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
    	makeAttributes(hd, element);
    	makeChildren(hd, element);
    }





    private static <T> void makeAttributes(ContentHandler hd, T obj) throws SAXException, IllegalArgumentException, IllegalAccessException {
    	AttributesImpl atts = new AttributesImpl();

    	Field[] fieldList = obj.getClass().getDeclaredFields();
    	for (Field field : fieldList) {
    		makeAttribute(field, obj, atts);
    	}

    	hd.startElement("", "", XMLCreatorSAX.getElementName(obj), atts);
    }

    private static <T> String getElementName(T obj) {
    	XMLElement element = obj.getClass().getAnnotation(XMLElement.class);
    	return element.value();
    }

    private static <T> void makeAttribute (Field field, T obj, AttributesImpl atts) throws IllegalArgumentException, IllegalAccessException {
		XMLAttribute xmlAttribute = field.getAnnotation(XMLAttribute.class);
		if (xmlAttribute != null) {
			if (StringUtils.isNotEmpty((String)field.get(obj))) {
				atts.addAttribute("", "", xmlAttribute.value(), "CDATA", (String)field.get(obj));
			}
		}
    }






    private static <T> void makeChildren(ContentHandler hd, T obj) throws SAXException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, IOException {
    	Field[] fieldList = obj.getClass().getFields();
    	for (Field field : fieldList) {
    		makeChild(hd, obj, field);
    	}
    	hd.endElement("", "", obj.getClass().getAnnotation(XMLElement.class).value());
    }

    @SuppressWarnings("unchecked")
    private static <T> void makeChild (ContentHandler hd, T obj, Field field) throws IllegalArgumentException, IllegalAccessException, IOException, SAXException, ClassNotFoundException {
		if (hasMoreChild(field)) {
			List<Object> list = (ArrayList<Object>)field.get(obj);
			if (list != null) {
				for (Object child : list) {
					makeAttrAndChildren(hd, child);
				}
			}
		} else if (hasOnlyTextValue(field)) {
			if (field.get(obj) != null) {
				hd.characters(((String)field.get(obj)).toCharArray(), 0, ((String)field.get(obj)).length());
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
