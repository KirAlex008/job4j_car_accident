package ru.job4j.accident.model;

import org.springframework.stereotype.Component;

import java.util.*;
import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

/**
 * В заявлении указывает: адрес, номер машины, описание нарушения и фотографию нарушения.
 */
@Component
@Entity
@Table(name = "accident")
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;
    private String address;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "type_id")

    private AccidentType type;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "accident_rule",
            joinColumns = {@JoinColumn(name = "accident_id")},
            inverseJoinColumns = {@JoinColumn(name = "rule_id")})
    private Set<Rule> rules = new HashSet<>();

    public Accident() {
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
    }


    /**
    public Accident(String name, String text, String address) {
        this.name = name;
        this.text = text;
        this.address = address;
    }
     */

     public static Accident of(String name, String text, String address, AccidentType type) {
     Accident accident = new Accident();
     accident.name = name;
     accident.text = text;
     accident.address = address;
     accident.type = type;

     return accident;
     }

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

    public Set<Rule> getRules() {
        return rules;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "Accident{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", text='" + text + '\''
                + ", address='" + address + '\''
                + ", type=" + type
                + ", rules=" + rules
                + '}';
    }
}
