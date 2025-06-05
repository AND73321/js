/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author USUARIO
 */
public class Concexion {
    public static Connection getConnection() throws Exception{
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/banco", "root", "");
        
    }
}
