package ua.klunniy.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.klunniy.spring.models.Book;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.service.BookService;
import ua.klunniy.spring.service.PeopleService;
import ua.klunniy.spring.util.validator.PersonValidator;

import javax.validation.Valid;
import java.util.List;

/**
 * REST описывает то какие URLы и HTTP методы у нас должны быть для взаимодействия с данными
 * <p>
 * <p>
 * С GET запросом вот по этому URL мы получим все записи:
 * GET     /posts               Получаем все записи(READ)
 * <p>
 * GET     /posts/:id          Получаем одну запись(READ)
 * DELETE  /posts/:id          Удаляем запись(DELETE)
 * <p>
 * GET     /posts/new           HTML форма создания записи
 * POST    /posts               Создаем новую запись(CREATE)
 * <p>
 * GET     /posts/:id/edit     HTML форма редактирования записи
 * PATCH   /posts/:id          Обновляем запись(UPDATE)
 */
@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final BookService bookService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleService.index());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") long personId, Model model) {
        Person show = peopleService.show(personId);
        model.addAttribute("person", show);
        List<Book> bookList = bookService.getBooksByPersonId(personId);
        if (bookList.isEmpty()) {
            model.addAttribute("condition", null);
        } else {
            model.addAttribute("condition", "text");
            model.addAttribute("bookList", bookList);
        }
        return "/people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping("/new")
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/people/new";
        }

        try {
            personValidator.validate(person, bindingResult);
            if (bindingResult.hasErrors()) {
                return "/people/new";
            }
            peopleService.save(person);
        } catch (Exception e) {
            System.out.println("Error in createPerson");
            return "/people/new";
        }

        return "/people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") long id) {
        model.addAttribute("person", peopleService.show(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }

        try {
            personValidator.validate(person, bindingResult);
            if (bindingResult.hasErrors()) {
                return "/people/edit";
            }
        } catch (Exception e) {
            System.out.println("Error in createPerson");
            return "/people/edit";
        }

        peopleService.update(id, person);
        return "/people/show";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") long id) {
        peopleService.delete(id);
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
