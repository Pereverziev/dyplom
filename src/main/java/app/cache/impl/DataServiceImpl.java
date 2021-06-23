package app.cache.impl;

import app.cache.DataService;
import app.model.Person;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DataServiceImpl implements DataService {

    private Map<String, Person> idToPersonMap = new HashMap<>();

    @Override
    public void add(Person person) {
        Optional<Person> personWithTheSameName = idToPersonMap.values().stream().filter(p -> p.getName().equals(person.getName())).findAny();
        if (personWithTheSameName.isPresent()) {
            throw Problem.builder().withStatus(Status.BAD_REQUEST).withDetail("Person with given name already exists").build();
        }
        String identifier = UUID.randomUUID().toString();
        idToPersonMap.put(identifier, person);
        person.setId(identifier);
    }

    @Override
    public void update(Person newPerson, String id) {
        Person person = idToPersonMap.get(id);
        if (person == null) {
            throw Problem.builder().withStatus(Status.BAD_REQUEST).withDetail("Person with id " + id + " doesnt exist").build();
        }
        if (!person.getName().equals(newPerson.getName()) && idToPersonMap.values().stream().anyMatch(p -> p.getName().equals(newPerson.getName()))) {
            throw Problem.builder().withStatus(Status.BAD_REQUEST).withDetail("Person with given name already exists").build();
        }
        newPerson.setId(id);
        idToPersonMap.put(id, newPerson);
    }

    @Override
    public Collection<Person> getAll(Optional<String> statusFilter, Optional<Boolean> driverLicenseFilter) {
        Collection<Person> response = idToPersonMap.values();
        if (statusFilter.isPresent()) {
            response = response.stream().filter(r -> r.getStatus().toString().equals(statusFilter.get())).collect(Collectors.toList());
        }
        if (driverLicenseFilter.isPresent()) {
            response = response.stream().filter(r -> r.getDriverLicense().equals(driverLicenseFilter.get())).collect(Collectors.toList());
        }
        return response;
    }

    @Override
    public Person getById(String id) {
        Person person = idToPersonMap.get(id);
        if (person == null) {
            throw Problem.builder().withStatus(Status.BAD_REQUEST).withDetail("Person with id " + id + " doesnt exist").build();
        }
        return person;
    }

    @Override
    public Person deleteById(String id) {
        Person person = idToPersonMap.get(id);
        if (person == null) {
            throw Problem.builder().withStatus(Status.BAD_REQUEST).withDetail("Person with id " + id + " doesnt exist").build();
        }
        idToPersonMap.remove(id);
        return person;
    }
}
