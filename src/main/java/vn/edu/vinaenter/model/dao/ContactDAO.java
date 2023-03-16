package vn.edu.vinaenter.model.dao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.constant.Defines;
import vn.edu.vinaenter.model.bean.Contact;

@Repository
public class ContactDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String FIND_ALL = "SELECT * FROM vnecontact ORDER BY id DESC";
	private static final String INSERT_ONE = "INSERT INTO vnecontact(fullname,email,subject,content) VALUES(?,?,?,?)";
	private static final String DELETE_ONE_BY_ID = "DELETE FROM vnecontact WHERE id = ?";
	private static final String FIND_ONE_BY_ID = "SELECT * FROM vnecontact WHERE id = ?";
	private static final String UPDATE_ONE_BY_ID = "UPDATE vnecontact SET fullname = ?, email = ?, subject = ?, content = ? WHERE id = ?";
	private static final String COUNT_CONTACT = "SELECT COUNT(*) FROM vnecontact";
	
	private static final String COUNT_ALL = "SELECT COUNT(*) FROM vnecontact";
	private static final String FIND_PAGINATION = "SELECT * FROM vnecontact ORDER BY id DESC LIMIT ?, ?";
	
	
	public BeanPropertyRowMapper<Contact> getRowMapper(){
		return new BeanPropertyRowMapper<>(Contact.class);
	}
	
	public List<Contact> getItems() {
		return jdbcTemplate.query(FIND_ALL, getRowMapper());
		
	}

	public int add(Contact contact) {
		return jdbcTemplate.update(INSERT_ONE, new Object[] {contact.getFullname(), contact.getEmail(),contact.getSubject(),contact.getContent()} );
	}

	public int delete(int id) {
		return jdbcTemplate.update(DELETE_ONE_BY_ID, new Object[] {id});
	}

	public Contact getItem(int id) {
		return jdbcTemplate.queryForObject(FIND_ONE_BY_ID, new Object[] {id}, getRowMapper());
	}

	public int edit(Contact contact) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update(UPDATE_ONE_BY_ID, new Object[] {contact.getFullname(),contact.getEmail(),contact.getSubject(), contact.getContent(), contact.getId()});
	}

	public int count() {
		return jdbcTemplate.queryForObject(COUNT_CONTACT, Integer.class);
	}

	public int countItems() {
		return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
	}

	public List<Contact> getItemsPagination(int offset) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(FIND_PAGINATION, new Object[] {offset, Defines.ROW_COUNT}, getRowMapper());
	}
	
}
