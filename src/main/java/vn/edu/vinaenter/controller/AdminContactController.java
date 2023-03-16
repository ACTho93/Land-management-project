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
import vn.edu.vinaenter.model.bean.Contact;
import vn.edu.vinaenter.model.dao.ContactDAO;

@Controller
@RequestMapping("admin/contact")
public class AdminContactController {

	@Autowired
	private ContactDAO contactDAO;
	
	@GetMapping("index")
	public String index(@RequestParam(name ="page", required = false) Integer page, ModelMap modelMap) {

		int numberOfItems = contactDAO.countItems();
		int numberOfPages = (int) Math.ceil(numberOfItems * 1.0 / Defines.ROW_COUNT); // * 1.0 de ep kieu ve so thuc
		
		if (page == null) {
			page = 1;
		}else if (page < 1){
			return "redirect:/?page=1";
		}else if (page > numberOfPages){
			return "redirect:/?page="+numberOfPages;
		}
		
		int offset = (page - 1 ) * Defines.ROW_COUNT;
		
		
		List<Contact> listContact = contactDAO.getItemsPagination(offset);
		modelMap.addAttribute("listContact", listContact);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("numberOfPages", numberOfPages);
		return "cland.admin.contact.index";
		
	}
	
	@GetMapping("add")
	public String add() {
		return "cland.admin.contact.add";
	}
	
	@PostMapping("add")
	public String add(@Valid @ModelAttribute("contact") Contact contact, BindingResult br, RedirectAttributes ra, ModelMap modelMap) {
		
		if (br.hasErrors()) {
			return "cland.admin.contact.add";
		}
		
		if (contactDAO.add(contact) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_ADD_SUCCESS);
			return "redirect:/admin/contact/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/contact/index";
		}
	}
	
	@GetMapping("del/{id}")
	public String del(@PathVariable int id, RedirectAttributes ra, ModelMap modelMap) {
		
		if (contactDAO.delete(id) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_DELETE_SUCCESS);
			return "redirect:/admin/contact/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/contact/index";
		}
		
		
	}
	
	@GetMapping("edit/{id}")
	public String edit(@PathVariable int id, RedirectAttributes ra, ModelMap modelMap ) {
		Contact contact = contactDAO.getItem(id);
		modelMap.addAttribute("contact", contact);
		return "cland.admin.contact.edit";
	}
	
	
	@PostMapping("edit/{id}")
	public String edit(@Valid @ModelAttribute("contact") Contact contact, BindingResult br, RedirectAttributes ra, ModelMap modelMap) {
		
		if (br.hasErrors()) {
			return "cland.admin.contact.edit";
		}
		
		if (contactDAO.edit(contact) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_UPDATE_SUCCESS);
			return "redirect:/admin/contact/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/contact/index";
		}
	}
}
