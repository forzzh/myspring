package cn.spring.main;

public interface BeanFactory {

	/**
	 * 根据name获取bean
	 * @param beanName
	 * @return
	 */
	Object getBean(String beanName);
}
