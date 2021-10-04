package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements Store {

    private final HashMap<Integer, Accident> store = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(2);

    public AccidentMem() {
        Accident accident = new Accident();
        int currId = id.incrementAndGet();
        accident.setId(currId);
        store.put(accident.getId(), accident);
    }
    @Override
    public Accident save(Accident accident) {
        int currId = id.incrementAndGet();
        accident.setId(currId);
        store.putIfAbsent(accident.getId(), accident);
        return accident;
    }

    @Override
    public List<Accident> getAll() {
        List<Accident> copyOfAccidents = new ArrayList<>();
        for (var el : store.values()) {
            copyOfAccidents.add(el);
        }
        return copyOfAccidents;
    }

    @Override
    public void update(Accident accident) {
        store.replace(accident.getId(), accident);
    }

    @Override
    public Accident findById(Integer id) {
        return store.get(id);
    }

    @Override
    public void delete(Accident accident) {

    }

}

