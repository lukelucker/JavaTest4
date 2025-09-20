package pl.kurs.task1.models;

import pl.kurs.task1.exceptions.DuplicatedElementOnListException;
import pl.kurs.task1.exceptions.ElementNotFoundException;
import pl.kurs.task1.exceptions.InvalidStringContainerPatternException;
import pl.kurs.task1.exceptions.InvalidStringContainerValueException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class StringContainer {

    private final Pattern pattern;
    private Node head;
    private int size = 0;
    private boolean duplicatedNotAllowed;

    public StringContainer(String regex) {
        this(regex, false);
    }

    public StringContainer(String regex, boolean duplicatedNotAllowed) {
        try {
            this.pattern = Pattern.compile(regex);
        } catch (Exception e) {
            throw new InvalidStringContainerPatternException(regex);
        }
        this.duplicatedNotAllowed = duplicatedNotAllowed;
    }

    private static class Node {
        String value;
        LocalDateTime addedAt;
        Node next;

        Node(String value) {
            this.value = value;
            this.addedAt = LocalDateTime.now();
        }
    }

    public void add(String value) {
        if (!pattern.matcher(value).matches()) {
            throw new InvalidStringContainerValueException(value);
        }
        if (duplicatedNotAllowed && contains(value)) {
            throw new DuplicatedElementOnListException(value);
        }

        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    private boolean contains(String value) {
        Node current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of range.");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    public int size() {
        return size;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of range.");
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    public void remove(String value) {
        if (head == null) {
            throw new ElementNotFoundException(value);
        }
        if (head.value.equals(value)) {
            head = head.next;
            size--;
            return;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.value.equals(value)) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
        throw new ElementNotFoundException(value);
    }


}
