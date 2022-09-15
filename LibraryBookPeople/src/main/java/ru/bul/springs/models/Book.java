package ru.bul.springs.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private int id_book;


    @NotEmpty(message = "Нужно ввести название книги")
    @Size(min = 3,max = 50,message = "Название книги от 3 до 50 символов")
    private String name_book;
    @NotEmpty(message = "Нужно ввести автора книги: Ф И")
    @Size(min = 3,max = 50,message = "Автор книги от 3 до 50 символов в формате Ф И")
    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){2,}",message ="ФИ должно быть такой формы: Ф И (при желании О)" )
    private String author_book;

    @Min(value=1000,message = "год должен быть меньше 1000")
    private int year_book;

    public Book(int id_book, String name_book, String author_book, int year_book) {
        this.id_book = id_book;

        this.name_book = name_book;
        this.author_book = author_book;
        this.year_book = year_book;
    }

    public Book() {
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public String getName_book() {
        return name_book;
    }

    public void setName_book(String name_book) {
        this.name_book = name_book;
    }

    public String getAuthor_book() {
        return author_book;
    }

    public void setAuthor_book(String author_book) {
        this.author_book = author_book;
    }

    public int getYear_book() {
        return year_book;
    }

    public void setYear_book(int year_book) {
        this.year_book = year_book;
    }
}
