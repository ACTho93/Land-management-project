package vn.edu.vinaenter.model.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Category {
	private int id;
	
	@NotBlank
	//Truyện cổ tích
	@Pattern(regexp = "[^A-Za-zÀ-ÿ '-]*\\s+")
	private String name;
	
	private int total;
	
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
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Category(int id, @NotBlank String name, int total) {
		super();
		this.id = id;
		this.name = name;
		this.total = total;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Category() {
		super();
	}
	
	
}
