package com.kyamran.app;

import com.kyamran.app.view.MainView;
import com.kyamran.app.view.View;

public class AppRunner {
    public static void main(String[] args) {
        View mainView = new MainView();
        mainView.run();
    }
}
