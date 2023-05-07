package msu.cmc.jaweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPage {

    @GetMapping(value = "/error")
    public String error() {
        return "error";
    }

}
