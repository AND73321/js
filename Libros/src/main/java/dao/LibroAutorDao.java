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
import models.LibroAutor;
import utils.Conexion;

/**
 *
 * @author USUARIO
 */
public class LibroAutorDao {
    
    public List<LibroAutor> getAllAsignaciones(){
        List<LibroAutor> lista =  new ArrayList<>();
        
        String SQL = "SELECT * FROM libro_autor";
        
        try (
                Connection con =  Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();
                ){
            
            while (rs.next()) {                
                LibroAutor asignacion = new LibroAutor();
                
                asignacion.setIdAutor(rs.getInt("id_autor"));
                asignacion.setIdLibro(rs.getInt("id_libro"));
                
                lista.add(asignacion);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en getAllAsignaciones:\n" + e.getMessage());
        }
        return lista;
    }
    
    public boolean asignarAutor(LibroAutor liAu){
        String SQL = "INSERT INTO libro_autor (id_libro, id_autor) VALUES (?, ?)";
        
        try (
                Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ){
            
            ps.setInt(1, liAu.getIdLibro());
            ps.setInt(2, liAu.getIdAutor());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en asignarAutor:\n" + e.getMessage());
            return false;
        }
    }
    
}
