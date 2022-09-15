package ru.bul.springs.models;


import javax.validation.constraints.*;

public class Person {
   private int id_person;

   @NotEmpty(message = "Нужно ввести ФИО!")
   @Size(min = 3,max = 50,message = "ФИО должно быть от 3 до 50 символов")
   @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){3,}",message ="ФИО должно быть такой формы: Ф И О" )
   private String FIO;

   @Min(value=1,message = "возвраст должен быть больше 1")
   private int year_of_birth;







    public Person(int id_person, String FIO, int year_of_birtch) {
        this.id_person = id_person;
        this.FIO = FIO;
        this.year_of_birth = year_of_birtch;

    }
    public Person(){}


    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
