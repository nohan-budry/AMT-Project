package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Field;
import ch.heigvd.amt.project.utils.Pagination;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class FieldManager implements FieldManagerLocal {

    @Resource(lookup = "jdbc/amt_project")
    DataSource dataSource;

    @Override
    public List<Field> findAll(Pagination pagination) {
        List<Field> fields = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM fields LIMIT ?, ?"
            );

            statement.setInt(1, pagination.getOffset());
            statement.setInt(2, pagination.getAmount());

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
            closeConnection(connection);
        }

        return fields;
    }

    @Override
    public Field create(Field entity) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO fields (size) VALUES ( ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getSize());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return entity.toBuilder().idField(keys.getInt(1)).build();
            }

        } finally {
            closeConnection(con);
        }

        return null;
    }

    @Override
    public Field findById(Integer id) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM fields WHERE idField = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if (!hasRecord) {
                throw new KeyNotFoundException("Could not find field = " + id);
            }
            Field existingUser = Field.builder()
                    .idField(rs.getInt("idField"))
                    .size(rs.getInt("size"))
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
    public void deleteById(Integer id) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM fields WHERE idField = ?");
            statement.setLong(1, id);
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
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
