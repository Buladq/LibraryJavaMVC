package ru.bul.springs.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bul.springs.dao.PersonDAO;
import ru.bul.springs.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aclazz) {
        return Person.class.equals(aclazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //есть ли человек с таким же email
        Person person= (Person) target;
        if(personDAO.show(person.getFIO()).isPresent()){
            errors.rejectValue("FIO","","фио уже занята");
        }


    }
}
