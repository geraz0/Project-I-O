package org.example;

import java.io.*;

public class ObjectStream {
    public static void main(String[] args) {
        // Specify the file to write and read from
        String objectFile = "objectData.bin";

        // Creating an object to serialize
        Person person = new Person("Gabbi Erazo", 30);

        // Writing the object to a file using ObjectOutputStream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(objectFile))) {
            objectOutputStream.writeObject(person);
            System.println("Object written to file successfully!");

        } catch (IOException e) {
            System.err.println("An error occurred while writing the object: " + e.getMessage());
        }

        // Reading the object from the file using ObjectInputStream
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(objectFile))) {
            Person readPerson = (Person) objectInputStream.readObject();
            System.out.println("Object read from file:");
            System.out.println("Name: " + readPerson.getName());
            System.out.println("Age: " + readPerson.getAge());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred while reading the object: " + e.getMessage());
        }
    }
}
