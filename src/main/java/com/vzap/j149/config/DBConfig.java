package com.vzap.j149.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rossb
 */
public class DBConfig {

    public static Connection getCon() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/j149_appointment_setter?useSSL=false","root","root");
    }
}
