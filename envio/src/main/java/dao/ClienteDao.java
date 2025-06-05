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
import models.Cliente;
import utils.Conexion;

/**
 *
 * @author USUARIO
 */
public class ClienteDao {
    
    
    public List<Cliente> getAllClients(){
        List<Cliente> clientes = new ArrayList<>();
        
        String SQL = "SELECT * FROM cliente";
        
        try (
                Connection con  = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();
                ){
            
            while (rs.next()) {                
                Cliente cli = new Cliente();
                
                cli.setCedula(rs.getString("cedula"));
                cli.setNombre(rs.getString("nombre"));
                clientes.add(cli);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en getAllClients:\n" + e.getMessage());
        }
        return clientes;
    }
    
    
    public boolean postClient(Cliente client){
        String SQL = "INSERT INTO cliente (cedula, nombre) VALUES (?,?)";
        try (
                Connection con  = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(SQL);
                ){
            
            ps.setString(1, client.getCedula());
            ps.setString(2, client.getNombre());
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en postClient:\n" + e.getMessage());
            return false;
        }
    }
    
}
