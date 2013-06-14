package test.xml;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import test.xml.vo.Leaf;
import test.xml.vo.Sub;

public class XMLCreatorDOMTest {
	ByteArrayOutputStream byteArrayOutputStream = null;

	@Before
	public void before () {
		byteArrayOutputStream = new ByteArrayOutputStream();
	}

	@Test
	public void leafCreateTest() throws UnsupportedEncodingException {
		Leaf l = new Leaf();

		String expected = "<Leaf/>";

		XMLCreatorDOM.turnPOJOIntoXML(l, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void subCreateTest() throws UnsupportedEncodingException {
		Sub s = new Sub();

		String expected = "<Sub/>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void superCreateTest() throws UnsupportedEncodingException {
		Super s = new Super();

		String expected = "<Super/>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void leafWithValueTest() throws UnsupportedEncodingException {
		Leaf l = new Leaf();
		l.textValue = "value";

		String expected = "<Leaf>value</Leaf>";

		XMLCreatorDOM.turnPOJOIntoXML(l, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void subWithAttrTest() throws UnsupportedEncodingException {
		Sub s = new Sub();

		s.subAttr1 = "学校";

		String expected = "<Sub subAttr1=\"学校\"/>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void superWithAttrTest() throws UnsupportedEncodingException {
		Super s = new Super();

		s.attr1 = "学校";
		s.attr2 = "attr2";

		String expected = "<Super attr1=\"学校\" attr2=\"attr2\"/>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void subWithLeafTest() throws UnsupportedEncodingException {
		Sub s = new Sub();

		s.leaf = new ArrayList<Leaf>();
		Leaf l = new Leaf();
		s.leaf.add(l);

		String expected = "<Sub><Leaf/></Sub>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void subWithLeafValueTest() throws UnsupportedEncodingException {
		Sub s = new Sub();

		s.leaf = new ArrayList<Leaf>();
		Leaf l = new Leaf();
		l.textValue = "カナ";
		s.leaf.add(l);

		String expected = "<Sub><Leaf>カナ</Leaf></Sub>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void subWithAttrAndLeafValueTest() throws UnsupportedEncodingException {
		Sub s = new Sub();
		s.subAttr1 = "属性";

		s.leaf = new ArrayList<Leaf>();
		Leaf l = new Leaf();
		l.textValue = "カナ";
		s.leaf.add(l);

		String expected = "<Sub subAttr1=\"属性\"><Leaf>カナ</Leaf></Sub>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void subWith2LeafTest() throws UnsupportedEncodingException {
		Sub s = new Sub();
		s.subAttr1 = "属性";

		s.leaf = new ArrayList<Leaf>();
		Leaf l = new Leaf();
		l.textValue = "カナ";
		s.leaf.add(l);
		Leaf l2 = new Leaf();
		s.leaf.add(l2);

		String expected = "<Sub subAttr1=\"属性\"><Leaf>カナ</Leaf><Leaf/></Sub>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void superWith2SubTest() throws UnsupportedEncodingException {
		Super s = new Super();
		s.sub = new ArrayList<Sub>();

		Sub sub = new Sub();
		sub.subAttr1 = "attr";

		s.sub.add(sub);

		Sub sub2 = new Sub();
		s.sub.add(sub2);

		String expected = "<Super><Sub subAttr1=\"attr\"/><Sub/></Super>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void superWithSubAndLeafTest() throws UnsupportedEncodingException {
		Super s = new Super();
		s.sub = new ArrayList<Sub>();

		Sub sub = new Sub();
		sub.subAttr1 = "attr";

		s.sub.add(sub);

		s.leaf = new ArrayList<Leaf>();
		Leaf l = new Leaf();
		s.leaf.add(l);

		String expected = "<Super><Sub subAttr1=\"attr\"/><Leaf/></Super>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void superWithSub2AndLeaf2Test() throws UnsupportedEncodingException {
		Super s = new Super();
		s.sub = new ArrayList<Sub>();

		Sub sub = new Sub();
		sub.subAttr1 = "attr";
		s.sub.add(sub);

		Sub sub2 = new Sub();
		sub2.textValue = "sub2value";
		s.sub.add(sub2);

		s.leaf = new ArrayList<Leaf>();
		Leaf l = new Leaf();
		s.leaf.add(l);
		Leaf l2 = new Leaf();
		l2.textValue = "leafvalue";
		s.leaf.add(l2);

		String expected =
				"<Super>" +
					"<Sub subAttr1=\"attr\"/><Sub>sub2value</Sub>" +
					"<Leaf/><Leaf>leafvalue</Leaf>" +
				"</Super>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}

	@Test
	public void superTest() throws UnsupportedEncodingException {
		Super s = new Super();
		s.sub = new ArrayList<Sub>();

		Sub sub = new Sub();
		sub.subAttr1 = "attr";
		s.sub.add(sub);

		Sub sub2 = new Sub();
		sub2.textValue = "sub2value";
		s.sub.add(sub2);

		Sub sub3 = new Sub();
		sub3.subAttr1 = "attr";
		s.sub.add(sub3);

		sub3.leaf = new ArrayList<Leaf>();
		Leaf l3 = new Leaf();
		l3.textValue = "value";
		Leaf l4 = new Leaf();
		sub3.leaf.add(l3);
		sub3.leaf.add(l4);


		s.leaf = new ArrayList<Leaf>();
		Leaf l = new Leaf();
		s.leaf.add(l);
		Leaf l2 = new Leaf();
		l2.textValue = "leafvalue";
		s.leaf.add(l2);

		String expected =
				"<Super>" +
					"<Sub subAttr1=\"attr\"/>" +
					"<Sub>sub2value</Sub>" +
					"<Sub subAttr1=\"attr\">" +
						"<Leaf>value</Leaf>" +
						"<Leaf/>" +
					"</Sub>" +
					"<Leaf/><Leaf>leafvalue</Leaf>" +
				"</Super>";

		XMLCreatorDOM.turnPOJOIntoXML(s, byteArrayOutputStream);
		checkResults(expected, byteArrayOutputStream);
	}





	private void checkResults(String input, ByteArrayOutputStream baos) throws UnsupportedEncodingException {
		String output = baos.toString("UTF-8");
		output = output.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		System.out.println(output);

		input = input.replaceAll("\n", "");
		output = output.replaceAll("\n", "");
		output = output.replaceAll("    ", "");

//		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + input;
		Assert.assertEquals(input, output);
	}
}
