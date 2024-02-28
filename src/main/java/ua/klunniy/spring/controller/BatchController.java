package ua.klunniy.spring.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.klunniy.spring.service.PersonService;

/**
 * @author Serhii Klunniy
 */
@Controller
@RequestMapping("/test-batch-update")
@NoArgsConstructor
public class BatchController {

    private PersonService personService;

    @Autowired
    public BatchController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch() {
        personService.testMultipleUpdate();
        return "batch/index";
    }

    @GetMapping("/with")
    public String withBatch() {
        personService.testBatchUpdate();
        return "batch/index";
    }

}
