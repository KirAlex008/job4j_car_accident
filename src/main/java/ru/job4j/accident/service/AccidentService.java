package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccidentService {
    private AccidentMem serviceStore;

    public AccidentService(AccidentMem serviceStore) {
        this.serviceStore = serviceStore;
    }

    public List<Accident> getAllAccidents() {
        return new ArrayList<>(this.serviceStore.findAll());
    }

    public List<AccidentType> getAllAccidentTypes() {
        return new ArrayList<>(this.serviceStore.getallAccidentType());
    }

}
