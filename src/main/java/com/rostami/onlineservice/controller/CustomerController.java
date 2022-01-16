package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping
    public ModelAndView saveCustomer(@ModelAttribute Customer customer){
        customerService.save(customer);
        return new ModelAndView("redirect:");
    }
}
