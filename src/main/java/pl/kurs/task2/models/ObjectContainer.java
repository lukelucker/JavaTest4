package pl.kurs.task2.models;

import pl.kurs.task2.exceptions.ConditionNotMetException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ObjectContainer<T extends Serializable> implements Serializable {

    private Node<T> head;
    private transient Predicate<T> condition;

    private static class Node<T> implements Serializable {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    public ObjectContainer(Predicate<T> condition) {
        this.condition = condition;
    }

    public void add(T obj) {
        if (!condition.test(obj)) {
            throw new ConditionNotMetException("Object does not satisfy container condition: " + obj);
        }
        Node<T> newNode = new Node<>(obj);
        Node<T> last = getLastNode();
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
    }

    private Node<T> getLastNode() {
        if (head == null) return null;
        Node<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }

    public List<T> getWithFilter(Predicate<T> filter) {
        List<T> result = new ArrayList<>();
        forEachNode(n -> {
            if (filter.test(n.value)) {
                result.add(n.value);
            }
        });
        return result;
    }

    private void forEachNode(Consumer<Node<T>> action) {
        Node<T> current = head;
        while (current != null) {
            action.accept(current);
            current = current.next;
        }
    }

    public void removeIf(Predicate<T> condition) {
        while (head != null && condition.test(head.value)) {
            head = head.next;
        }

        Node<T> current = head;
        while (current != null && current.next != null) {
            if (condition.test(current.next.value)) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
    }

    public void storeToFile(String filename, Predicate<T> filter, Function<T, String> formatter) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Node<T> current = head;
            while (current != null) {
                if (filter.test(current.value)) {
                    bw.write(formatter.apply(current.value));
                    bw.newLine();
                }
                current = current.next;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Serializable> ObjectContainer<T> fromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ObjectContainer<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        while (current != null) {
            sb.append(current.value)
                    .append("\n");
            current = current.next;
        }
        return sb.toString();
    }
}
