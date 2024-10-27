package com.dev2ever.junit5demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/persons")
public class PersonControllerTemplate {

    private final PersonJdbcTemplate personJdbcTemplate;

    public PersonControllerTemplate(PersonJdbcTemplate personJdbcTemplate) {
        this.personJdbcTemplate = personJdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personJdbcTemplate.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personJdbcTemplate.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        int result = personJdbcTemplate.save(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePerson(
            @PathVariable Long id, @RequestBody Person person) {
        person.setId(id);
        int result = personJdbcTemplate.update(person);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        int result = personJdbcTemplate.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
