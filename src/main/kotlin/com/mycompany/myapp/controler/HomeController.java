package com.mycompany.myapp.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("message", "Hello Admin");
        return "home";
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String managerPage(Model model) {
        model.addAttribute("message", "Hello Manager");
        return "home";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPage(Model model) {
        model.addAttribute("message", "Hello User");
        return "home";
    }
}
