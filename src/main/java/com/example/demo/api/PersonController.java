package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("submitPerson")
    public void addPerson(@Valid @NonNull @RequestBody Person person) {
        personService.addPerson(person);
    }

    @GetMapping("/getPerson")
    public List<Person> getAllPerson() {
        return personService.getAllPeople();
    }

    @GetMapping("getPersonWith/{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        return personService.getPersonById(id).orElse(null);
    }

    @DeleteMapping("deletePerson/{id}")
    public int deletePerson(@PathVariable("id") UUID id) {
        return personService.deletePerson(id);
    }

    @PutMapping("updatePerson/{id}")
    public int updatePerson(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }
}
