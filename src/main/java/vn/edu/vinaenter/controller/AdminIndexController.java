package vn.edu.vinaenter.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import vn.edu.vinaenter.model.dao.CategoryDAO;
import vn.edu.vinaenter.model.dao.ContactDAO;
import vn.edu.vinaenter.model.dao.LandDAO;
import vn.edu.vinaenter.model.dao.UserDAO;

@Controller
public class AdminIndexController {

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private LandDAO landDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ContactDAO contactDAO;
	
	@GetMapping("admincp")
	public String index(ModelMap modelMap) {
		
		int countCat = categoryDAO.count();
		modelMap.addAttribute("countCat", countCat);
		
		int countland = landDAO.count();
		modelMap.addAttribute("countland", countland);
		
		int countuser = userDAO.count();
		modelMap.addAttribute("countuser", countuser);
		
		int countcontact = contactDAO.count();
		modelMap.addAttribute("countcontact", countcontact);
		
		
		
		return "cland.admin.index.index";
	}
	
}
