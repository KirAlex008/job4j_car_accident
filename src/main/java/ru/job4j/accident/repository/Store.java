package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;

public interface Store {
    public Accident save(Accident accident);
    public List<Accident> getAll();
    public void update(Accident accident);
    public Accident findAccidentById(Integer id);
    public void delete(Accident accident);
    public AccidentType findTypeById(Integer id);
    public Rule findRuleById(String id);
    public List<Rule> getAllRule();
    public List<AccidentType> getAllAccidentType();
    //findAccidentType(id)
}
