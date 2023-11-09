package com.kyamran.app.view;

import com.kyamran.app.view.viewFactory.LabelViewFactory;
import com.kyamran.app.view.viewFactory.PostViewFactory;
import com.kyamran.app.view.viewFactory.ViewFactory;
import com.kyamran.app.view.viewFactory.WriterViewFactory;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class MainView implements View {
    private static final Map<String, Supplier<ViewFactory>> FACTORY_MAP = Map.of(
            "1", LabelViewFactory::new,
            "2", PostViewFactory::new,
            "3", WriterViewFactory::new
    );
    private static final String OPERATIONS_INFO = """
            ----------------------------------
            1: Label
            2: Post
            3: Writer
            4: Exit.
            ----------------------------------
            """;

    private final Scanner in = new Scanner(System.in);
    private boolean appStatus = true;

    @Override
    public void run() {
        try {
            while (appStatus) {
                System.out.println(OPERATIONS_INFO);
                System.out.print("Enter a command: ");
                String operation = in.next();

                switch (operation) {
                    case "1", "2", "3" -> getViewFactory(operation).getView().run();
                    case "4" -> appStatus = false;
                    default -> System.out.println("Incorrect, try again.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Input error.");
        }
    }

    private ViewFactory getViewFactory(String operation) {
        Supplier<ViewFactory> factorySupplier = FACTORY_MAP.get(operation);
        if (factorySupplier != null) {
            return factorySupplier.get();
        } else {
            throw new IllegalArgumentException("Factory not found for operation: " + operation);
        }
    }
}