package com.kyamran.app.view.viewFactory;

import com.kyamran.app.view.PostView;
import com.kyamran.app.view.View;

public class PostViewFactory implements ViewFactory {
    @Override
    public View getView() {
        return new PostView();
    }
}
