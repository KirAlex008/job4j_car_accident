package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentJdbcTemplate implements Store {

    private final AtomicInteger id = new AtomicInteger(2);
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        Accident accident = new Accident();
        accident.setName("name1");
        accident.setText("Text1");
        accident.setAddress("Address1");
        int currId = id.incrementAndGet();
        accident.setId(currId);
        save(accident);
    }

    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("insert into accident (name, text, address) values (?, ?, ?)",
                    new String[] {"id"});
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            return statement;
        }, keyHolder);
        System.out.println(accident.toString());
        return accident;
    }

    public List<Accident> getAll() {
        return jdbc.query("select id, name, text, address from accident",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    return accident;
                });
    }

    @Override
    public void update(Accident accident) {
        jdbc.update(
                "update accident set name = ?, text = ?, address = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getId());
    }

    @Override
    public Accident findById(Integer id) {
        return jdbc.queryForObject("select id, name, text, address from accident where id = ?",
                (resultSet, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(resultSet.getInt("id"));
                    accident.setName(resultSet.getString("name"));
                    accident.setText(resultSet.getString("text"));
                    accident.setAddress(resultSet.getString("address"));
                    return accident;
                },
                id);
    }

    @Override
    public void delete(Accident accident) {
        jdbc.update(
                "delete from accident where id = ?",
                accident.getId());
    }
}
