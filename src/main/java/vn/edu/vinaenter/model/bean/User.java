package vn.edu.vinaenter.model.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class User {
	private int id;
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]+")
	private String username;
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]+")
	private String fullname;

	private String password;
	
	private Role role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String username, String fullname, String password, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.password = password;
		this.role = role;
	}
	

	
}
