package vn.edu.vinaenter.model.bean;

import javax.validation.constraints.Pattern;

public class Role {

	private int id;
	
	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]+")
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
}
