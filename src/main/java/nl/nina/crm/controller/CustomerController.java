package nl.nina.crm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import nl.nina.crm.model.Customer;
import nl.nina.crm.service.CustomerService;

@Controller
public class CustomerController implements WebMvcConfigurer {

	@Autowired
	private CustomerService customerService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/list");
        registry.addViewController("/login").setViewName("login");
    }

	@GetMapping("/search")
	public String searchCustomers(@RequestParam("searchName") String searchName, Model model) {
		List<Customer> customers = customerService.searchCustomers(searchName);
		model.addAttribute("customers", customers);
		return "list-customers";
	}

	@GetMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> customers = customerService.getCustomers();
		model.addAttribute("customers", customers);
		return "list-customers";
	}

	@GetMapping("/showForm")
	public String showForm(@RequestParam(name = "customerId", required = false) Integer id, Model model) {
		Customer customer = null;
		
		if(id == null) {
			customer = new Customer();
		} else {
			customer = customerService.getCustomer(id);
		}
		
		model.addAttribute("customer", customer);
		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "customer-form";
		}

		customerService.saveCustomer(customer);
		return "redirect:/list";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		String status = null;
		
		try {
			customerService.deleteCustomer(id);
		} catch (EmptyResultDataAccessException e) {
			status = "error";
			e.printStackTrace();
		}
		return "redirect:/list?" + status;
	}
}
