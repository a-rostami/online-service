package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("")
    public ModelAndView signupSignIn(ModelAndView model){
        model.setViewName("customer-signup");
        return model;
    }

    @PostMapping("/saveCustomer")
    public ModelAndView saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/editCustomer")
    public ModelAndView editCustomer(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        Customer customer = customerService.findById(id);
        ModelAndView model = new ModelAndView("customer-form");
        model.addObject("customer", customer);
        return model;
    }

    @GetMapping("/deleteCustomer")
    public ModelAndView deleteCustomer(HttpServletRequest request){
        Long id = Long.valueOf(request.getParameter("id"));
        customerService.deleteById(id);
        return new ModelAndView("redirect:/");
    }
}
