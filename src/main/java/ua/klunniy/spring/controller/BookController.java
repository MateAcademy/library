package ua.klunniy.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.klunniy.spring.models.Book;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.service.BookService;
import ua.klunniy.spring.service.PersonService;
import ua.klunniy.spring.util.BookValidator;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookValidator bookValidator;

    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator, PersonService personService) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookService.index());
        return "/book/index";
    }

    @GetMapping("/{id}")
    public String showBookById(@PathVariable("id") long id, Model model, HttpSession httpSession) {
        Book book = bookService.show(id);
        model.addAttribute("book", book);
        Long personIdWhoGetTheBook = book.getPersonId();
        if (personIdWhoGetTheBook != null) {
            Person personWhoGetThisBook = personService.show(personIdWhoGetTheBook);
            model.addAttribute("person", personWhoGetThisBook);
            model.addAttribute("condition", "people present");
        } else {
            model.addAttribute("condition", null);
            List<Person> index = personService.index();
            model.addAttribute("people", index );
            model.addAttribute("person", new Person() );
            httpSession.setAttribute("book", book);
        }
        return "/book/show";
    }

    @PatchMapping("/{id}/release")
    public String freeTheBook(@PathVariable("id") long bookId) {
        bookService.releaseTheBookFromThePerson(bookId);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/book/new";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/book/new";
        }

        bookService.save(book);
        return "/book/show";
    }

    @PatchMapping("/appoint")
    public String appoint(@ModelAttribute("person") Person person, HttpSession httpSession, Model model) {
        Book book = (Book) httpSession.getAttribute("book");
        Long personId = person.getId();
        bookService.setPersonId(book.getBookId(), personId);
        model.addAttribute("book", book);
        return "/book/show";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") long id) {
        model.addAttribute("book", bookService.show(id));
        return "/book/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("id") long id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/book/edit";
        }

        book.setBookId(id);
        bookService.update(id, book);
        return "/book/show";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book";
    }

}
