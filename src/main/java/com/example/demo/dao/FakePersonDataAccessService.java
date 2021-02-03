package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{

    private final static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> getAllPerson() {
        return DB;
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        for (Person db: DB) {
            if (id.equals(db.getId())) {
                return Optional.of(db);
            }
        }
        return Optional.empty();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = getPersonById(id);
        if (person.empty().isPresent()) return 0;
        else {
            DB.remove(person.get());
            return 1;
        }
    }

    @Override
    public int updatePersonById(UUID id, final Person personUpdate) {
        return getPersonById(id).map(p -> {
            int indexOfPerson = DB.indexOf(p);
            if (indexOfPerson >= 0) {
                DB.set(indexOfPerson, new Person(id, personUpdate.getName()));
                return 1;
            } return 0;
        }).orElse(0);
    }
}
