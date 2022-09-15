package ru.bul.springs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.bul.springs.models.Book;
import ru.bul.springs.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * from Person",new BeanPropertyRowMapper<>(Person.class));
    }


    public Person show(int id){
       return jdbcTemplate.query("SELECT * from Person WHERE id_person=?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
               .stream().findAny().orElse(null);

    }

    public Optional <Person> show(String FIO){
        return jdbcTemplate.query("SELECT * from Person WHERE FIO=?",new Object[]{FIO},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();

    }
    public void save(Person person){
   jdbcTemplate.update("INSERT INTO Person(fio,year_of_birth) VALUES (?,?)",person.getFIO(),person.getYear_of_birth());
    }

    public void update(int id,Person ubpdatedPerson){
        jdbcTemplate.update("UPDATE Person SET FIO=?,year_of_birth=? WHERE id_person=?",ubpdatedPerson.getFIO(),
                ubpdatedPerson.getYear_of_birth(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id_person=?",id);
    }

    //Проверка на наличие книги и выдача определннего атрибута
    public Person checkOnHave(int id){
      return  jdbcTemplate.query("select * from person join book b on person.id_person = b.people_id where id_person=?",
                        new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    //возврат книг,которые есть у человека
    public List<Book> booksHave(int idUser){

        return jdbcTemplate.query("select name_book,author_book,year_book,id_book  from book join person p on book.people_id = p.id_person where id_person=?",
                new BeanPropertyRowMapper<>(Book.class),idUser);
    }
}
