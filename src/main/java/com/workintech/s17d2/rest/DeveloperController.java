package com.workintech.s17d2.rest;

import jakarta.annotation.PostConstruct;
import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.workintech.s17d2.tax.Taxable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private Taxable developerTax;
    public Map<Integer, Developer> developers;

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
        developers.put(100, new Developer(100, "Ali Can", 30000.0, Experience.JUNIOR));
    }

    @Autowired
    public DeveloperController(Taxable developerTax) {
        this.developerTax = developerTax;
    }

    @GetMapping
    public List<Developer> getDevelopers() {
        return developers.values().stream().toList();
    }
    @GetMapping("/{id}")
    public Developer getDeveloper(@PathVariable Integer id) {
        if (developers.containsKey(id)) {
            return developers.get(id);
        } else {
            System.out.println("Belirtilen ID'e ait dev. bulunamadı : " + id);
            return null;

        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Developer postDevelopers(@RequestBody Developer newDeveloper) {
        Double salaryTax = newDeveloper.getSalary();
        switch (newDeveloper.getExperience()) {
            case JUNIOR:
            salaryTax = salaryTax * developerTax.getSimpleTaxRate();
            newDeveloper.setSalary(salaryTax);
            developers.put(newDeveloper.getId(), newDeveloper);
            break;
            case MID:
                salaryTax = salaryTax * developerTax.getMiddleTaxRate();
                newDeveloper.setSalary(salaryTax);
                developers.put(newDeveloper.getId(), newDeveloper);
                break;
            case SENIOR:
                salaryTax = salaryTax * developerTax.getUpperTaxRate();
                newDeveloper.setSalary(salaryTax);
                developers.put(newDeveloper.getId(), newDeveloper);
                break;
        }
        return newDeveloper;
    }

    @PutMapping("/{id}")
    public Developer updateDeveloper(@PathVariable Integer id, @RequestBody Developer developer) {
        developers.put(id, developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    public Developer deleteDeveloper(@PathVariable Integer id) {
        return developers.remove(id);
    }


}
