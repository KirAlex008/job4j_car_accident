package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.service.AccidentService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexControl {
    private AccidentService service;

    public IndexControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("accidents", this.service.getAllAccidents());
        model.addAttribute("types", this.service.getAllAccidentTypes());
        model.addAttribute("rules", this.service.getAllRules());
        return "index";
    }
}
