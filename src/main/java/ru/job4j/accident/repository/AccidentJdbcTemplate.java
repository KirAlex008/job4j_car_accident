package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentJdbcTemplate implements Store {

    private final AtomicInteger id = new AtomicInteger();
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        Accident accident = new Accident();
        accident.setName("name1");
        accident.setText("Text1");
        accident.setAddress("Address1");
        int currId = id.incrementAndGet();
        accident.setId(currId);
        AccidentType type = AccidentType.of(1, "type1");
        accident.setType(type);
        Rule rule1 = Rule.of(1, "rule1");
        Rule rule2 = Rule.of(2, "rule2");
        Set<Rule> ruleSet = new HashSet<>();
        ruleSet.add(rule1);
        ruleSet.add(rule2);
        accident.setRules(ruleSet);
        save(accident);
    }

    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("insert into accident (name, text, address, type_id) values (?, ?, ?, ?)",
                    new String[] {"id"});
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, keyHolder);
        for (var el : accident.getRules()) {
            Rule rule = findRuleById(Integer.toString(el.getId()));
            /**this.jdbc.update(
                    "insert into accident_rule (accident_id, rule_id) values (?, ?)",
                    accident.getId(), rule.getId());
             */

            KeyHolder keyHolder2 = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                PreparedStatement statement = connection.prepareStatement("insert into accident_rule (accident_id, rule_id) values (?, ?)",
                        new String[] {"id"});
                statement.setInt(1, accident.getId());
                statement.setInt(2, rule.getId());
                return statement;
            }, keyHolder2);
    }
        return accident;
    }
    public List<Accident> getAll() {
        return jdbc.query("select id, name, text, address, type_id from accident",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    Integer typeId = rs.getInt("type_id");
                    accident.setType(findTypeById(typeId));
                    Set<Rule> ruleSet = new HashSet<>();
                    int num = accident.getId();
                    List<Integer> list = this.jdbc.query("select rule_id from accident_rule where accident_id = ?",
                    (resultSet, rowNum) -> resultSet.getInt("rule_id"), num);
                    for (var el : list) {
                        ruleSet.add(findRuleById(Integer.toString(el)));
                    }
                    accident.setRules(ruleSet);
                    return accident;
                });
    }

    @Override
    public void update(Accident accident) {
        jdbc.update(
                "update accident set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        for (var el : accident.getRules()) {
            Rule rule = findRuleById(Integer.toString(el.getId()));
            jdbc.update(
                    "insert into accident_rule (accident_id, rule_id) values (?, ?)",
                    accident.getId(), rule.getId());
        }
    }

    @Override
    public Accident findAccidentById(Integer id) {
        Accident accident1 = jdbc.queryForObject("select id, name, text, address, type_id from accident where id = ?",
                (resultSet, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(resultSet.getInt("id"));
                    accident.setName(resultSet.getString("name"));
                    accident.setText(resultSet.getString("text"));
                    accident.setAddress(resultSet.getString("address"));
                    accident.setType(findTypeById(resultSet.getInt("type_id")));
                    return accident;
                }, id);
        Set<Rule> ruleSet = new HashSet<>();
        List<Integer> list = this.jdbc.query("select rule_id from accident_rule where accident_id = ?",
                (resultSet, rowNum) -> resultSet.getInt("rule_id"), id);
        for (var el : list) {
            ruleSet.add(findRuleById(Integer.toString(el)));
        }
        accident1.setRules(ruleSet);
        return accident1;
    }

    @Override
    public void delete(Accident accident) {
        jdbc.update(
                "delete from accident where id = ?",
                accident.getId());
    }

    @Override
    public AccidentType findTypeById(Integer id) {
        return jdbc.queryForObject("select id, name from type where id = ?",
                (resultSet, rowNum) -> {
                    AccidentType newType = new AccidentType();
                    newType.setId(resultSet.getInt("id"));
                    newType.setName(resultSet.getString("name"));
                    return newType;
                }, id);
    }

    @Override
    public Rule findRuleById(String id) {
        int iId = Integer.parseInt(id);
        return jdbc.queryForObject("select id, name from rule where id = ?",
                (resultSet, rowNum) -> {
                    Rule newRule = new Rule();
                    newRule.setId(resultSet.getInt("id"));
                    newRule.setName(resultSet.getString("name"));
                    return newRule;
                }, iId);
    }

    @Override
    public List<Rule> getAllRule() {
        return jdbc.query("select id, name from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    @Override
    public List<AccidentType> getAllAccidentType() {
        return jdbc.query("select id, name from type",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }
}
