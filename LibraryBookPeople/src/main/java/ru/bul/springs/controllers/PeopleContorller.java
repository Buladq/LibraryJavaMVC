package ru.bul.springs.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.dao.PersonDAO;
import ru.bul.springs.models.Person;
import ru.bul.springs.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleContorller {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleContorller(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping// оставляем пустым
    public String index(Model model){
        model.addAttribute("people",personDAO.index());


        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){

        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,Model model){
        model.addAttribute("person",personDAO.show(id));


//        проверка есть ли книги у владельца
        if(personDAO.checkOnHave(id)!=null){
            model.addAttribute("have","Книги:");
            model.addAttribute("books",personDAO.booksHave(id));
        }
        else {
//            System.out.println(personDAO.checkOnHave(id));
            model.addAttribute("have","Человек пока не взял ни одной книги");
        }



        return "people/show";
    }




    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";

    }
}
