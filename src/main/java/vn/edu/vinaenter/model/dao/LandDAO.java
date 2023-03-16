package vn.edu.vinaenter.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.constant.Defines;
import vn.edu.vinaenter.model.bean.Category;
import vn.edu.vinaenter.model.bean.Land;

@Repository
public class LandDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String FIND_ALL = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id ORDER BY id DESC" ;
	private static final String INSERT_ONE = "INSERT INTO lands(name, categoryid, picture, description, detail, address, area) VALUES(?,?,?,?,?,?,?)";
	private static final String DELETE_ONE_BY_ID = "DELETE FROM lands WHERE id = ?";
	private static final String FIND_ONE_BY_ID = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id WHERE l.id = ?" ;
	private static final String UPDATE_ONE_BY_ID = "UPDATE lands SET name = ?, categoryid = ?, picture = ?, description = ?,detail = ?, address = ?, area = ? WHERE id = ?";
	private static final String COUNT_LAND= "SELECT COUNT(*) FROM lands";
	private static final String FIND_ALL_BY_LANDNEW = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id ORDER BY id DESC LIMIT 3" ;
	private static final String FIND_ALL_BY_COUNTVIEW = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id ORDER BY count_views DESC LIMIT 5" ;
	private static final String FIND_ALL_BY_ID = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id WHERE categoryid = ?" ;
	private static final String FIND_BY_RELATED = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id WHERE l.categoryid = ? && l.id != ? ORDER BY l.categoryid DESC LIMIT ?" ;
	private static final String FIND_ALL_INTRO = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id ORDER BY id DESC LIMIT 1" ;
	private static final String COUNT_NAMELAND = "SELECT COUNT(*) FROM lands WHERE name = ?";
	private static final String FIND_ALL_LANDSEARCH = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id WHERE l.name LIKE ? || c.name LIKE ? || l.description LIKE ? LIMIT ?, ?" ;
	private static final String COUNT_LAND_ID_CAT= "SELECT COUNT(*) FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id WHERE categoryid = ?";
	private static final String COUNT_VIEWS= "UPDATE lands SET count_views = count_views + 1 WHERE id = ?";
	
	private static final String COUNT_ALL = "SELECT COUNT(*) FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id" ;
	private static final String FIND_PAGINATION = "SELECT l.*, c.name FROM lands AS l INNER JOIN categories AS c ON l.categoryid = c.id ORDER BY date_create DESC LIMIT ?, ?" ;
	
	
	private static final String DELETE_LAND_BY_ID = "DELETE FROM lands WHERE categoryid = ?";
	private static final String COUNT_LAND_BY_SEARCH = "SELECT COUNT(*) FROM lands WHERE name LIKE ?";
	
	
	
	
	public RowMapper<Land> getRowMapper(){
		return  new RowMapper<Land>() {
			@Override
			public Land mapRow(ResultSet rs, int arg1) throws SQLException {
				Land land = new Land(rs.getInt("id"), rs.getString("l.name"), rs.getString("description"), rs.getString("detail"), rs.getTimestamp("date_create"), rs.getString("picture"), rs.getInt("area"), rs.getString("address"), rs.getInt("count_views"), new Category(rs.getInt("categoryid"), rs.getString("c.name")));
				return land;
			}
		};
	}
	
	public List<Land> getItems() {
		return jdbcTemplate.query(FIND_ALL, getRowMapper());
	}

	public int add(Land land) {
		return jdbcTemplate.update(INSERT_ONE, new Object[] {land.getName(), land.getCategory().getId(), land.getPicture(), land.getDescription(),land.getDetail() ,land.getAddress(),land.getArea() });
	}

	public int delete(int id) {
		return jdbcTemplate.update(DELETE_ONE_BY_ID, new Object[] {id});
	}

	@SuppressWarnings("deprecation")
	public Land getItem(int id) {
		return jdbcTemplate.queryForObject(FIND_ONE_BY_ID,new Object[] {id} , getRowMapper());
	}

	public int edit(Land land) {
		return jdbcTemplate.update(UPDATE_ONE_BY_ID, new Object[] {land.getName(), land.getCategory().getId(), land.getPicture(), land.getDescription(),land.getDetail(), land.getAddress(),land.getArea(), land.getId()});
	}

	public int count() {
		return jdbcTemplate.queryForObject(COUNT_LAND, Integer.class);
	}

	public List<Land> getItemsByLandNew() {
		return jdbcTemplate.query(FIND_ALL_BY_LANDNEW, getRowMapper());
	}

	public List<Land> getItemsByCountView() {
		return jdbcTemplate.query(FIND_ALL_BY_COUNTVIEW, getRowMapper());
	}

	public List<Land> getItemsByCategoryId(int idDMT) {
		return jdbcTemplate.query(FIND_ALL_BY_ID, getRowMapper(),idDMT);
	}

	@SuppressWarnings("deprecation")
	public Land getItemById(int id) {
		return jdbcTemplate.queryForObject(FIND_ONE_BY_ID,new Object[] {id} , getRowMapper());
	}

	public List<Land> getItemsRelated(Land land) {
		return jdbcTemplate.query(FIND_BY_RELATED, getRowMapper(),land.getCategory().getId(), land.getId(), 3);
	}

	public List<Land> getItemsIntro() {
		return jdbcTemplate.query(FIND_ALL_INTRO, getRowMapper());
	}

	public boolean hasLand(String name) {
		return jdbcTemplate.queryForObject(COUNT_NAMELAND,new Object[] {name} , Boolean.class);
	}

	public List<Land> getItemsSearch(String search, int offset) {
		return jdbcTemplate.query(FIND_ALL_LANDSEARCH,new Object[] {"%"+search+"%","%"+search+"%","%"+search+"%",offset, Defines.ROW_COUNT } , getRowMapper());
	}

	public int count(int id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(COUNT_LAND_ID_CAT, new Object[] {id} ,Integer.class);
	}

	public Integer increaseView(int id) {
		return jdbcTemplate.update(COUNT_VIEWS, new Object[] {id});
		
	}

	public int countItems() {
		return jdbcTemplate.queryForObject(COUNT_ALL,Integer.class);
	}

	public List<Land> getItemsPagination(int offset) {
		return jdbcTemplate.query(FIND_PAGINATION,new Object[] {offset, Defines.ROW_COUNT} , getRowMapper());
	}

	public int dellandAdmin(int id) {
		return jdbcTemplate.update(DELETE_LAND_BY_ID, new Object[] {id});
		
	}

	public int countItemsByName(String name) {
		return jdbcTemplate.queryForObject(COUNT_LAND_BY_SEARCH, new Object[] {"%" + name + "%"} ,Integer.class);
	}

	



//	public List<Land> getItemsRelated(Land land, int idland) {
//		return jdbcTemplate.query(FIND_ALL_RELATED, getRowMapper(),land.getCategory().getId(), idland, 2);
//	}
}
