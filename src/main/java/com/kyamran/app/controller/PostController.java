package com.kyamran.app.controller;

import com.kyamran.app.model.Post;
import com.kyamran.app.service.PostService;

import java.util.List;

public class PostController {
    PostService postService = new PostService();

    public Post save(Post post) {
        return postService.save(post);
    }

    public Post update(Post post) {
        return postService.update(post);
    }

    public Post getById(Long Long) {
        return postService.getById(Long);
    }

    public void deleteById(Long Long) {
        postService.deleteById(Long);
    }

    public List<Post> getAll() {
        return postService.getAll();
    }


}
