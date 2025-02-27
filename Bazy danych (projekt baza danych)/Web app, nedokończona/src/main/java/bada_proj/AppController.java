package bada_proj;

import java.util.List;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController implements WebMvcConfigurer {

	// Pr4 Dla Dom Seniora
	@Autowired
	private Dom_Seniora_DAO domSenioraDao;

	@RequestMapping(value = {"/index", "/"})
	public String viewHomePage(Model model)
	{

		List<Dom_Seniora> listDomSeniora = domSenioraDao.list();
		model.addAttribute("listDomSeniora", listDomSeniora);

		return "index";
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/main").setViewName("main");
		registry.addViewController("/login").setViewName("login");

		registry.addViewController("/main_admin").setViewName("admin/main_admin");
		registry.addViewController("/main_user").setViewName("user/main_user");
		registry.addViewController("/new_adres_form").setViewName("new_adres_form");
	}

	@Controller
	public class DashboardController
	{
		@RequestMapping
				("/main")
		public String defaultAfterLogin
				(HttpServletRequest request) {
			if
			(request.isUserInRole ("ADMIN")) {
				return "redirect:/main_admin";
			}
			else if
			(request.isUserInRole ("USER")) {
				return "redirect:/main_user";
			}
			else
			{
				return "redirect:/index";
			}
		}
	}

//	@RequestMapping(value={"/main_admin"})
//	public String showAdminPage(Model model) {
//		return "admin/main_admin";
//	}
//
//	@RequestMapping(value={"/main_user"})
//	public String showUserPage(Model model) {
//		return "user/main_user";
//	}




//	@RequestMapping("/")
//	public String viewHomePage(@RequestParam(name = "table", required = false, defaultValue = "adresy") String table, Model model) {
//		if ("adresy".equals(table)) {
//			List<Adres> listAdres = adresDao.list();
//			model.addAttribute("listAdres", listAdres);
//			return "adresy";
//		} else if ("dom_seniora".equals(table)) {
//			List<Dom_Seniora> listDomSeniora = domSenioraDao.list();
//			model.addAttribute("listDomSeniora", listDomSeniora);
//			return "domy-seniora";
//		}
//		return "error"; // Możesz obsłużyć błędną wartość parametru
//	}

//	@RequestMapping("/")
//	public String viewdefaultPage() {
//		return "/login";
//	}

	@RequestMapping("/new_DomSeniora")
	public String showNewFormDomSeniora(Model modelDomSeniora) {
		Dom_Seniora dom_Seniora = new Dom_Seniora();
		modelDomSeniora.addAttribute("dom_Seniora", dom_Seniora);

		return "new_dom_seniora_form";
	}

	@RequestMapping(value = "/save_DomSeniora", method = RequestMethod.POST)
	public String saveDomSeniora(@ModelAttribute("dom_Seniora") Dom_Seniora dom_Seniora) {
		domSenioraDao.save(dom_Seniora);

		return "redirect:/";
	}

	@RequestMapping("/edit_DomSeniora/{id}")
	public ModelAndView showEditFormDomSeniora(@PathVariable(name = "id") int idDomSeniora) {
		ModelAndView mav = new ModelAndView("edit_form");
		Dom_Seniora dom_Seniora = domSenioraDao.get(idDomSeniora);
		mav.addObject("dom_seniora", dom_Seniora);

		return mav;
	}
	@RequestMapping(value="/update_DomSeniora", method = RequestMethod.POST)
	public String updateDomSeniora(@ModelAttribute("dom_Seniora") Dom_Seniora dom_Seniora){
		domSenioraDao.update(dom_Seniora);

		return "redirect:/";
	}
	
	@RequestMapping("/delete_DomSeniora/{id}")
	public String deleteDomSeniora(@PathVariable(name = "id") int idDomSeniora) {
		domSenioraDao.delete(idDomSeniora);
		
		return "redirect:/";
	}
	
	//Pr4 dla Adresy
	@Autowired
	private Adres_DAO adresDao;


	@RequestMapping("/new_adres_form")
	public String showNewFormAdres(Model model) {
		Adres adres = new Adres();
		model.addAttribute("adres", adres);

		return "new_adres_form";
	}

	@RequestMapping(value = "/save_adres", method = RequestMethod.POST)
	public String saveAdres(@ModelAttribute("adres") Adres adres) {
		adresDao.save(adres);

		return "redirect:/";
	}

	@RequestMapping("/edit_adres/{id_adresu}")
	public ModelAndView showEditFormAdres(@PathVariable(name = "id_adresu") int id_adresu) {
		ModelAndView mav = new ModelAndView("edit_adres_form");
		Adres adres = adresDao.get(id_adresu);
		mav.addObject("adres", adres);

		return mav;
	}
	@RequestMapping(value="/update_adres", method = RequestMethod.POST)
	public String updateAdres(@ModelAttribute("adres") Adres adres){
		adresDao.update(adres);

		return "redirect:/";
	}
	
	@RequestMapping("/delete_adres/{id_adresu}")
	public String deleteAdres(@PathVariable(name = "id_adresu") int id_adresu) {
		adresDao.delete(id_adresu);
		
		return "redirect:/";
	}
}
