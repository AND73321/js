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
import models.Transaccion;
import utils.Concexion;

/**
 *
 * @author USUARIO
 */
public class TransaccionDao {
    
    public List<Transaccion> getAllTransacciones() throws Exception {
        List<Transaccion> listaTransacciones = new ArrayList<>();
        String SQL = "SELECT id_transaccion, cedula, tipo, monto FROM transacciones ORDER BY id_transaccion DESC";
        try (
            Connection con = Concexion.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Transaccion trans = new Transaccion();
                trans.setIdTransaccion(rs.getInt("id_transaccion"));
                trans.setCedula(rs.getString("cedula"));
                trans.setTipo(rs.getString("tipo"));
                trans.setMonto(rs.getDouble("monto"));
                // No leemos ni seteamos fecha
                listaTransacciones.add(trans);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en getAllTransacciones:\n" + e.getMessage());
        }
        return listaTransacciones;
    }

    public boolean insertTransaccion(Transaccion transa) throws Exception {
        String SQL = "INSERT INTO transacciones (cedula, tipo, monto) VALUES (?, ?, ?)";
        try (
            Connection con = Concexion.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL)
        ) {
            ps.setString(1, transa.getCedula());
            ps.setString(2, transa.getTipo().toUpperCase());
            ps.setDouble(3, transa.getMonto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en insertTransaccion:\n" + e.getMessage());
            return false;
        }
    }
    
}
