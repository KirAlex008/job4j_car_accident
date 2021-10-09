package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;


@Controller
public class AccidentControl {

    private final AccidentJdbcTemplate accidents;

    public AccidentControl(AccidentJdbcTemplate accidents) {
        this.accidents = accidents;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidents.getAll());
        model.addAttribute("rules", accidents.getAllRule());
        model.addAttribute("types", accidents.getAllAccidentType());
        return "accident/create";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidents.findAccidentById(id));
        model.addAttribute("types", accidents.getAllAccidentType());
        model.addAttribute("rules", accidents.getAllRule());
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, @RequestParam("type.id") int id, HttpServletRequest req) {
        AccidentType type = accidents.findTypeById(id);
        accident.setType(type);
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet();
        for (String el : ids) {
            rules.add(accidents.findRuleById(el));
        }
        accident.setRules(rules);
        accidents.save(accident);
        return "redirect:/";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, @RequestParam("type.id") int id, HttpServletRequest req) {
        AccidentType type = accidents.findTypeById(id);
        accident.setType(type);
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet();
        for (String el : ids) {
            rules.add(accidents.findRuleById(el));
        }
        accident.setRules(rules);
        accidents.update(accident);
        return "redirect:/";
    }
}
