package com.kyamran.app.view.viewFactory;

import com.kyamran.app.view.LabelView;
import com.kyamran.app.view.View;

public class LabelViewFactory implements ViewFactory {
    @Override
    public View getView() {
        return new LabelView();
    }
}
