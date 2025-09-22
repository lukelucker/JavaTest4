package pl.kurs.task2.models;

import pl.kurs.task2.exceptions.ConditionNotMetException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ObjectContainer<T> {

    private Node<T> head;
    private Predicate<T> condition;

    private static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    public ObjectContainer(Predicate<T> condition) {
        this.condition = condition;
    }

    public boolean add(T obj) {
        if (!condition.test(obj)) {
            throw new ConditionNotMetException("Object does not satisfy container condition: " + obj);
        }

        Node<T> newNode = new Node<>(obj);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        return true;
    }

    public List<T> getWithFilter(Predicate<T> filter) {
        List<T> result = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            if (filter.test(current.value)) {
                result.add(current.value);
            }
            current = current.next;
        }
        return result;
    }

    public void removeIf(Predicate filter) {

    }


}
