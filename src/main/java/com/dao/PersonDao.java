package com.dao;

import com.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PersonDao implements Dao<Person> {
    private final JdbcTemplate JDBC_TEMPLATE;

    public List<Person> index() {
        return JDBC_TEMPLATE.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return JDBC_TEMPLATE.query(
                        "select * from person where person_id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        JDBC_TEMPLATE.update("insert into person (name, birth_year) values (?, ?)",
                person.getName(),
                person.getBirthYear());
    }

    public void update(int id, Person person) {
        JDBC_TEMPLATE.update(
                "update person set name = ?, birth_year = ? where person_id = ?",
                person.getName(),
                person.getBirthYear(),
                id);
    }

    public void delete(int id) {
        JDBC_TEMPLATE.update("delete from person where person_id = ?", id);
    }
}
