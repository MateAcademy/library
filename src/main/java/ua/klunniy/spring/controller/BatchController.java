package ua.klunniy.spring.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.klunniy.spring.service.PeopleService;

/**
 * @author Serhii Klunniy
 */
@Controller
@RequestMapping("/test-batch-update")
@NoArgsConstructor
public class BatchController {

    private PeopleService peopleService;

    @Autowired
    public BatchController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch() {
        peopleService.testMultipleUpdate();
        return "batch/index";
    }

    @GetMapping("/with")
    public String withBatch() {
        peopleService.testBatchUpdate();
        return "batch/index";
    }

}
