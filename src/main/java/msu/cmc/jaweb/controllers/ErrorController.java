package msu.cmc.jaweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping(value = "/error")
    public String error() {
        return "error";
    }
    
    @GetMapping(value = "/")
    public String index() {
        return "index";
    }
}
