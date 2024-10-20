package com.dev2ever.junit5demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Service class for managing Person entities.
 */
@Service
public class PersonService {

    private final PersonRepository personRepository;

    // Constructor injection
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        logPerson(person);
        return personRepository.save(person);
    }

    /**
     * Logs the details of a Person entity.
     *
     * @param person the Person to log
     */
    public void logPerson(Person person) {
        System.out.println("Logging Person:");
        System.out.println("ID: " + person.getId());
        System.out.println("First Name: " + person.getFirstName());
        System.out.println("Last Name: " + person.getLastName());
    }

    /**
     * Updates an existing Person entity with new values.
     *
     * @param id the ID of the Person to update
     * @param updatedPerson the Person object containing updated values
     * @return the updated Person entity
     * @throws ResourceNotFoundException if no Person is found with the given ID
     */
    public Person update(Long id, Person updatedPerson) {
        return personRepository.findById(id).map(person -> {
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            return personRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
    }

    /**
     * Deletes a Person entity by its ID.
     *
     * @param id the ID of the Person to be deleted
     * @throws ResourceNotFoundException if no Person is found with the given ID
     */
    public void delete(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
        personRepository.delete(person);
    }
}

