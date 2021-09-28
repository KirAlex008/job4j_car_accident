package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Arrays;
import java.util.List;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        AccidentMem store = new AccidentMem();
        Accident accident = Accident.of("name1", "text1", "address1");
        store.add(accident);
        List<Accident> accidents = store.findAll();
        model.addAttribute("accidents", accidents);
        return "index";
    }
}
