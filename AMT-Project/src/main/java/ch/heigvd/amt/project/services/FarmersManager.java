package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.authentification.IAuthenticationService;
import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.model.Field;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class FarmersManager implements FarmersManagerLocal {

    @Resource(lookup = "jdbc/amt_project")
    DataSource dataSource;

    @EJB
    IAuthenticationService authenticationService;

    @Override
    public List<Farmer> findAll() {
        List<Farmer> farmers = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM farmers");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                farmers.add(Farmer.builder()
                        .username(rs.getString("username"))
                        .firstName(rs.getString("firstName"))
                        .lastName(rs.getString("lastName"))
                        .address(rs.getString("address"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build());

            }

            connection.close();


        } catch (SQLException e) {
            Logger.getLogger(FarmersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return farmers;
    }

    @Override
    public Farmer create(Farmer entity) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO farmers ( username,firstName, lastName,address ,email, password) VALUES ( ?,?, ?, ?, ?,?)");
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getAddress());
            statement.setString(5, entity.getEmail());
            statement.setString(6, authenticationService.hashPassword(entity.getPassword()));
            statement.execute();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public Farmer findByUser(String id) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT username, firstName, lastName,address ,email FROM farmers WHERE username = ?");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if (!hasRecord) {
                throw new KeyNotFoundException("Could not find user with username = " + id);
            }
            Farmer existingUser = Farmer.builder()
                    .username(rs.getString(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .email(rs.getString(4))
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
    public Farmer findById(String id) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT username, firstName, lastName,address ,email FROM farmers WHERE idFarmer = ?");
            statement.setInt(1, Integer.parseInt(id));
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if (!hasRecord) {
                throw new KeyNotFoundException("Could not find  = " + id);
            }
            Farmer existingUser = Farmer.builder()
                    .username(rs.getString(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .email(rs.getString(4))
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
    public void update(Farmer entity) throws KeyNotFoundException {

        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE farmers SET firstName=?, lastName=?, address=?, email=? WHERE idFarmer = ?");
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getAddress());
            statement.setString(4, entity.getEmail());
            statement.setInt(5, entity.getIdFarmer());
            int numberOfUpdatedUsers = statement.executeUpdate();
            if (numberOfUpdatedUsers != 1) {
                throw new KeyNotFoundException("Could not find user with username = " + entity.getUsername());
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
            PreparedStatement statement = con.prepareStatement("DELETE FROM farmers WHERE idFarmer = ?");
            statement.setInt(1, Integer.parseInt(id));
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

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
