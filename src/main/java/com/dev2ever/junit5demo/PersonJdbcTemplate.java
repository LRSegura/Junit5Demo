package com.dev2ever.junit5demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PersonJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;
    private final PersonRowMapper personRowMapper;

    public PersonJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.personRowMapper = new PersonRowMapper();
    }

    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, personRowMapper);
    }

    public Person findById(Long id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, personRowMapper);
    }

    public int save(Person person) {
        String sql = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        return jdbcTemplate.update(sql, person.getFirstName(), person.getLastName());
    }

    public int update(Person person) {
        String sql = "UPDATE person SET first_name = ?, last_name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), person.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM person WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static class PersonRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setId(rs.getLong("id"));
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            return person;
        }
    }
}
