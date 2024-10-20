package com.dev2ever.junit5demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService(personRepository);
    }

    @Test
    void findAll_WhenNoRecord() {
        when(personRepository.findAll()).thenReturn(new ArrayList<>()); // mocking Repository's findAll method

        List<Person> result = personService.findAll();

        assertThat(result).isEmpty();
    }

    /**
     * Test method that verifies the `findAll` method of the `PersonService` class
     * when there are multiple `Person` records in the repository.
     *
     * The method mocks the behavior of the `PersonRepository`'s `findAll` method
     * to return a predefined list of multiple `Person` objects. It then calls
     * the `findAll` method on the `PersonService` and asserts that the returned
     * list has a size of 2 and contains the correct `Person` objects with the
     * expected first names and last names.
     */
    @Test
    void findAll_WhenMultipleRecords() {
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        personList.add(person1);
        personList.add(person2);

        when(personRepository.findAll()).thenReturn(personList); // mocking Repository's findAll method

        List<Person> result = personService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(0).getLastName()).isEqualTo("Doe");
        assertThat(result.get(1).getFirstName()).isEqualTo("Jane");
        assertThat(result.get(1).getLastName()).isEqualTo("Doe");
    }
    @Test
    void save_WhenValidPerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        when(personRepository.save(any(Person.class))).thenReturn(person); // mocking Repository's save method

        Person result = personService.save(person);

        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    @Test
    void save_WhenInvalidPerson() {
        Person person = new Person();
        // Invalid person object, no name set, assuming validators in place

        assertThrows(ConstraintViolationException.class, () -> personService.save(person));
    }
}

