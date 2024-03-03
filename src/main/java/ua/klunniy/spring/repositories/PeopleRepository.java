package ua.klunniy.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.klunniy.spring.models.Person;

/**
 * @author Serhii Klunniy
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {

}