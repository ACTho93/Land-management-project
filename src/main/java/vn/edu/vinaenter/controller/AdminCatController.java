package vn.edu.vinaenter.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import vn.edu.vinaenter.model.bean.Category;
import vn.edu.vinaenter.model.dao.CategoryDAO;
import vn.edu.vinaenter.model.dao.LandDAO;

@Controller
@RequestMapping("admin/cat")
public class AdminCatController {

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private LandDAO landDAO;
	
	
	@GetMapping("index")
	public String index(@RequestParam(name ="page", required = false) Integer page, ModelMap modelMap) {
		int numberOfItems = categoryDAO.countItems();
		int numberOfPages = (int) Math.ceil(numberOfItems * 1.0 / Defines.ROW_COUNT); // * 1.0 de ep kieu ve so thuc
		
		if (page == null) {
			page = 1;
		}else if (page < 1){
			return "redirect:/?page=1";
		}else if (page > numberOfPages){
			return "redirect:/?page="+numberOfPages;
		}
		
		int offset = (page - 1 ) * Defines.ROW_COUNT;
		
		List<Category> categories = categoryDAO.getItemsPagination(offset);
		modelMap.addAttribute("categories", categories);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("numberOfPages", numberOfPages);
		
		
		return "cland.admin.cat.index";
	}
	
	@GetMapping("add")
	public String add() {
		return "cland.admin.cat.add";
	}
	
	@PostMapping("add")
	public String add(@Valid @ModelAttribute("category") Category category, BindingResult br, RedirectAttributes ra) {
		
		if (categoryDAO.hasCat(category.getName())) {
			br.rejectValue("name", "namecatExisted");
		}
		
		if (br.hasErrors()) {
			return "cland.admin.cat.add";
		}
		
		
		
		
		if (categoryDAO.add(category) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_ADD_SUCCESS);
			return "redirect:/admin/cat/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/cat/index";
		}
		
	}
	
	@GetMapping("del/{id}")
	public String delete(@PathVariable int id, RedirectAttributes ra ) {
		
		landDAO.dellandAdmin(id);
		
		if (categoryDAO.delete(id) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_DELETE_SUCCESS);
			return "redirect:/admin/cat/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/cat/index";
		}
		
	}
	

	@GetMapping("edit/{id}")
	public String edit(@PathVariable int id, ModelMap modelMap) {
		Category category = categoryDAO.getItem(id);
		modelMap.addAttribute("category", category);
		return "cland.admin.cat.edit";
		
	}
	
	@PostMapping("edit/{id}")
	public String edit(@Valid @ModelAttribute("category") Category category, BindingResult br, RedirectAttributes ra) {
		// id ghi trùng với khai báo private int id;
		if (br.hasErrors()) {
			return "cland.admin.cat.edit";
		}
		
		if (categoryDAO.edit(category) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_UPDATE_SUCCESS);
			return "redirect:/admin/cat/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/cat/index";
		}
		
	}
	
//	@PostMapping("search")
//	public String search(@RequestParam String name, ModelMap modelMap) {
//		
//		System.out.println(name);
//		List<Category> listCatSearch = categoryDAO.getItemsByName(name);
//		System.out.println(listCatSearch);
//		modelMap.addAttribute("listCatSearch", listCatSearch);
//		modelMap.addAttribute("name", name);
//		
//		return "cland.admin.cat.search";
//	}
	
	@GetMapping("search")
	public String search(@RequestParam String name, ModelMap modelMap) {
		List<Category> listCatSearch = categoryDAO.getItemsByName(name);
		modelMap.addAttribute("listCatSearch", listCatSearch);
		return "cland.admin.cat.search";
	}
	
	
}
