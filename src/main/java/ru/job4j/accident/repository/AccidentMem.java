package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Accident accident = new Accident("name1", "text1", "address1");
        accident.setType(type);
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

    public boolean create(Accident accident) {
        int currId = id.incrementAndGet();
        accident.setId(currId);
        return store.putIfAbsent(accident.getId(), accident) == null;
    }

    public List<Accident> findAll() {
        List<Accident> copyOfAccidents = new ArrayList<>();
        for (var el : store.values()) {
            copyOfAccidents.add(el);
        }
        return copyOfAccidents;
    }

    public void update(Accident accident) {
        store.replace(accident.getId(), accident);

    }

    public Accident findById(Integer id) {
        return store.get(id);
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

