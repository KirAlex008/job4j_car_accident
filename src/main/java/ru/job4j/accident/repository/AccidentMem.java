package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class AccidentMem {

    private final HashMap<Integer, Accident> store = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(2);
    private Map<Integer, Rule> rules = new HashMap<>();
    private Map<Integer, AccidentType> types = new HashMap<>();

    public AccidentMem() {
        AccidentType type = AccidentType.of(1, "Две машины");
        Accident accident = new Accident();
        Set<Rule> ruleSet = new HashSet();
        Rule rule1 = Rule.of(3, "Статья. 3");
        Rule rule2 = Rule.of(1, "Статья. 1");
        ruleSet.add(rule1);
        ruleSet.add(rule2);
        accident.setName("name1");
        accident.setText("Text1");
        accident.setAddress("Address1");
        accident.setType(type);
        accident.setRules(ruleSet);
        int currId = id.incrementAndGet();
        accident.setId(currId);
        store.put(accident.getId(), accident);
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
    }


    public Accident save(Accident accident) {
        int currId = id.incrementAndGet();
        accident.setId(currId);
        store.putIfAbsent(accident.getId(), accident);
        return accident;
    }



    public List<Accident> getAll() {
        List<Accident> copyOfAccidents = new ArrayList<>();
        for (var el : store.values()) {
            copyOfAccidents.add(el);
        }
        return copyOfAccidents;
    }


    public void update(Accident accident) {
        store.replace(accident.getId(), accident);
    }


    public Accident findAccidentById(Integer id) {
        return null;
    }



    public void delete(Accident accident) {

    }

    public AccidentType findTypeById(Integer id) {
        return null;
    }


    public Rule findRuleById(String id) {
        return null;
    }


    public List<Rule> getAllRule() {
        return null;
    }


    public List<AccidentType> getAllAccidentType() {
        return null;
    }

    public AccidentType findAccidentType(int id) {
        return types.get(id);
    }

    public List<AccidentType> getallAccidentType() {
        return types.values().stream().collect(Collectors.toList());
    }

    public Rule findRUleType(String id) {
        int iId = Integer.parseInt(id);
        return rules.get(iId);
    }

    public List<Rule> getallRule() {
        return rules.values().stream().collect(Collectors.toList());
    }


    public Map<Integer, Rule> getRules() {
        return rules;
    }

    public Map<Integer, AccidentType> getTypes() {
        return types;
    }

}

