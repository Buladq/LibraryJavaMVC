package ru.bul.springs.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.dao.BookDAO;
import ru.bul.springs.dao.PersonDAO;
import ru.bul.springs.models.Book;
import ru.bul.springs.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

@Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
    this.personDAO = personDAO;
}

    @GetMapping// оставляем пустым
    public String index(Model model){
    model.addAttribute("books",bookDAO.index());

        return "book/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){

        return "book/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){


        if(bindingResult.hasErrors())
            return "book/new";

        bookDAO.save(book);
        return "redirect:/book";

    }



    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult, @PathVariable("id") int id) {


        if(bindingResult.hasErrors()){
            return "book/edit";
        }

        bookDAO.update(id, book);
        return "redirect:/book";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/book";

    }



    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book",bookDAO.show(id));

        Optional<Person> bookhas = bookDAO.heHas(id);

        //проверка есть ли владелец у книги
        if (bookhas.isPresent()){
            model.addAttribute("having", bookhas.get());
        }

        else{
            model.addAttribute("noHaving", personDAO.index());

        }

        return "book/show";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/book/" + id;
    }
}
