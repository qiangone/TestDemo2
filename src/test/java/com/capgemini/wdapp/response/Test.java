package com.capgemini.wdapp.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.capgemini.wdapp.util.DateUtil;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<User> list = new ArrayList<User>();
		User u1 = new User(1,"aaaa");
		User u2 = new User(2,"bbbb");
		list.add(u1);
		//list.add(u2);
//		
//		for(@SuppressWarnings("unused") User u : list){
//			u = u2;
//			System.out.print(u);
//		}
//		
//		for(User u : list){
//			System.out.print(u.getName());
//		}
//		
		Date d1 = DateUtil.dateParseWithSec("2016-03-07 11:22:13");
		Date d2 = DateUtil.dateParseWithSec("2016-03-08 11:22:13");
		
		System.out.println(DateUtil.getDiffDay(d1, d2));
		
		
		String a = "abc";
		String b = new String("abc");
		System.out.println("a==b" + (a==b));
		System.out.println("a equals b" + a.equals(b));

	}
	

}


 class User{
	int id;
	String name;
	
	public User(int id, String name){
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id" + id + "name"+name;
	}
	
	
	
	
}
