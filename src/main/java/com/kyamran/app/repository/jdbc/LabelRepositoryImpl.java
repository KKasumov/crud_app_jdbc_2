package com.kyamran.app.repository.jdbc;

import com.kyamran.app.model.Label;
import com.kyamran.app.model.Post;
import com.kyamran.app.repository.LabelRepository;
import com.kyamran.app.utilities.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {
    private static LabelRepositoryImpl instance;

    public static LabelRepositoryImpl getInstance() {
        return instance == null ? instance = new LabelRepositoryImpl() : instance;
    }

    @Override
    public Label save(Label label) {
        try {
            PreparedStatement statement1 = DBUtils.getPreparedStatementWithKeys("INSERT INTO Labels (Name) VALUES (?)");
            statement1.setString(1, label.getName());
            statement1.executeUpdate();
            ResultSet generatedKeys1 = statement1.getGeneratedKeys();

            if (generatedKeys1.next()) {
                long labelId = generatedKeys1.getLong(1);
                label.setId(labelId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return label;
    }



    @Override
    public Label update(Label label) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE Labels SET Name = ? WHERE ID = ?")) {
            statement.setString(1, label.getName());
            statement.setLong(2, label.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return label;
    }

    @Override
    public Label getById(Long id) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Labels WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToLabel(resultSet);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = DBUtils.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Post_Labels WHERE LabelID = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Labels WHERE ID = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Label mapResultSetToLabel(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Label(id, name);
    }


    @Override
    public List<Label> getAll() {
        List<Label> labelList = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Labels")) {
            while (resultSet.next()) {
                Label label = mapResultSetToLabel(resultSet);
                labelList.add(label);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return labelList;
    }

    public void savePostLabels(Post post, List<Label> labels) {
        try {
            for (Label label : labels) {
                PreparedStatement statement2 = DBUtils.getPreparedStatementWithKeys("INSERT INTO Post_Labels (PostId, LabelId) VALUES (?, ?)");
                statement2.setLong(1, post.getId());
                statement2.setLong(2, label.getId());
                statement2.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
