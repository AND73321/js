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
import models.Libro;
import utils.Conexion;

/**
 *
 * @author USUARIO
 */
public class LibroDao {
    
    public List<Libro> getAllLibros(){
        
        List<Libro> lista = new ArrayList<>();
        
        String SQL = "SELECT * FROM  libros";
        
        try (
                Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery()
                ){
            
            while (rs.next()) {                
                Libro libro = new Libro();
                
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                lista.add(libro);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en getAllLibros:\n" + e.getMessage());
        }
        return lista;
    }
    
    public boolean crearLibro(Libro libro){
        String SQL = "INSERT INTO libros (titulo) VALUES (?)";
        
        try (
                Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ){
            ps.setString(1, libro.getTitulo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en crearLibro:\n" + e.getMessage());
            return false;
        }
    }
    
}
