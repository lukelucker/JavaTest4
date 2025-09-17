package pl.kurs.task1.models;

import pl.kurs.task1.exceptions.InvalidStringContainerPatternException;
import pl.kurs.task1.exceptions.InvalidStringContainerValueException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class StringContainer {

    private final Pattern pattern;
    private Node head;
    private int size = 0;
    private LocalDateTime dateTime;

    public StringContainer(String regex) {
        try {
            this.pattern = Pattern.compile(regex);
        } catch (Exception e) {
            throw new InvalidStringContainerPatternException(regex);
        }
    }

    private static class Node {
        String value;
        Node next;

        Node(String value) {
            this.value = value;
        }
    }

    public void add(String value) {
        if (!pattern.matcher(value).matches()) {
            throw new InvalidStringContainerValueException(value);
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
}
