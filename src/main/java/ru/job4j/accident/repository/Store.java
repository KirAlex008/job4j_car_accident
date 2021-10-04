package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;

import java.util.List;

public interface Store {
    public Accident save(Accident accident);
    public List<Accident> getAll();
    public void update(Accident accident);
    public Accident findById(Integer id);
    public void delete(Accident accident);
}
