package cn.spring.bean;

public class B {
	
	public B() {
		System.out.println("B创建了");
	}

	private A a;

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
	
	
}
