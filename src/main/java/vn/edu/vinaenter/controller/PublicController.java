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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.constant.Defines;
import vn.edu.vinaenter.model.bean.Category;
import vn.edu.vinaenter.model.bean.Contact;
import vn.edu.vinaenter.model.bean.Land;
import vn.edu.vinaenter.model.dao.CategoryDAO;
import vn.edu.vinaenter.model.dao.ContactDAO;
import vn.edu.vinaenter.model.dao.LandDAO;

@Controller

public class PublicController {

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private LandDAO landDAO;
	
	@Autowired
	private ContactDAO contactDAO;
	
	
	@ModelAttribute
	public void commonObject(ModelMap modelMap) {
		

		List<Category> categoriess = categoryDAO.getItems();
		modelMap.addAttribute("categoriess", categoriess);
		
		List<Category> categories = categoryDAO.getCategoriesWithTotal();
		modelMap.addAttribute("categories", categories);
		
		List<Land> landOfcount = landDAO.getItemsByCountView();
		modelMap.addAttribute("landOfcount", landOfcount);
	}
	
	
	@GetMapping("")
	public String index(@RequestParam(name ="page", required = false) Integer page, ModelMap modelMap) { // required cho truong hop trang thu 1
		
		
		int numberOfItems = landDAO.countItems();
		int numberOfPages = (int) Math.ceil(numberOfItems * 1.0 / Defines.ROW_COUNT); // * 1.0 de ep kieu ve so thuc
		
		
		if (page == null) {
			page = 1;
		}else if (page < 1){
			return "redirect:/?page=1";
		}else if (page > numberOfPages){
			return "redirect:/?page="+numberOfPages;
		}
		
		
		int offset = (page - 1 ) * Defines.ROW_COUNT;
		
		List<Land> lands = landDAO.getItemsPagination(offset);
		modelMap.addAttribute("lands", lands);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("numberOfPages", numberOfPages);
		
		
		return "cland.public.index";
	}
	
	

	
	@GetMapping("cat")
	public String cat() {
		
		return "cland.public.cat";
	}
	
	@GetMapping("danh-muc/{catName}-{id}")
	public String cat(@PathVariable int id, ModelMap modelMap) {
		
		List<Land> lands = landDAO.getItemsByCategoryId(id);
		modelMap.addAttribute("lands", lands);
		
		Category cate = categoryDAO.getItem(id);
		modelMap.addAttribute("cate", cate);
		
		modelMap.addAttribute("id", id);
		
		
		
		return "cland.public.cat";
	}
	
	@GetMapping("lien-he")
	public String contact() {
		
		return "cland.public.contact";
	}
	
	@PostMapping("lien-he")
	public String contact(@Valid @ModelAttribute("contact") Contact contact,BindingResult br, RedirectAttributes ra, ModelMap modelMap) {
		
		if (br.hasErrors()) {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "cland.public.contact";
		}
		
		if (contactDAO.add(contact) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_ADD_SUCCESS);
			return "redirect:/lien-he";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/lien-he";
		}
		
	}
	
	@GetMapping("single1")
	public String single(ModelMap modelMap) {
		
		List<Land> landd = landDAO.getItemsIntro();
		modelMap.addAttribute("landd", landd);
		
		return "cland.public.single1";
	}
	
	@GetMapping("{landName}-{id}.html")
	public String single(@PathVariable int id, ModelMap modelMap) {
		Land landById = landDAO.getItemById(id);
		modelMap.addAttribute("landById", landById);
		
		landDAO.increaseView(id);
		
		List<Land> listlandRelated = landDAO.getItemsRelated(landById);
		modelMap.addAttribute("land", landById);
		modelMap.addAttribute("listlandRelated", listlandRelated);
		
		return "cland.public.single";
		
	}
	
	@GetMapping("search")
	public String search(@RequestParam(name ="page", required = false) Integer page, @RequestParam String search, ModelMap modelMap) {
		
		int numberOfItems = landDAO.countItemsByName(search);
		
		int numberOfPages = (int) Math.ceil(numberOfItems * 1.0 / Defines.ROW_COUNT); // * 1.0 de ep kieu ve so thuc
		
		
		if (page == null) {
			page = 1;
		}else if (page < 1){
			return "redirect:/search/?page=1";
		}else if (page > numberOfPages){
			return "redirect:/search/?page="+numberOfPages;
		}
		
		
		int offset = (page - 1 ) * Defines.ROW_COUNT;
		
		
		List<Land> listlandsSearch = landDAO.getItemsSearch(search, offset);
		
		modelMap.addAttribute("listlandsSearch", listlandsSearch);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("numberOfPages", numberOfPages);
		
		return "cland.public.search";
	}
	
}
