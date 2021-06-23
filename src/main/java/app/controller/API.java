package app.controller;

import app.cache.DataService;
import app.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/webhook")
public class API {

    @Autowired
    private DataService dataService;

    @PostMapping()
    public Person create(@RequestBody Person person) {
        dataService.add(person);
        return person;
    }

    @GetMapping("/{UUID}")
    public Person getById(@PathVariable String UUID) {
        return dataService.getById(UUID);
    }

    @GetMapping
    public Collection<Person> getAll(@RequestParam Optional<String> statusFilter, @RequestParam Optional<Boolean> driverLicenseFilter) {
        return dataService.getAll(statusFilter, driverLicenseFilter);
    }

    @DeleteMapping("/{UUID}")
    public Person deleteById(@PathVariable String UUID) {
        return dataService.deleteById(UUID);
    }

    @PatchMapping(value = "/{UUID}")
    public Person updateById(@PathVariable String UUID, @RequestBody Person newPersonInfo) {
        dataService.update(newPersonInfo, UUID);
        return newPersonInfo;
    }
}

