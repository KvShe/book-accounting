package com.services;

import com.models.Book;
import com.models.Person;
import com.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;

//    public List<Person> findAll(Sort var) {
//        return peopleRepository.findAll(var);
//    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Person findPersonAndBooksById(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
//            checkExpiredBooks(person.get().getBooks());

            person.get().getBooks().forEach(book -> {
                long diffInMillis = Math.abs(book.getTakenAt().getTime() - new Date().getTime());

                if (diffInMillis > 864_000_000) {
                    book.setExpired(true);
                }
            });

            return person.get();
        }

        return null;
    }

    private void checkExpiredBooks(List<Book> books) {
        for (Book book : books) {
            long diffInMillis = Math.abs(book.getTakenAt().getTime() - new Date().getTime());

            if (diffInMillis > 864_000_000) {
                book.setExpired(true);
            }
        }
    }
}
