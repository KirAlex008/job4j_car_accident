package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final HashMap<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public boolean add(Accident accident) {
        boolean flag = false;
        Accident rsl = accidents.putIfAbsent(id.incrementAndGet(), accident);
        if (rsl == null) {
            flag = true;
        }
        return flag;
    }

    public List<Accident> findAll() {
        List<Accident> copyOfAccidents = new ArrayList<>();
        for (var el : accidents.values()) {
            copyOfAccidents.add(el);
        }
        return copyOfAccidents;
    }
}

