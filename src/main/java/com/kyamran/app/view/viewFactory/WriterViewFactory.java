package com.kyamran.app.view.viewFactory;

import com.kyamran.app.view.View;
import com.kyamran.app.view.WriterView;

public class WriterViewFactory implements ViewFactory {
    @Override
    public View getView() {
        return new WriterView();
    }
}
