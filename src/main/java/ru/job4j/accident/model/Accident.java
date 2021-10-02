package ru.job4j.accident.model;

import java.util.Objects;


/**
 * В заявлении указывает: адрес, номер машины, описание нарушения и фотографию нарушения.
 */

public class Accident {
    private int id;
    private String name;
    private String text;
    private String address;
    private AccidentType type;

    public Accident(String name, String text, String address) {
        this.name = name;
        this.text = text;
        this.address = address;
    }

    /**
     public static Accident of(String name, String text, String address) {
     Accident accident = new Accident();
     accident.name = name;
     accident.text = text;
     accident.address = address;
     return accident;
     }
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Accident accident = (Accident) o;
        return id == accident.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public AccidentType getType() {
        return type;
    }

    public void setType(AccidentType type) {
        this.type = type;
    }
}
