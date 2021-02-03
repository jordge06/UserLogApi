package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        final String insert = "INSERT INTO person (id, name) VALUES (?, ?)";
        return jdbcTemplate.update(insert, id, person.getName());
        //return 0;
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        final String selectById = "SELECT id, name FROM person WHERE id = ?";
        //return jdbcTemplate.queryForObject(selectById, new PersonMapper, id);
        Person person = jdbcTemplate.queryForObject(
                selectById,
                (resultSet, i) -> new Person(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("name")),
                id);
        return Optional.ofNullable(person);
    }

    @Override
    public List<Person> getAllPerson() {
        final String selectAll = "SELECT id, name FROM person";
        //return jdbcTemplate.query(selectAll, new PersonMapper());
        return jdbcTemplate.query(selectAll, (resultSet, i) -> {
            final UUID id = UUID.fromString(resultSet.getString("id"));
            final String name = resultSet.getString("name");
            return new Person(id, name);
        });
    }

    @Override
    public int deletePersonById(UUID id) {
        final String delete = "DELETE FROM person WHERE id = ?";
        int status =  jdbcTemplate.update(delete, id);
        if (status != 0) {
            System.out.println("Person Delete for ID: " + id);
        } else {
            System.out.println("No such Person With ID: " + id);
        }
        return status;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final String update = "UPDATE person SET name = ? WHERE id = ?";
        int status = jdbcTemplate.update(update, person.getName(), id);
        if (status != 0) {
            System.out.println("Person Update for ID: " + id);
        } else {
            System.out.println("No such Person With ID: " + id);
        }
        return status;
    }

//    private static final class PersonMapper implements RowMapper<Person> {
//        @Override
//        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
//            Person person = new Person(UUID.fromString(resultSet.getString("id")),
//                    resultSet.getString("name"));
//            return person;
//        }
//    }
}
