package com.kyamran.app.view;

import com.kyamran.app.controller.LabelController;
import com.kyamran.app.controller.PostController;
import com.kyamran.app.model.Label;
import com.kyamran.app.model.Post;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LabelView implements View {
    private static final String OPERATIONS_INFO = """
                ----------------------------------
                1: Save label.
                2: Update label.
                3: Get label by ID.
                4: Delete label.
                5: Get all labels.
                6: Exit.
                ----------------------------------
                """;

    private final Scanner in = new Scanner(System.in);
    private final LabelController labelController = new LabelController();

    private final PostController postController = new PostController();
    @Override
    public void run() {
        try {
            boolean appStatus = true;
            while (appStatus) {
                System.out.println(OPERATIONS_INFO);
                System.out.print("Enter a command: ");
                String operation = in.next();

                switch (operation) {
                    case "1" -> saveLabelView();
                    case "2" -> updateLabelView();
                    case "3" -> getByIdLabelView();
                    case "4" -> deleteByIdLabelView();
                    case "5" -> getAllLabelView();
                    case "6" -> appStatus = false;
                    default -> System.out.println("Incorrect, try again.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Input error.");
        }
    }

    private void saveLabelView() {
        System.out.println("Input name: ");
        String name = in.next();
        System.out.println("Input post ID: ");
        Long postId = in.nextLong();
        Label label = new Label();
        label.setName(name);

        label = labelController.save(label);
        System.out.println("Added: " + label);

        Post post = postController.getById(postId);
        labelController.savePostLabels(post, Collections.singletonList(label));
    }


    private void updateLabelView() {
        System.out.println("Select label ID: ");
        Long id = in.nextLong();
        System.out.println("Select new Name: ");
        String name = in.next();

        Label label = labelController.update(Label.builder()
                .id(id)
                .name(name)
                .build());
        System.out.println("Updated label: " + label);
    }

    private void getAllLabelView() {
        List<Label> labelList = labelController.getAll();
        labelList.forEach(System.out::println);
    }

    private void deleteByIdLabelView() {
        System.out.println("Input label ID: ");
        Long id = in.nextLong();

        labelController.deleteById(id);
        System.out.println("Success delete!");
    }

    private void getByIdLabelView() {
        System.out.println("Input label ID: ");
        Long id = in.nextLong();

        Label label = labelController.getById(id);
        if (label == null) {
            System.out.println("ID not found, try again");
        } else {
            System.out.println(label);
        }
    }
}