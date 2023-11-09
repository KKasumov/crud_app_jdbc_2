package com.kyamran.app.repository;

import com.kyamran.app.model.Label;
import com.kyamran.app.model.Post;

import java.util.List;

public interface LabelRepository extends GenericRepository<Label, Long> {
    void savePostLabels(Post post, List<Label> labels);
}
