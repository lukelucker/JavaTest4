package pl.kurs.task2.app;

import pl.kurs.task2.models.ObjectContainer;
import pl.kurs.task2.models.Person;

import java.util.List;

public class ObjectContainerRunner {

    public static void main(String[] args) {

        ObjectContainer<Person> peopleFromWarsaw = new ObjectContainer<>(p -> p.getCity().equals("Warsaw"));

        peopleFromWarsaw.add(new Person("Jan", "Warsaw", 30));
        peopleFromWarsaw.add(new Person("Weronika","Warsaw", 20));
        //peopleFromWarsaw.add(new Person("Waldek", "Monaco", 34));

        List<Person> females = peopleFromWarsaw.getWithFilter(p -> p.getName().endsWith("a"));
        females.forEach(System.out::println);


    }
}
