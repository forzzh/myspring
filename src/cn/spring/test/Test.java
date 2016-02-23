package cn.spring.test;

import cn.spring.bean.A;
import cn.spring.bean.B;
import cn.spring.main.BeanFactory;
import cn.spring.main.ClassPathXmlApplicationContext;

public class Test {

	@org.junit.Test
	public void test1() {
		BeanFactory bf = new ClassPathXmlApplicationContext("/application");
		
		A a = (A) bf.getBean("A");
		A a2 = (A) bf.getBean("A");
		System.out.println(a2.getName());
		
		System.out.println(a.getName());
	}
	
	@org.junit.Test
	public void test2() {
		BeanFactory bf = new ClassPathXmlApplicationContext("/application");
		
		B b = (B) bf.getBean("B");
		
		System.out.println(b.getA().getName());
		B b2 = (B) bf.getBean("B");
		
		System.out.println(b2.getA().getName());
	}
}
