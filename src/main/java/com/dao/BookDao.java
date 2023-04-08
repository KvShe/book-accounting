package com.dao;

import com.models.Book;
import com.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BookDao implements Dao<Book> {
    private final JdbcTemplate JDBC_TEMPLATE;

    public List<Book> index() {
        return JDBC_TEMPLATE.query(
                "select * from book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return JDBC_TEMPLATE.query(
                        "select * from book where book_id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        JDBC_TEMPLATE.update(
                "insert into book (author, name, age) values (?, ?, ?)",
                book.getAuthor(),
                book.getName(),
                book.getAge());
    }

    public void update(int id, Book book) {
        JDBC_TEMPLATE.update(
                "update book set author = ?, name = ?, age = ? where book_id = ?",
                book.getAuthor(),
                book.getName(),
                book.getAge(),
                id);
    }

    public void delete(int id) {
        JDBC_TEMPLATE.update("delete from book where book_id = ?", id);
    }

    public Person getPerson(int bookId) {
        return JDBC_TEMPLATE.query(
                        "select * from person join person_book on person.person_id = person_book.person_id where book_id = ?",
                        new Object[]{bookId},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void empty(int id) {
        JDBC_TEMPLATE.update("delete from person_book where book_id = ?", id);
    }
}
