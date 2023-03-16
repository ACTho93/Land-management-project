package vn.edu.vinaenter.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.constant.Defines;
import vn.edu.vinaenter.model.bean.Category;

@Repository
public class CategoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String FIND_ALL = "SELECT * FROM categories";
	private static final String FIND_ONE_BY_ID = "SELECT * FROM categories WHERE id = ?";
	private static final String INSERT_ONE = "INSERT INTO categories(name) VALUES(?)";
	private static final String DELETE_ONE_BY_ID = "DELETE FROM categories WHERE id = ?";
	private static final String UPDATE_ONE_BY_ID = "UPDATE categories SET name = ? WHERE id = ?";
	private static final String COUNT_CAT = "SELECT COUNT(*) FROM categories";
	private static final String FIND_ALL_BY_NAME = "SELECT * FROM categories WHERE name LIKE ?";
	private static final String COUNT_CATNAME = "SELECT COUNT(*) FROM categories WHERE name = ?";
	private static final String COUNT_CATDM = "SELECT COUNT(*) FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id WHERE categoryid = ?";
	private static final String SQL_GET_CATEGORY_BY_TOTAL = "SELECT c.id, c.name, count(l.id) total FROM categories c LEFT JOIN lands l ON c.id = l.categoryid GROUP BY c.id";
	
	private static final String COUNT_ALL = "SELECT COUNT(*) FROM categories";
	private static final String FIND_PAGINATION = "SELECT * FROM categories ORDER BY id DESC LIMIT ?, ?";
	
	
	
	public BeanPropertyRowMapper<Category> getRowMapper(){
		return new BeanPropertyRowMapper<>(Category.class);
	}
	
//	private RowMapper<Category> getRowMapper1() {
//		return new RowMapper<Category>() { //RowMapper là 1 interface nên k thể tạo đối tượng = từ khóa new nên override
//			@Override
//			public Category mapRow(ResultSet rs, int arg1) throws SQLException {
//				Category category = new Category(rs.getInt("id"), rs.getString("name"));
//				return category;
//			}
//		};
//	}
	
	
	public List<Category> getItems(){
		return jdbcTemplate.query(FIND_ALL, getRowMapper());
		
	}
	
	public List<Category> getCategoriesWithTotal(){
		return jdbcTemplate.query(SQL_GET_CATEGORY_BY_TOTAL, getRowMapper());
		
	}
	
	
	public int add(Category category) {
		return jdbcTemplate.update(INSERT_ONE, new Object[] {category.getName()});
	}

	public int delete(int id) {
		return jdbcTemplate.update(DELETE_ONE_BY_ID, new Object[] {id});
	}

	@SuppressWarnings("deprecation")
	public Category getItem(int id) {
		return jdbcTemplate.queryForObject(FIND_ONE_BY_ID, new Object[] {id}, getRowMapper());
	}


	public int edit(Category category) {
		return jdbcTemplate.update(UPDATE_ONE_BY_ID, new Object[] { category.getName(), category.getId() });
	}


	public int count() {
		return jdbcTemplate.queryForObject(COUNT_CAT, Integer.class);
	}


	@SuppressWarnings("deprecation")
	public List<Category> getItemsByName(String name) {
		return jdbcTemplate.query(FIND_ALL_BY_NAME, new String[] { "%"+name+"%" }, getRowMapper());
	}

	@SuppressWarnings("deprecation")
	public boolean hasCat(String name) {
		return jdbcTemplate.queryForObject(COUNT_CATNAME, new Object[] {name} , Boolean.class);
	}

	@SuppressWarnings("deprecation")
	public int count(int idDMT) {
		return jdbcTemplate.queryForObject(COUNT_CATDM, new Object[] {idDMT}, Integer.class);
	}

	public int countItems() {
		return jdbcTemplate.queryForObject(COUNT_ALL,Integer.class);
	}

	public List<Category> getItemsPagination(int offset) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(FIND_PAGINATION, new Object[] { offset, Defines.ROW_COUNT }, getRowMapper());
	}

}
	
