package ua.klunniy.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.service.PersonService;

/**
 * @author Serhii Klunniy
 */
@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
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

        Person personFromDb = personService.show(person.getId());

        if (personFromDb != null) {
            if (!(person.getEmail().equals(personFromDb.getEmail())) && personService.showByEmail(person.getEmail()).isPresent()) {
                errors.rejectValue("email", "", "Error, this email is present in database");
            }

            if (!(person.getAddress().equals(personFromDb.getAddress())) && personService.showByAddress(person.getAddress()).isPresent()) {
                errors.rejectValue("address", "", "Error, this address is present in database");
            }
        } else {
            if ((personService.showByEmail(person.getEmail()).isPresent())) {
                errors.rejectValue("email", "", "Error, this email is present in database");
            }

            if ((personService.showByAddress(person.getAddress()).isPresent())) {
                errors.rejectValue("address", "", "Error, this address is present in database");
            }
        }
    }

}
