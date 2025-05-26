package com.vzap.j149.system.user.repo;

import com.vzap.j149.config.DBConfig;
import com.vzap.j149.system.user.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserRepoImpl implements UserRepo{
    //Logger used for printing stack trace to glassfish log file/console
    private static final Logger LOG = Logger.getLogger(UserRepoImpl.class.getName());

    @Override
    public Optional<User> createUser(User user) {
        String query = "INSERT INTO users(username, password, email, role) VALUES(?, ?, ?, ?)";
        try(Connection con = DBConfig.getCon(); PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole().name());
            
            if(ps.executeUpdate() > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()){
                        user.setUserId(rs.getLong(1));
                        return Optional.of(user);
                    }
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to insert new user into the database", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserRepoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Optional.empty();
    }
    

    @Override
    public Optional<User> findUserById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<User> findByCredentials(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<User> updateUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<User> deleteUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
