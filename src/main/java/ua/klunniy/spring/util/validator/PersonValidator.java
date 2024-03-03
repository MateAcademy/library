package ua.klunniy.spring.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.service.PeopleService;

import java.util.Objects;

/**
 * @author Serhii Klunniy
 */
@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    //Здесь я реализовал логику - проверку из Бд email, если нового пользователя создаю то в БД
//проверяется есть ли такой емайл, а вот если обновляю пользователя из БД то емейл в БД проверяется только если в обновленном он изменен
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (person.getId() == null) {
            //save - значит добавляю нового пользователя
            if (peopleService.showByEmail(person.getEmail()).isPresent()) {
                errors.rejectValue("email", "", "Error, this email is present in database");
            }


        } else {
            //update - значит обновляю нового пользователя
            Person personFromDb = peopleService.show(person.getId());

            if (peopleService.showByEmail(person.getEmail()).isPresent() && !Objects.equals(personFromDb.getEmail(), person.getEmail())) {
                errors.rejectValue("email", "", "Error, this email is present in database");
            }

//            if (!(person.getEmail().equals(personFromDb.getEmail())) && personService.showByEmail(person.getEmail()).isPresent()) {
//                errors.rejectValue("email", "", "Error, this email is present in database");
//            }

//            if (person.getFirstName().equals(personFromDb.getFirstName()) && person.getLastName().equals(personFromDb.getLastName())
//                    && person.getPatronymic().equals(personFromDb.getPatronymic())) {
//            } else {
//                String firstName = person.getFirstName();
//                String lastName = person.getLastName();
//                String patronymic = person.getPatronymic();
//                if (personService.show(firstName, lastName, patronymic)) {
//                    errors.rejectValue("firstName", "", "Error, this firstName, lastName, patronymic is present in database");
//                }
        }
    }


//        if (personFromDb != null) {
//
//
//        } else {
////проверку сделать на длину емейла
//            if ((personService.showByEmail(person.getEmail()).isPresent())) {
//                errors.rejectValue("email", "", "Error, this email is present in database");
//            }
//
//            if ((personService.showByAddress(person.getAddress()).isPresent())) {
//                errors.rejectValue("address", "", "Error, this address is present in database");
//            }
//
//            if ((personService.showSuchPerson(person).isPresent())) {
//                errors.rejectValue("firstName", "", "Error, this Person is present in database");
//            }
    //       }
//    }

}
