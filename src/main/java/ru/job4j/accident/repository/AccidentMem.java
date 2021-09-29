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
    private final AtomicInteger id = new AtomicInteger();

    public AccidentMem() {
        Accident accident = new Accident(id.incrementAndGet(), "name1", "text1", "address1");
        store.put(accident.getId(), accident);
    }

    public boolean add(Accident accident) {
        boolean flag = false;
        Accident rsl = store.putIfAbsent(id.incrementAndGet(), accident);
        if (rsl == null) {
            flag = true;
        }
        return flag;
    }

    public List<Accident> findAll() {
        List<Accident> copyOfAccidents = new ArrayList<>();
        for (var el : store.values()) {
            copyOfAccidents.add(el);
        }
        return copyOfAccidents;
    }
}

