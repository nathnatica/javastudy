package test.xml;
import java.util.List;

import test.xml.vo.Leaf;
import test.xml.vo.Sub;

    @XMLElement("Super") public class Super extends XMLObject {
    	@XMLAttribute("attr1") public String attr1;
    	@XMLAttribute("attr2") public String attr2;
    	@XMLElement("Sub") public List<Sub> sub;
    	@XMLElement("Leaf") public List<Leaf> leaf;
    }
