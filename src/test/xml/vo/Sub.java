package test.xml.vo;
import java.util.List;

import test.xml.XMLAttribute;
import test.xml.XMLElement;
import test.xml.XMLObject;


@XMLElement("Sub") public class Sub extends XMLObject {
   	@XMLAttribute("subAttr1") public String subAttr1;
   	@XMLElement("Leaf") public List<Leaf> leaf;
}
