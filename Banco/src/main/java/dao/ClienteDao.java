/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Cliente;
import utils.Concexion;

/**
 *
 * @author USUARIO
 */
public class ClienteDao {
    
    public List<Cliente> getAllClients() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = Concexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCedula(rs.getString("cedula"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setSaldo(rs.getDouble("saldo"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en getAllClients:\n" + e.getMessage());
        }
        return clientes;
    }
    
    public boolean crearCliente(Cliente cliente) {
        String SQL = "INSERT INTO clientes (cedula, nombre, apellido, saldo) VALUES (?, ?, ?, ?)";
        try (Connection con = Concexion.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setDouble(4, cliente.getSaldo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en crearCliente:\n" + e.getMessage());
            return false;
        }
    }

    public boolean actualizarSaldo(String cedula, double nuevoSaldo) throws Exception {
        String SQL = "UPDATE clientes SET saldo = ? WHERE cedula = ?";
        try (Connection con = Concexion.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setDouble(1, nuevoSaldo);
            ps.setString(2, cedula);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en actualizarSaldo:\n" + e.getMessage());
            return false;
        }
    }

    public Cliente buscarPorCedula(String cedula) {
        Cliente cliente = null;
        String SQL = "SELECT * FROM clientes WHERE cedula = ?";
        try (Connection con = Concexion.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setCedula(rs.getString("cedula"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setSaldo(rs.getDouble("saldo"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en buscarPorCedula:\n" + e.getMessage());
        }
        return cliente; // Será null si no lo encontró
    }
}
