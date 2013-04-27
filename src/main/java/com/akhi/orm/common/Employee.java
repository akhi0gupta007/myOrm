package com.akhi.orm.common;

public class Employee {

	private String name;
	private String designation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignatiom(String designation) {
		this.designation = designation;
	}

	public Employee(String name, String designatiom) {
		super();
		this.name = name;
		this.designation = designatiom;
	}

}
