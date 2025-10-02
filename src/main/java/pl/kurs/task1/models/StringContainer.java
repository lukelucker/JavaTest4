package pl.kurs.task1.models;

import pl.kurs.task1.exceptions.DuplicatedElementOnListException;
import pl.kurs.task1.exceptions.ElementNotFoundException;
import pl.kurs.task1.exceptions.InvalidStringContainerPatternException;
import pl.kurs.task1.exceptions.InvalidStringContainerValueException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class StringContainer implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private static class Node implements Serializable {
        String value;
        LocalDateTime addedAt;
        Node next;

        Node(String value) {
            this.value = value;
            this.addedAt = LocalDateTime.now();
        }
    }

    public void add(String value) {
        validateValue(value);
        addNode(new Node(value));
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

    public StringContainer getDataBetween(LocalDateTime dateFrom, LocalDateTime dateTo) {
        StringContainer result = new StringContainer(pattern.pattern(), duplicatedNotAllowed);
        Node current = head;
        while (current != null) {
            boolean afterFrom = (dateFrom == null || !current.addedAt.isBefore(dateFrom));
            boolean beforeTo = (dateTo == null || !current.addedAt.isAfter(dateTo));
            if (afterFrom && beforeTo) {
                result.add(current.value);
            }
            current = current.next;
        }
        return result;
    }

    public static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void storeToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException("File writing error", e);
        }
    }

    public static StringContainer fromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (StringContainer) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("File reading error", e);
        }
    }

    @Override
    public String toString() {
        String result = "StringContainer{\n" +
                "pattern=" + pattern.pattern() + ",\n" +
                "duplicatedNotAllowed=" + duplicatedNotAllowed + ",\n" +
                "size=" + size + "\n" +
                "elements:\n";

        Node current = head;
        while (current != null) {
            result += current.value + " (" + current.addedAt + ")\n";
            current = current.next;
        }
        result += "}";
        return result;
    }

    private void validateValue(String value) {
        if (!pattern.matcher(value).matches()) {
            throw new InvalidStringContainerValueException(value);
        }
        if (duplicatedNotAllowed && contains(value)) {
            throw new DuplicatedElementOnListException(value);
        }
    }

    private void addNode(Node newNode) {
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
}
