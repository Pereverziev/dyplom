package app.cache;

import app.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface DataService {

    void add(Person person);

    void update(Person person, String id);

    Person getById(String id);

    Collection<Person> getAll(Optional<String> statusFilter, Optional<Boolean> driverLicenseFilter);

    Person deleteById(String id);

}