package com.controllers;

import com.dao.BookDao;
import com.models.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BooksController {
    private final BookDao BOOK_DAO;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", BOOK_DAO.index());

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", BOOK_DAO.getPerson(id));
        model.addAttribute("book", BOOK_DAO.show(id));

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        BOOK_DAO.save(book);

        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String empty(@PathVariable("id") int id, Model model) {
        BOOK_DAO.empty(id);

        return "redirect:/books/{id}";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", BOOK_DAO.show(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book,
                         @PathVariable("id") int id) {
        BOOK_DAO.update(id, book);

        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        BOOK_DAO.delete(id);

        return "redirect:/books";
    }
}
