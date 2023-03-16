package vn.edu.vinaenter.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.constant.Defines;
import vn.edu.vinaenter.model.bean.Role;
import vn.edu.vinaenter.model.bean.User;
import vn.edu.vinaenter.model.dao.RoleDAO;
import vn.edu.vinaenter.model.dao.UserDAO;

@Controller
@RequestMapping("admin/user")
public class AdminUserController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@GetMapping("index")
	public String index(@RequestParam(name ="page", required = false) Integer page, ModelMap modelMap) {
		
		int numberOfItems = userDAO.countItems();
		int numberOfPages = (int) Math.ceil(numberOfItems * 1.0 / Defines.ROW_COUNT); // * 1.0 de ep kieu ve so thuc
		
		if (page == null) {
			page = 1;
		}else if (page < 1){
			return "redirect:/?page=1";
		}else if (page > numberOfPages){
			return "redirect:/?page="+numberOfPages;
		}
		
		int offset = (page - 1 ) * Defines.ROW_COUNT;
		
		
		
		List<User> users = userDAO.getItemsPagination(offset);
		modelMap.addAttribute("users", users);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("numberOfPages", numberOfPages);
		
		
		return "cland.admin.user.index";
	}
	
	@GetMapping("add")
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("roles", roleDAO.getItems());
		return "cland.admin.user.add";
	}
	
	@PostMapping("add")
	public String add(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam("roleId") int roleId, RedirectAttributes ra,ModelMap modelMap) {
		
		user.setRole(new Role(roleId, null));
		
		if (userDAO.hasUser(user.getUsername())) {
			result.rejectValue("username", "usernameExisted");
		}
		
		if (result.hasErrors()) {
			modelMap.addAttribute("roles", roleDAO.getItems());
			return "cland.admin.user.add";
		}
		
		
		
		user.setPassword(bCrypt.encode(user.getPassword()));
		if (userDAO.add(user) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_ADD_SUCCESS);
			return "redirect:/admin/user/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/user/index";
		}
		
	}
	
	@GetMapping("del/{id}")
	public String delete(@PathVariable int id, RedirectAttributes ra, Principal principal ) {
		
		User user = userDAO.getItem(id);
		
		if (user.getRole().getName().equals("ADMIN") && user.getUsername().equals(principal.getName())) {
			// không xóa được, thì chuyển về trang index
			
			ra.addFlashAttribute("msg", "Không được xóa");
			return "redirect:/admin/user/index";
		}
		
		if (userDAO.delete(id) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_DELETE_SUCCESS);
			return "redirect:/admin/user/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/user/index";
		}
		
	}
	
	@GetMapping("edit/{id}")
	public String edit(@PathVariable int id, ModelMap modelMap) {
		User user = userDAO.getItem(id);
		modelMap.addAttribute("user", user);
		
		modelMap.addAttribute("roles", roleDAO.getItems());
		return "cland.admin.user.edit";
	}
	
	@PostMapping("edit/{id}")
	public String edit(@Valid @ModelAttribute User user, BindingResult result, @RequestParam("roleId") int roleId, RedirectAttributes ra,ModelMap modelMap) {
		
		user.setRole(new Role(roleId, null));
		
		
		// làm sao để lấy lại được password của user cũ, khi người dùng k edit lại
		
		User oldUser = userDAO.getItem(user.getId()); // user cũ
		if ("".equals(user.getPassword())) { // nếu password mới trùng với rỗng thì lấy lại pass cũ
			user.setPassword(oldUser.getPassword());
		}
		
		
		if (result.hasErrors()) {
			modelMap.addAttribute("roles", roleDAO.getItems());
			return "cland.admin.user.edit";
		}
		
		
		user.setPassword(bCrypt.encode(user.getPassword()));
		if (userDAO.edit(user) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_UPDATE_SUCCESS);
			return "redirect:/admin/user/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/user/index";
		}
		
	}
	
	@GetMapping("search")
	public String search(@RequestParam String name, ModelMap modelMap) {
		List<User> listuserSearch = userDAO.getItemsSearch(name);
		modelMap.addAttribute("listuserSearch", listuserSearch);
		return "cland.admin.user.search";
	}
	
	@GetMapping("profile")
	public String profile(ModelMap modelMap, Principal principal) {
		
		String username = principal.getName();
		User user = userDAO.getItemByUsername(username);
		modelMap.addAttribute("user", user);
		
		modelMap.addAttribute("roles", roleDAO.getItems());
		return "cland.admin.user.profile";
	}
	
	@PostMapping("profile/{id}")
	public String profile(@Valid @ModelAttribute User user, BindingResult result, @RequestParam("roleId") int roleId, RedirectAttributes ra,ModelMap modelMap) {
		
		user.setRole(new Role(roleId, null));
		// làm sao để lấy lại được password của user cũ, khi người dùng k edit lại
		User oldUser = userDAO.getItem(user.getId()); // user cũ
		
		if ("".equals(user.getPassword())) { // nếu password mới trùng với rỗng thì lấy lại pass cũ
			user.setPassword(oldUser.getPassword());
		}
		
		
		if (result.hasErrors()) {
			modelMap.addAttribute("roles", roleDAO.getItems());
			return "cland.admin.user.profile";
		}
		
		
		user.setPassword(bCrypt.encode(user.getPassword()));
		if (userDAO.edit(user) > 0) {
			ra.addFlashAttribute("msg", "Thay đổi thông tin thành công!");
			return "redirect:/admincp";
		} else {
			ra.addFlashAttribute("msg", "Thay đổi thông tin thất bại!");
			return "redirect:/admin/user/index";
		}
		
	}
	
}
