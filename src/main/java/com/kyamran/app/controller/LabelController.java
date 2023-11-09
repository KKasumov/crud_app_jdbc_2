package com.kyamran.app.controller;

import com.kyamran.app.model.Label;
import com.kyamran.app.model.Post;
import com.kyamran.app.service.LabelService;

import java.util.List;

public class LabelController {
    LabelService labelService = new LabelService();

    public Label save(Label label) {
        return labelService.save(label);
    }

    public Label update(Label label) {
        return labelService.update(label);
    }

    public Label getById(Long Long) {
        return labelService.getById(Long);
    }

    public void deleteById(Long Long) {
        labelService.deleteById(Long);
    }

    public List<Label> getAll() {
        return labelService.getAll();
    }

    public void savePostLabels(Post post, List<Label> labels) {
        labelService.savePostLabels(post, labels);
    }
}
