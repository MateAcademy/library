package ua.klunniy.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.klunniy.spring.models.Person;

import java.util.List;

/**
 * @author Serhii Klunniy
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    List<Person> findByFirstName(String name);

}