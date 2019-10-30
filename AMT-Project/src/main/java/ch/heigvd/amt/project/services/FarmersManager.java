package ch.heigvd.amt.project.services;

import ch.heigvd.amt.project.model.Farmer;

import javax.annotation.Resource;
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

    @Override
    public List<Farmer> findAllFarmers() {
        List<Farmer> farmers = new ArrayList<>();
        Connection connection =null;
        try {
            connection =dataSource.getConnection();

            PreparedStatement pstmt= connection.prepareStatement("SELECT * FROM farmers");
            ResultSet rs =pstmt.executeQuery();

            while (rs.next()){

                Logger.getLogger(FarmersManager.class.getName()).info(rs.getString("firstName"));
                Logger.getLogger(FarmersManager.class.getName()).info("-----------------------------");



                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                long id=rs.getLong("id");


                farmers.add(new Farmer(id,firstName,lastName));
            }

            connection.close();


        }catch (SQLException e){
            Logger.getLogger(FarmersManager.class.getName()).log(Level.SEVERE,null,e);
        }
        return farmers;
    }
}
