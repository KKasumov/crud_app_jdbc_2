package com.kyamran.app.repository.jdbc;

import com.kyamran.app.model.Label;
import com.kyamran.app.model.Post;
import com.kyamran.app.model.PostStatus;
import com.kyamran.app.repository.PostRepository;
import com.kyamran.app.utilities.DBUtils;
import com.kyamran.app.utilities.DateFormatter;

import java.sql.*;
import java.util.*;

public class PostRepositoryImpl implements PostRepository {
    private static PostRepositoryImpl instance;
    private PostRepositoryImpl() {
    }

    public static PostRepositoryImpl getInstance() {
        return instance == null ? instance = new PostRepositoryImpl() : instance;
    }

    public Post save(Post post) {
        Long writerId = post.getWriterId();
        if (writerId == null) {
            throw new IllegalArgumentException("WriterId must be set on Post before saving");
        }

        try (PreparedStatement statement = DBUtils.getPreparedStatementWithKeys("INSERT INTO Posts (Content, Created, Updated, PostStatus, WriterId)\n" +
                "VALUES (?, ?, ?, ?, ?) ")) {
            statement.setString(1, post.getContent());
            statement.setString(2, DateFormatter.getCurrentDate());
            statement.setString(3, DateFormatter.getCurrentDate());
            statement.setString(4, PostStatus.ACTIVE.toString());
            statement.setLong(5, writerId);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try (PreparedStatement statement = DBUtils.getPreparedStatementWithKeys("UPDATE Posts\n" +
                "SET Content = ?, Updated = ?, PostStatus = ? WHERE ID = ?")) {
            statement.setString(1, post.getContent());
            statement.setString(2, DateFormatter.getCurrentDate());
            statement.setString(3, PostStatus.UNDER_REVIEW.toString());
            statement.setLong(4, post.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public Post getById(Long id) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys("SELECT * FROM Posts p LEFT JOIN Post_Labels pl ON p.id = pl.PostID LEFT JOIN Labels l ON l.id = pl.LabelID WHERE p.ID = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Post> posts = mapResultSetToPostList(resultSet);
                if (!posts.isEmpty()) {
                    return posts.get(0);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Post.builder().build();
    }



    @Override
    public void deleteById(Long id) {
        try (PreparedStatement statement = DBUtils.getPreparedStatementWithKeys("UPDATE Posts SET PostStatus = ? WHERE ID = ?")) {
            statement.setString(1, PostStatus.DELETED.toString());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Posts p LEFT JOIN Post_Labels pl ON p.id = pl.PostID LEFT JOIN Labels l ON l.id = pl.LabelID")) {
            return mapResultSetToPostList(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            // Возвращаем пустой список в случае ошибки. Возможно, стоит изменить этот подход.
            return Collections.emptyList();
        }
    }


    @Override
    public List<Post> returnPostsByWriterId(Long id) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys("SELECT * FROM posts p LEFT JOIN Post_Labels pl ON p.ID = pl.PostID LEFT JOIN Labels l ON l.ID = pl.LabelID WHERE p.WriterId = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return mapResultSetToPostList(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<Post> mapResultSetToPostList(ResultSet resultSet) throws SQLException {
        Map<Long, Post> postMap = new HashMap<>();
        Map<Long, Label> labelMap = new HashMap<>();

        while (resultSet.next()) {
            Long id = resultSet.getLong("ID");

            if(!postMap.containsKey(id)) {
                String content = resultSet.getString("Content");
                String created = resultSet.getString("Created");
                String updated = resultSet.getString("Updated");
                String postStatus = resultSet.getString("PostStatus");
                Long writerId = resultSet.getLong("WriterId");

                List<Label> labels = new ArrayList<>();
                Post post = new Post(id, content, created, updated, PostStatus.valueOf(postStatus), writerId, labels);
                postMap.put(id, post);
            }

            Long labelId = resultSet.getLong("l.id");
            if (!resultSet.wasNull()) {
                String labelName = resultSet.getString("l.name");
                Label label = labelMap.get(labelId);
                if (label == null) {
                    label = new Label(labelId, labelName);
                    labelMap.put(labelId, label);
                }
                postMap.get(id).getLabels().add(label);
            }
        }

        return new ArrayList<>(postMap.values());
    }
}
