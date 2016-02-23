package cn.spring.main;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.spring.config.Bean;
import cn.spring.config.Property;
import cn.spring.config.parse.ConfigManager;
import cn.spring.utils.BeanUtils;

public class ClassPathXmlApplicationContext implements BeanFactory {
	
	private Map<String, Bean> config;
	
	//使用一个map来做spring的容器,放置spring所管理的对象
	private Map<String, Object> context = new HashMap<String, Object>();

	@Override
	public Object getBean(String beanName) {
		Object bean = context.get(beanName);
		if (bean == null) {
			bean = createBean(config.get(beanName));
		}
		return bean;
	}

	public ClassPathXmlApplicationContext() {
		
	}

	public ClassPathXmlApplicationContext(String path) {
		config = ConfigManager.getConfig(path);
		
		if (config != null) {
			for (Entry<String, Bean> en : config.entrySet()) {
				String beanName = en.getKey();
				Bean bean = en.getValue();
				
				Object existObj = context.get(beanName);
				
				//当bean为单例时,才放入容器
				if (existObj == null && "singleton".equals(bean.getScope())) {
					//根据bean配置创建bean对象
					Object beanObj = createBean(bean);
					//初始化或放入容器中
					context.put(beanName, beanObj);
				}
			}
		}
	}

	/*
	 *  <bean name="A" class="cn.itcast.bean.A"  >
		<property name="name" value="123" ></property>
		</bean>
	 */
	private Object createBean(Bean bean) {
		String className = bean.getClassName();
		Class clazz = null;
		Object beanObj;
		
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("请检查您的bean配置");
		}
		
		//创建class对象
		try {
			beanObj = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建class对象出错，请检查您的bean配置");
		} 
		
		//bean的注入
		if (bean.getProperties() != null) {
			for (Property property : bean.getProperties()) {
				String name = property.getName();
				Method setMethod = BeanUtils.getWriteMethod(beanObj, name);
				
				Object param = null;
				if (property.getValue() != null) {
					String value = property.getValue();
					param = value;
				}
				
				//特殊类型的注入
				if (property.getRef() != null) {
					Object existObj = context.get(property.getRef());
					//看是否已经注入过
					if (existObj == null) {
						existObj = createBean(config.get(property.getRef()));
						//单例才放入容器
						if (config.get(property.getRef()).equals("singleton")) {
							context.put(property.getRef(), existObj);
						}
					}
					param = existObj;
				}
				
				try {
					setMethod.invoke(beanObj, param);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("您的bean属性没有对应的set方法");
				}
			}
		}
		return beanObj;
	}
}
