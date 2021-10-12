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
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.RuleRepository;
import ru.job4j.accident.repository.TypeRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class AccidentControl {

    private final AccidentRepository accidents;
    private final TypeRepository types;
    private final RuleRepository rules;


    public AccidentControl(AccidentRepository accidents, TypeRepository types, RuleRepository rules) {
        this.accidents = accidents;
        this.types = types;
        this.rules = rules;
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Accident> res = new ArrayList<>();
        accidents.findAll().forEach(res::add);
        model.addAttribute("accidents", res);
        List<AccidentType> allTypes = new ArrayList<>();
        List<Rule> allRules = new ArrayList<>();
        types.findAll().forEach(allTypes::add);
        rules.findAll().forEach(allRules::add);
        model.addAttribute("types", allTypes);
        model.addAttribute("rules", allRules);
        return "accident/create";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        Optional<Accident> res = accidents.findById(id);
        if (res.isPresent()) {
            Accident ac = res.orElse(null);
            model.addAttribute("accident", ac);
        }
        List<AccidentType> allTypes = new ArrayList<>();
        List<Rule> allRules = new ArrayList<>();
        types.findAll().forEach(allTypes::add);
        rules.findAll().forEach(allRules::add);
        model.addAttribute("types", allTypes);
        model.addAttribute("rules", allRules);
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, @RequestParam("type.id") int id, HttpServletRequest req) {
        Optional<AccidentType> type = types.findById(id);
        if (type.isPresent()) {
            accident.setType(type.orElse(null));
        }
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rulesSet = new HashSet();

        for (String el : ids) {
            Optional<Rule> rule = rules.findById(Integer.parseInt(el));
            if (type.isPresent()) {
                rulesSet.add(rule.orElse(null));
            }
        }
        accident.setRules(rulesSet);
        accidents.save(accident);
        return "redirect:/";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, @RequestParam("type.id") int id, HttpServletRequest req) {
        Optional<AccidentType> type = types.findById(id);
        if (type.isPresent()) {
            accident.setType(type.orElse(null));
        }
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rulesSet = new HashSet();

        for (String el : ids) {
            Optional<Rule> rule = rules.findById(Integer.parseInt(el));
            if (type.isPresent()) {
                rulesSet.add(rule.orElse(null));
            }
        }
        accident.setRules(rulesSet);
        accidents.save(accident);
        return "redirect:/";
    }
}
