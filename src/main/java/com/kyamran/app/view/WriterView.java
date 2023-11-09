package com.kyamran.app.view;

import com.kyamran.app.controller.WriterController;
import com.kyamran.app.model.Writer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WriterView implements View {
    private static final String OPERATIONS_INFO = """
                ----------------------------------
                1: Save writer.
                2: Update writer.
                3: Get writer by ID.
                4: Delete writer.
                5: Get all writers.
                6: Exit.
                ----------------------------------
                """;

    private final Scanner in = new Scanner(System.in);
    private final WriterController writerController = new WriterController();

    @Override
    public void run() {
        try {
            boolean appStatus = true;
            while (appStatus) {
                System.out.println(OPERATIONS_INFO);
                System.out.print("Enter a command: ");
                String operation = in.next();

                switch (operation) {
                    case "1" -> saveWriterView();
                    case "2" -> updateWriterView();
                    case "3" -> getWriterByIdView();
                    case "4" -> deleteWriterByIdView();
                    case "5" -> getAllWritersView();
                    case "6" -> appStatus = false;
                    default -> System.out.println("Incorrect, try again.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Input error.");
        }
    }

    public void saveWriterView() {
        System.out.print("Input first name: ");
        String firstName = in.next();
        System.out.print("Input last name: ");
        String lastName = in.next();

        Writer writer = new Writer();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);

        writerController.save(writer);
        System.out.println("Saved!");
    }

    public void updateWriterView() {
        System.out.print("Input writer ID: ");
        Long id = in.nextLong();
        Writer writerToUpdate = writerController.getById(id);
        if (writerToUpdate == null) {
            System.out.println("Writer not found!");
            return;
        }
        System.out.print("Input new first name: ");
        String firstName = in.next();
        System.out.print("Input new last name: ");
        String lastName = in.next();
        writerToUpdate.setFirstName(firstName);
        writerToUpdate.setLastName(lastName);

        writerController.update(writerToUpdate);
        System.out.println("Updated!");
    }


    public void getWriterByIdView() {
        System.out.print("Input writer ID: ");
        Long id = in.nextLong();
        Writer writer = writerController.getById(id);
        System.out.println(writer);
    }

    public void deleteWriterByIdView() {
        System.out.print("Input writer ID: ");
        Long id = in.nextLong();
        writerController.deleteById(id);
        System.out.println("Deleted!");
    }

    public void getAllWritersView() {
        List<Writer> writerList = writerController.getAll();
        writerList.forEach(System.out::println);
    }
}