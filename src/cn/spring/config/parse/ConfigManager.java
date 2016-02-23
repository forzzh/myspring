package cn.spring.config.parse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.spring.config.Bean;
import cn.spring.config.Property;

/**
 * 读取配置文件
 * @author zhaozhihang
 *
 */
public class ConfigManager {

	public static Map<String, Bean> getConfig(String path) {
		Document doc = getDocument();
		String xpath = "//bean";
		
		Map<String, Bean> map = new HashMap<String, Bean>();
		
		List<Element> list = doc.selectNodes(xpath);
		
		if (list != null) {
			for (Element e : list) {
				Bean bean = new Bean();
				String name = e.attributeValue("name");
				String className = e.attributeValue("class");
				String scope = e.attributeValue("scope");
				
				bean.setName(name);
				bean.setClassName(className);
				if (scope != null) {
					bean.setScope(scope);
				}
				
				//封装子元素
				List<Element> children = e.elements("property");
				
				if (children != null) {
					for (Element child : children) {
						Property perperty = new Property();
						
						String pName = child.attributeValue("name");
						String pvalue = child.attributeValue("value");
						String pRef = child.attributeValue("ref");
						perperty.setName(pName);
						perperty.setValue(pvalue);
						perperty.setRef(pRef);
						bean.getProperties().add(perperty);
					}
				}
				map.put(name, bean);
			}
		}
		return map;
	}
	
	/**
	 * 获取文件
	 * @return
	 */
	private static Document getDocument() {
		//1创建解析器
		SAXReader reader = new SAXReader();
		
		//2加载配置文件
		InputStream is = ConfigManager.class.getResourceAsStream("/applicationContext.xml");
		Document doc = null;
		try {
			doc = reader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("配置文件加载失败");
		}
		return doc;
	}
}
