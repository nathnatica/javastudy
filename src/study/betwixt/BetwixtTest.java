package study.betwixt;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.betwixt.io.BeanReader;
import org.xml.sax.SAXException;

public class BetwixtTest {


	public static void main(String[] args) throws IOException, IntrospectionException, SAXException {

		String filePath = "C:\\workspace\\UT\\src\\test\\betwixt\\mb_flash_judge_info.xml";
		String path = "mbFlashJudgeInfo";

		InfoBean beanObject = tranXmlToBean(filePath, path, InfoBean.class);

		List<String> l = beanObject.getName();
		for (int i=0; i<l.size(); i++) {
			System.out.println(l.get(i));
		}
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T tranXmlToBean(String filePath, String path, Class<T> clazz) throws IOException, IntrospectionException, SAXException {
		FileReader fr = new FileReader(filePath);
		BufferedReader bf = new BufferedReader(fr);

		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = bf.readLine()) != null) {
			sb.append(line).append("\r\n");
		}

		StringReader xmlReader = new StringReader(sb.toString());
		BeanReader beanReader = new BeanReader();

        beanReader.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(false);
        beanReader.getBindingConfiguration().setMapIDs(false);
        beanReader.registerBeanClass(path, clazz);
        T beanObject = (T)beanReader.parse(xmlReader);
        return beanObject;
	}

}
