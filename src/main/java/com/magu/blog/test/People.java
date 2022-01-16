package com.magu.blog.test;

public class People {
	private	int hungryState = 50; // 100이 만땅이면
	
	
	public void eat() {
		hungryState += 10;
	}
	public  static void name(String[] args) {
		People p = new People();
		p.eat();
	}
}
