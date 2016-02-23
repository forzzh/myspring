package cn.spring.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanUtils {

	/**
	 * 获取类对应的set方法
	 * @param beanObj
	 * @param name
	 * @return
	 */
	public static Method getWriteMethod(Object beanObj, String name) {
		Method writeMethod = null;
		try {
			//获取beanInfo
			BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
			//获取所有属性的描述器
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			//遍历
			if (descriptors != null) {
				for (PropertyDescriptor descriptor : descriptors) {
					String pName = descriptor.getName();
					
					if (pName.equals(name)) {
						//获得写入属性的set方法
						writeMethod = descriptor.getWriteMethod();
					}
				}
			}
			
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return writeMethod;
	}

}
