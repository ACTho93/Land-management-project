package vn.edu.vinaenter.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.constant.Defines;
import vn.edu.vinaenter.model.bean.Category;
import vn.edu.vinaenter.model.bean.Land;
import vn.edu.vinaenter.model.dao.CategoryDAO;
import vn.edu.vinaenter.model.dao.LandDAO;

@Controller
@RequestMapping("admin/land")
public class AdminLandController {

	@Autowired
	private LandDAO landDAO;

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private CategoryDAO categoryDAO;

	@GetMapping("index")
	public String index(@RequestParam(name ="page", required = false) Integer page, ModelMap modelMap) {

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
		return "cland.admin.land.index";
	}

	@GetMapping("add")
	public String add(ModelMap modelMap) {
		List<Category> categories = categoryDAO.getItems();
		modelMap.addAttribute("categories", categories);
		return "cland.admin.land.add";
	}

	@PostMapping("add")
	public String add(@Valid @ModelAttribute("news") Land land, BindingResult br,
			@RequestParam("hinhanh") MultipartFile multipartFile, HttpServletRequest request,
			@RequestParam("categoryid") int categoryid, RedirectAttributes ra, ModelMap modelMap) {

		if (landDAO.hasLand(land.getName())) {
			br.rejectValue("name", "namelandexisted");
		}
		
		
		if (br.hasErrors()) {
			List<Category> categories = categoryDAO.getItems();
			modelMap.addAttribute("categories", categories);
			return "cland.admin.land.add";
		}
		
		land.setCategory(new Category(categoryid, null));
		
		String fileName = multipartFile.getOriginalFilename(); // tạo tên file hinh.jpg
		
		
		fileName = vn.edu.vinaenter.util.FileUtil.rename(fileName);
		
		
		land.setPicture(fileName);
		
		if (landDAO.add(land) > 0) {

			String dirUpload = servletContext.getRealPath("")+ "WEB-INF" + File.separator + Defines.DIR_UPLOAD; // tạo đường dẫn thuc tên thư mục
			System.out.println(dirUpload);
			File dir = new File(dirUpload); // kiểm tra dirUpload có chưa, nếu chưa thì tạo mới thư mục
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			String filePath = dirUpload + File.separator + fileName;  // tạo đường dẫn upload đến thư mục
			
			try {
				multipartFile.transferTo(new File(filePath)); // upload file lên server
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			ra.addFlashAttribute("msg", Defines.MSG_ADD_SUCCESS);
			return "redirect:/admin/land/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/land/index";
		}
		
	}

	@GetMapping("del/{id}")
	public String add(@PathVariable int id, RedirectAttributes ra) {
		
		Land land = landDAO.getItem(id);
		
		if (landDAO.delete(id) > 0) {
			File file = new File(servletContext.getRealPath("WEB-INF/files")+ File.separator + land.getPicture() );
			file.delete();
			ra.addFlashAttribute("msg", Defines.MSG_DELETE_SUCCESS);
			return "redirect:/admin/land/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/land/index";
		}

	}
	
	@GetMapping("edit/{id}")
	public String edit(@PathVariable int id,ModelMap modelMap) {
		
		modelMap.addAttribute("land", landDAO.getItem(id));
		
		modelMap.addAttribute("category", categoryDAO.getItems());
		
		return "cland.admin.land.edit";
	}
	
	
	@PostMapping("edit/{id}")
	public String edit(@Valid @ModelAttribute("news") Land land, BindingResult br, ModelMap modelMap,@RequestParam("categoryid") int categoryid,
			@RequestParam("hinhanh") CommonsMultipartFile multipartFile, RedirectAttributes ra) {
		land.setCategory(new Category(categoryid, null));
		
		Land oldland = landDAO.getItem(land.getId()); // Get file cũ
		
		if(oldland == null) {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/land/index";
		}
		
		if (br.hasErrors()) {
			land.setPicture(oldland.getPicture());
			modelMap.addAttribute("category", categoryDAO.getItems());
			modelMap.addAttribute("land", land);
			return "cland.admin.land.edit";
		}
		
		
		/**
		 * Case1: co upload
		 *   - lay thong tin file 
		 *   - upload hinh moi
		 *   - cap nhat ten file
		 *   - (optional): xoa  file cu.
		 *   
		 * Case2: ko upload
		 * 	van nhu cu
		 * */
		
		
		String picture = oldland.getPicture(); // truong hop khong co upload
		
		String fileName = multipartFile.getOriginalFilename(); // tạo tên file hinh.jpg
		
		if (!"".equals(fileName)) {
			picture = vn.edu.vinaenter.util.FileUtil.rename(fileName);
			String dirUpload = servletContext.getRealPath("WEB-INF/files"); // tạo đường dẫn thuc tên thư mục
			System.out.println(dirUpload);
				
			File dir = new File(dirUpload); // kiểm tra dirUpload có chưa, nếu chưa thì tạo mới thư mục
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			// xóa ảnh cũ
			File oldFile = new File(servletContext.getRealPath("WEB-INF/files")+ File.separator + oldland.getPicture() );
			oldFile.delete();
			
			// upload ảnh mới
			String filePath = dirUpload + File.separator + picture;  // tạo đường dẫn upload đến thư mục
			
			try {
				multipartFile.transferTo(new File(filePath)); // upload file lên server
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		land.setPicture(picture);
		
		//neu sửa thành công!
		if (landDAO.edit(land) > 0) {
			ra.addFlashAttribute("msg", Defines.MSG_UPDATE_SUCCESS);
			return "redirect:/admin/land/index";
		} else {
			ra.addFlashAttribute("msg", Defines.MSG_ERROR);
			return "redirect:/admin/land/index";
		}

		
	}
	
	@GetMapping("search")
	public String search(@RequestParam(name ="page", required = false) Integer page,@RequestParam String search,ModelMap modelMap) {
		
		int numberOfItems = landDAO.countItemsByName(search);
		int numberOfPages = (int) Math.ceil(numberOfItems * 1.0 / Defines.ROW_COUNT); // * 1.0 de ep kieu ve so thuc
		
		
		if (page == null) {
			page = 1;
		}else if (page < 1){
			return "redirect:/admin/land/search/?page=1";
		}else if (page > numberOfPages){
			return "redirect:/admin/land/search?page="+numberOfPages;
		}
		
		
		int offset = (page - 1 ) * Defines.ROW_COUNT;
		
		List<Land> listlandSearch = landDAO.getItemsSearch(search,offset);
		modelMap.addAttribute("listlandSearch", listlandSearch);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("numberOfPages", numberOfPages);
		
		return "cland.admin.land.search";
	}
}