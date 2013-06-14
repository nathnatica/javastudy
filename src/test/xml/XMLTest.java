package test.xml;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import test.xml.vo.Leaf;
import test.xml.vo.Sub;

public class XMLTest {
	ByteArrayOutputStream byteArrayOutputStream = null;

	@Before
	public void before () {
		byteArrayOutputStream = new ByteArrayOutputStream();
	}


	@Test
	public void superWithTextValueTest() {

    	String input =
    		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    		"<Super>message1</Super>";

    	tryTest(input, Super.class);
	}

	@Test
	public void subWithTextValueTest() {

    	String input =
    		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    		"<Sub>errorMessage</Sub>";

    	tryTest(input, Sub.class);
	}

	@Test
	public void leafWithTextValueTest() {

    	String input =
    		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    		"<Leaf>errorMessage</Leaf>";

    	tryTest(input, Leaf.class);
	}

	@Test
	public void complexTest() {

    	String input =
    		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    		"<Super attr1=\"attr1\" attr2=\"ëÆê´ÇQ\">" +
    			"<Sub subAttr1=\"attr1\">" +
    				"<Leaf>äwçZ</Leaf>" +
    				"<Leaf>ÉJÉ^ÉJÉi</Leaf>" +
    			"</Sub>" +
    			"<Sub subAttr1=\"äwçZ\">text</Sub>" +
    			"<Sub>3rd</Sub>" +
    			"<Sub subAttr1=\"attr4\">" +
    				"<Leaf>sub4</Leaf>" +
    			"</Sub>" +
    			"<Leaf>child of leaf1</Leaf>" +
    			"<Leaf>child of leaf2</Leaf>" +
    		"</Super>";

    	tryTest(input, Super.class);
	}


	private <T extends XMLObject> void tryTest (String input, Class<T> type) {
    	try {
    		T obj = null;

    		try {
    			obj = XMLParser.turnStringIntoPOJO(input , type);
    		} catch (SAXException e) {
    			System.out.println(e.getMessage());
    		}
			XMLCreatorSAX.turnPOJOIntoXML(obj, this.byteArrayOutputStream);

			checkResults(input, this.byteArrayOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private void checkResults(String input, ByteArrayOutputStream baos) throws UnsupportedEncodingException {
		String output = baos.toString("UTF-8");
		System.out.println(output);

		input = input.replaceAll("\n", "");
		output = output.replaceAll("\n", "");
		output = output.replaceAll("    ", "");

		Assert.assertEquals(input, output);
	}
}
