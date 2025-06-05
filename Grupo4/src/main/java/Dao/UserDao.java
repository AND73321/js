/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Models.User;
import Utiles.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.text.StyleConstants;

/**
 *
 * @author USUARIO
 */
public class UserDao {
    public List<User> getAllUser(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        try(Connection conn = Conexion.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
            while(rs.next()){
                User user =  new User();
                user.setCedula(rs.getString("cedula"));
                user.setNombre(rs.getString("nombre"));
                user.setApellido(rs.getString("apellido"));
                user.setDireccion(rs.getString("direccion"));
                user.setTelefono(rs.getString("telefono"));
                users.add(user);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return users;
    }
    
    
    public boolean saveUser(User user) throws Exception{
        String sql = "INSERT INTO estudiantes (cedula, nombre, apellido, direccion, telefono) VALUES (?,?,?,?,?)";
        
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);){
            
                ps.setString(1, user.getCedula());
                ps.setString(2, user.getNombre());
                ps.setString(3, user.getApellido());
                ps.setString(4, user.getDireccion());
                ps.setString(5, user.getTelefono());
                return ps.executeUpdate() > 0;
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    public boolean save(User user){
       return true;
    }
    
    
    public boolean updateUser(User user){
        String SQL = "UPDATE estudiantes SET nombre = ?, apellido = ?, direccion = ?, telefono = ? WHERE cedula = ?";
        
        try (
                Connection conn = Conexion.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL);
                ){
            
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, user.getDireccion());
            ps.setString(4, user.getTelefono());
            ps.setString(5, user.getCedula());
                return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    public boolean deleteUser(User user){
        String SQL="DELETE FROM estudiantes WHERE cedula = ?";
        
        try (
                Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ){
            
            ps.setString(1, user.getCedula());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    public User getByYd(User user) {
    String SQL = "SELECT * FROM estudiantes WHERE cedula = ?";
    User resultado = null;

    try (
        Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL);
    ) {
        ps.setString(1, user.getCedula());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            resultado = new User();
            resultado.setCedula(rs.getString("cedula"));
            resultado.setNombre(rs.getString("nombre"));
            resultado.setApellido(rs.getString("apellido"));
            resultado.setDireccion(rs.getString("direccion"));
            resultado.setTelefono(rs.getString("telefono"));
        }
        rs.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error en getByYd:\n" + e.getMessage());
    }
    return resultado;
}

    
}
