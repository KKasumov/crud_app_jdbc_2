package com.kyamran.app.repository.jdbc;

import com.kyamran.app.model.Post;
import com.kyamran.app.model.Writer;
import com.kyamran.app.repository.WriterRepository;
import com.kyamran.app.utilities.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    private static WriterRepository instance;
    private final PostRepositoryImpl postRepository = PostRepositoryImpl.getInstance();

    private WriterRepositoryImpl() {
    }

    public static WriterRepository getInstance() {
        return instance == null ? instance = new WriterRepositoryImpl() : instance;
    }

    @Override
    public Writer save(Writer writer) {
        DBUtils.getInstance();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Writers (FirstName, LastName) VALUES (?, ?)")) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        DBUtils.getInstance();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE Writers SET FirstName = ?, LastName = ? WHERE ID = ?")) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.setLong(3, writer.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public void deleteById(Long id) {
        DBUtils.getInstance();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Writers WHERE ID = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Writer getById(Long id) {
        Writer writer = null;
        DBUtils.getInstance();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Writers WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    writer = mapResultSetToWriter(resultSet);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writerList = new ArrayList<>();
        DBUtils.getInstance();
        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Writers")) {
            while (resultSet.next()) {
                writerList.add(mapResultSetToWriter(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writerList;
    }

    private Writer mapResultSetToWriter(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String firstName = resultSet.getString("FirstName");
        String lastName = resultSet.getString("LastName");
        List<Post> posts = postRepository.returnPostsByWriterId(id);
        return new Writer(id, firstName, lastName, posts);
    }

}