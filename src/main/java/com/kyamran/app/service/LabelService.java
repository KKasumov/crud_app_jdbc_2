package com.kyamran.app.service;

import com.kyamran.app.model.Label;
import com.kyamran.app.model.Post;
import com.kyamran.app.repository.LabelRepository;
import com.kyamran.app.repository.jdbc.LabelRepositoryImpl;

import java.util.List;

public class LabelService {
    LabelRepository labelRepository = LabelRepositoryImpl.getInstance();

    public void setLabelRepository(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label save(Label label) { return labelRepository.save(label); }


    public Label update(Label label) {
        return labelRepository.update(label);
    }


    public Label getById(Long Long) {
        return labelRepository.getById(Long);
    }


    public void deleteById(Long Long) {
        labelRepository.deleteById(Long);
    }


    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    public void savePostLabels(Post post, List<Label> labels) {
        labelRepository.savePostLabels(post, labels);
    }
}
