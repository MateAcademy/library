package ua.klunniy.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.service.PersonService;
import ua.klunniy.spring.util.PersonValidator;

import javax.validation.Valid;

/**
 * REST описывает то какие URLы и HTTP методы у нас должны быть для взаимодействия с данными
 *
 *
 *   С GET запросом вот по этому URL мы получим все записи:
 *   GET     /posts               Получаем все записи(READ)
 *
 *   GET     /posts/:id          Получаем одну запись(READ)
 *   DELETE  /posts/:id          Удаляем запись(DELETE)
 *
 *   GET     /posts/new           HTML форма создания записи
 *   POST    /posts               Создаем новую запись(CREATE)
 *
 *   GET     /posts/:id/edit     HTML форма редактирования записи
 *   PATCH   /posts/:id          Обновляем запись(UPDATE)
 *
 * */
@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personService.index());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") long id, Model model) {
// Получим одного человека по id из DAO и передадим на отображение в представление
        model.addAttribute("person", personService.show(id));
        return "/people/show";
    }

// по адресу /people/new вернется html форма создания человека

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }


    @PostMapping("/new")
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/people/new";
        }

        personService.save(person);
        return "/people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") long id) {
        model.addAttribute("person", personService.show(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }

        personService.update(id, person);
        return "/people/show";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }

}

//    @GetMapping("/new")
//    public String newPeople(Model model) {
//        model.addAttribute("person", new Person());
//        return "/people/new";
//    }


// create
//    @PostMapping()
//    public String addNewPeopleInDB(@RequestParam(value = "name", required = false) String name,
//                                   @RequestParam(value = "surname", required = false) String surname,
//                                   @RequestParam(value = "email", required = false) String email,
//                                   Model model) {
//
//        Person person = new Person();
//
//        person.setName(name);
//        person.setSurname(surname);
//        person.setEmail(email);
//
//        model.addAttribute("person", person);
//
//        personService.add(name, surname, email);
//        return "redirect:/people";
//        return "people/show";
//    }
