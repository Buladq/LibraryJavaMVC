package ru.bul.springs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.bul.springs.models.Book;
import ru.bul.springs.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> index() {

   return jdbcTemplate.query("SELECT * from Book", new BeanPropertyRowMapper<>(Book.class));
    }
    public void save(Book book){
        jdbcTemplate.update("insert into book(name_book,author_book,year_book) values (?,?,?)",book.getName_book(),
                book.getAuthor_book(),book.getYear_book());
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT id_book,name_book,author_book,year_book from Book WHERE id_book=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void update(int id,Book ubpdatedBook){
        jdbcTemplate.update("UPDATE Book SET name_book=?,author_book=?,year_book=? WHERE id_book=?",ubpdatedBook.getName_book(),
                ubpdatedBook.getAuthor_book(),ubpdatedBook.getYear_book(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id_book=?",id);
    }



    public Optional <Person> heHas(int id){
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.people_id = Person.id_person where id_book=?",
                new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET people_id=NULL WHERE id_book=?", id);
    }
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET people_id =? WHERE id_book=?", selectedPerson.getId_person(), id);
    }
}