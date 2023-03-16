package vn.edu.vinaenter.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.constant.Defines;
import vn.edu.vinaenter.model.bean.Role;
import vn.edu.vinaenter.model.bean.User;

@Repository
public class UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String FIND_ALL = "SELECT u.*, r.name FROM users AS u INNER JOIN roles AS r ON u.roleId = r.id";
	private static final String FIND_ONE_BY_USERNAME = "SELECT u.*, r.name FROM users AS u INNER JOIN roles AS r ON u.roleId = r.id WHERE u.username = ?";
	private static final String INSERT_ONE = "INSERT INTO users(username,fullname,password,roleId) VALUES(?,?,?,?)";
	private static final String COUNT_USERNAME = "SELECT COUNT(*) FROM users WHERE username = ?";
	private static final String DELETE_ONE_BY_ID = "DELETE FROM users WHERE id = ?";
	private static final String FIND_ONE_BY_ID= "SELECT u.*, r.name FROM users AS u INNER JOIN roles AS r ON u.roleId = r.id WHERE u.id = ?";
	private static final String UPDATE_ONE_BY_ID = "UPDATE users SET fullname = ?, password = ?, roleId = ? WHERE id = ?";
	private static final String COUNT_USER = "SELECT COUNT(*) FROM users";
	private static final String FIND_ALL_USER_SEARCH = "SELECT u.*, r.name FROM users AS u INNER JOIN roles AS r ON u.roleId = r.id WHERE username LIKE ? || fullname LIKE ?";
	
	private static final String COUNT_ALL = "SELECT COUNT(*) FROM users";
	private static final String FIND_PAGINATION = "SELECT u.*, r.name FROM users AS u INNER JOIN roles AS r ON u.roleId = r.id ORDER BY u.id DESC LIMIT ?, ?";
	
	
	
	
	
	private RowMapper<User> getRowMapper() {
		return new RowMapper<User>() { //RowMapper là 1 interface nên k thể tạo đối tượng = từ khóa new nên override
			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("fullname"), rs.getString("password"), new Role(rs.getInt("roleId"), rs.getString("name")));
				return user;
			}
		};
	}
	
	public List<User> getItems(){
		return jdbcTemplate.query(FIND_ALL, getRowMapper());
		
	}


	public int add(User user) {
		return jdbcTemplate.update(INSERT_ONE, new Object[] {user.getUsername(), user.getFullname(), user.getPassword(), user.getRole().getId()});
	}
	
	@SuppressWarnings("deprecation")
	public boolean hasUser(String username) {
		return jdbcTemplate.queryForObject(COUNT_USERNAME, new Object[] {username} , Boolean.class);
	}


	public int delete(int id) {
		return jdbcTemplate.update(DELETE_ONE_BY_ID, new Object[] {id});
	}


	@SuppressWarnings("deprecation")
	public User getItem(int id) {
		return jdbcTemplate.queryForObject(FIND_ONE_BY_ID,new Object[] {id} , getRowMapper());
	}

	public int edit(User user) {
		return jdbcTemplate.update(UPDATE_ONE_BY_ID, new Object[] {user.getFullname(), user.getPassword(), user.getRole().getId(), user.getId()});
	}

	public int count() {
		return jdbcTemplate.queryForObject(COUNT_USER, Integer.class);
	}

	public List<User> getItemsSearch(String name) {
		return jdbcTemplate.query(FIND_ALL_USER_SEARCH, new String[] {"%"+name+"%","%"+name+"%"},getRowMapper() );
	}

	public int countItems() {
		return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
	}

	public List<User> getItemsPagination(int offset) {
		return jdbcTemplate.query(FIND_PAGINATION,new Object[] {offset, Defines.ROW_COUNT} , getRowMapper());
	}

	public User getItemByUsername(String username) {
		return jdbcTemplate.queryForObject(FIND_ONE_BY_USERNAME,new Object[] {username} , getRowMapper());
	}

}
