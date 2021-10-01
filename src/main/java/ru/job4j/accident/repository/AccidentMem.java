package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final HashMap<Integer, Accident> store = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(3);

    public AccidentMem() {
        Accident accident = new Accident("name1", "text1", "address1");
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
}

