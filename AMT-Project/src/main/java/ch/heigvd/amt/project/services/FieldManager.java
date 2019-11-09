package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Field;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class FieldManager implements FieldManagerLocal {

    @Resource(lookup = "jdbc/amt_project")
    DataSource dataSource;

    @Override
    public List<Field> findAll(int amount, int page) throws SQLException {
        List<Field> fields = new LinkedList<>();

        if (amount > 0 && page > 0) {

            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM fields LIMIT ?, ?"
                );

                statement.setInt(1, amount * (page - 1)); // offset
                statement.setInt(2, amount);

                ResultSet results = statement.executeQuery();
                while (results.next()) {
                    fields.add(Field.builder()
                            .idField(results.getInt("idField"))
                            .size(results.getInt("size"))
                            .build()
                    );
                }

            } catch (SQLException sqlException) {
                fields.clear();
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }

        return fields;
    }

    @Override
    public List<Field> findAll() {
        List<Field> fields = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM fields");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                fields.add(Field.builder()
                        .idField(rs.getInt("idField"))
                        .size((int) rs.getLong("size"))
                        .build());

            }

            connection.close();


        } catch (SQLException e) {
            Logger.getLogger(FarmersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return fields;
    }

    @Override
    public Field create(Field entity) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO fields (size) VALUES ( ?)");
            statement.setLong(1, entity.getSize());
            statement.execute();
            return entity;
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public Field findById(String id) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT size FROM fields WHERE idFarmer = ?");
            statement.setLong(1, Long.parseLong(id));
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if (!hasRecord) {
                throw new KeyNotFoundException("Could not find user with field = " + id);
            }
            Field existingUser = Field.builder()
                    .size(rs.getInt(1))
                    .build();
            return existingUser;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public void update(Field entity) throws KeyNotFoundException {

        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE fields SET size=? WHERE idField = ?");
            statement.setLong(1, entity.getSize());
            statement.setLong(2, entity.getIdField());
            int numberOfUpdatedUsers = statement.executeUpdate();
            if (numberOfUpdatedUsers != 1) {
                throw new KeyNotFoundException("Could not find  field = " + entity.getIdField());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            closeConnection(con);
        }

    }

    @Override
    public void deleteById(String id) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM fields WHERE idField = ?");
            statement.setLong(1, Long.parseLong(id));
            int numberOfDeletedUsers = statement.executeUpdate();
            if (numberOfDeletedUsers != 1) {
                throw new KeyNotFoundException("Could not find field = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            closeConnection(con);
        }

    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
