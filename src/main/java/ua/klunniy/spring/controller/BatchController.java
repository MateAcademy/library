package ua.klunniy.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.klunniy.spring.service.PersonService;

/**
 * @author Serhii Klunniy
 */
@Controller
@RequestMapping("test-batch-update")
public class ButchController {

    private final PersonService personService;

    @Autowired
    public ButchController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String index() {
        return "batch/index";
    }

    @GetMapping("without")
    public String withoutBatch() {
        return "batch/index";
    }

}
