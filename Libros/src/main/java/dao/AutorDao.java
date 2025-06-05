/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Autor;
import utils.Conexion;

/**
 *
 * @author USUARIO
 */
public class AutorDao {
    
    public List<Autor> getAllAutores(){
        
        List<Autor> lista = new ArrayList<>();
        
        String SQL = "SELECT * FROM autores";
        
        try (
                Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery()
                ){
            
            while (rs.next()) {                
                Autor a = new Autor();
                
                a.setIdAutor(rs.getInt("id_autor"));
                a.setNombre(rs.getString("nombre"));
                a.setApellido(rs.getString("apellido"));
                
                lista.add(a);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en getAllAutores:\n" + e.getMessage());
        }
        
        return lista;
    }
    
    public boolean crearAutor(Autor autor){
        String SQL = "INSERT INTO autores (nombre, apellido) VALUES (?, ?)";
        
        try (
                Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ){
            
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getApellido());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en crearAutor:\n" + e.getMessage());
            return false;
        }
    }
  
}
