package com.kyamran.app.repository;

import com.kyamran.app.model.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Long> {
    List<Post> returnPostsByWriterId(Long id);

}