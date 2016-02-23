package cn.spring.bean;

public class A {

	public A() {
		System.out.println("A创建了");
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
