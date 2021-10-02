package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final HashMap<Integer, Accident> store = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(3);

    public AccidentMem() {
        AccidentType type = AccidentType.of(1, "Две машины");
        Accident accident = new Accident("name1", "text1", "address1");
        accident.setType(type);
        int currId = id.incrementAndGet();
        accident.setId(currId);
        store.put(accident.getId(), accident);
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
        List<AccidentType> types = createAccidentTypes();
        return types.get(id - 1);
    }

    public List<AccidentType> getallAccidentType() {
        return createAccidentTypes();
    }

    public static List<AccidentType> createAccidentTypes() {
        List<AccidentType> types = new ArrayList<>();
        types.add(AccidentType.of(1, "Две машины"));
        types.add(AccidentType.of(2, "Машина и человек"));
        types.add(AccidentType.of(3, "Машина и велосипед"));
        return types;
    }

}

