package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.authentication.IAuthenticationService;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.model.Field;
import ch.heigvd.amt.project.utils.Pagination;

import javax.annotation.Resource;
import javax.ejb.EJB;
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

//TODO: Set admin where needed

@Stateless
public class FarmersManager implements FarmersManagerLocal {

    @Resource(lookup = "jdbc/amt_project")
    DataSource dataSource;

    @EJB
    IAuthenticationService authenticationService;

    @Override
    public List<Farmer> findAll(Pagination pagination) {
        List<Farmer> farmers = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM farmers LIMIT ?, ?"
            );

            statement.setInt(1, pagination.getOffset());
            statement.setInt(2, pagination.getAmount());

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                farmers.add(Farmer.builder()
                        .idFarmer(results.getInt("idFarmer"))
                        .username(results.getString("username"))
                        .firstName(results.getString("firstName"))
                        .lastName(results.getString("lastName"))
                        .address(results.getString("address"))
                        .email(results.getString("email"))
                        .build());
            }

        } catch (SQLException sqlException) {
            farmers.clear();
        } finally {
            closeConnection(connection);
        }

        return farmers;
    }

    @Override
    public Farmer create(Farmer entity) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO farmers ( username,firstName, lastName,address ,email, password) VALUES ( ?,?, ?, ?, ?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getAddress());
            statement.setString(5, entity.getEmail());
            statement.setString(6, authenticationService.hashPassword(entity.getPassword()));
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return entity.toBuilder().idFarmer(keys.getInt(1)).build();
            }

        } finally {
            closeConnection(con);
        }

        return null;
    }

    @Override
    public Farmer findByUser(String username) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(
                    "SELECT idFarmer, username, firstName, lastName, address ,email FROM farmers WHERE username = ?"
            );
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if (!hasRecord) {
                throw new KeyNotFoundException("Could not find user with username = " + username);
            }
            Farmer existingFarmer = Farmer.builder()
                    .idFarmer(rs.getInt(1))
                    .username(rs.getString(2))
                    .firstName(rs.getString(3))
                    .lastName(rs.getString(4))
                    .address(rs.getString(5))
                    .email(rs.getString(6))
                    .build();
            return existingFarmer;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public Farmer findById(Integer id) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(
                    "SELECT idFarmer, username, firstName, lastName, address , email FROM farmers WHERE idFarmer = ?"
            );
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if (!hasRecord) {
                throw new KeyNotFoundException("Could not find  = " + id);
            }
            return Farmer.builder()
                    .idFarmer(rs.getInt(1))
                    .username(rs.getString(2))
                    .firstName(rs.getString(3))
                    .lastName(rs.getString(4))
                    .address(rs.getString(5))
                    .email(rs.getString(6))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public void update(Farmer farmer) throws KeyNotFoundException {

        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE farmers SET firstName=?, lastName=?, address=?, email=? WHERE idFarmer = ?");
            statement.setString(1, farmer.getFirstName());
            statement.setString(2, farmer.getLastName());
            statement.setString(3, farmer.getAddress());
            statement.setString(4, farmer.getEmail());
            statement.setInt(5, farmer.getIdFarmer());
            int numberOfUpdatedUsers = statement.executeUpdate();
            if (numberOfUpdatedUsers != 1) {
                throw new KeyNotFoundException("Could not update");
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
            PreparedStatement statement = con.prepareStatement("DELETE FROM farmers WHERE idFarmer = ?");
            statement.setInt(1, id);
            int numberOfDeletedUsers = statement.executeUpdate();
            if (numberOfDeletedUsers != 1) {
                throw new KeyNotFoundException("Could not find user with username = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            closeConnection(con);
        }

    }

    @Override
    public Farmer login(String username, String password) {
        Connection con = null;
        try {
            con = dataSource.getConnection();

            PreparedStatement statement = con.prepareStatement(
                    "SELECT password FROM farmers WHERE username = ?"
            );
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();
            if (rs.next() && authenticationService.checkPassword(password, rs.getString(1))) {
                return findByUser(username);
            }

        } catch (Exception e) {
            return null;
        } finally {
            if (con != null)
                closeConnection(con);
        }

        return null;
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
