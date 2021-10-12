package ru.job4j.accident.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "accident_rule",
            joinColumns = {@JoinColumn(name = "rule_id")},
            inverseJoinColumns = {@JoinColumn(name = "accident_id")})
    private Set<Accident> accidents = new HashSet<>();

    public Rule() {
    }

    public static Rule of(int id, String name) {
        Rule rule = new Rule();
        rule.id = id;
        rule.name = name;
        return rule;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rule rule = (Rule) o;
        return id == rule.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

/**    @Override
    public String toString() {
        return "Rule{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", accidents=" + accidents
                + '}';
    }*/
}
