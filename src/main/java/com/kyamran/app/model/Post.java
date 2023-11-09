package com.kyamran.app.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Post {
    private Long id;
    private String content;
    private String created;
    private String updated;
    private PostStatus postStatus;
    private Long writerId;
    private List<Label> labels;

    public Post(String content, Long id) {
        this.content = content;
        this.id = id;
    }
}