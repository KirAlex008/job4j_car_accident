package ru.job4j.accident.control;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.RuleRepository;
import ru.job4j.accident.repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexControl {

    private final AccidentRepository accidents;
    private final TypeRepository types;
    private final RuleRepository rules;


    public IndexControl(AccidentRepository accidents, TypeRepository types, RuleRepository rules) {
        this.accidents = accidents;
        this.types = types;
        this.rules = rules;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Accident> res = new ArrayList<>();
        accidents.findAll().forEach(res::add);
        model.addAttribute("accidents", res);
        List<AccidentType> allTypes = new ArrayList<>();
        List<Rule> allRules = new ArrayList<>();
        types.findAll().forEach(allTypes::add);
        rules.findAll().forEach(allRules::add);
        model.addAttribute("types", allTypes);
        model.addAttribute("rules", allRules);
        return "index";
    }
}
