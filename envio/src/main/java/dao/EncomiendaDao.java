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
import models.Encomienda;
import utils.Conexion;

/**
 *
 * @author USUARIO
 */
public class EncomiendaDao {

    public List<Encomienda> getAllEncomiendas() {
        List<Encomienda> encomiendas = new ArrayList<>();

        String SQL = "SELECT * FROM encomineda";

        try (
                 Connection con = Conexion.getConnection();  PreparedStatement ps = con.prepareStatement(SQL);  ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
                Encomienda enco = new Encomienda();

                enco.setIdEncomienda(rs.getInt("id_encomienda"));
                enco.setOrigen(rs.getString("origen"));
                enco.setDestino(rs.getString("destino"));
                enco.setRecibe(rs.getString("recibe"));
                enco.setDescripcion(rs.getString("descripcion"));
                enco.setIdRemitente(rs.getString("id_remitente"));

                encomiendas.add(enco);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en getAllEncomiendas:\n" + e.getMessage());
        }
        return encomiendas;
    }

    public boolean postEncomienda(Encomienda enco) {
        String SQL = "INSERT INTO encomineda (origen, destino,recibe, descripcion, id_remitente) VALUES (?,?,?,?,?)";

        try (
                 Connection con = Conexion.getConnection();  PreparedStatement ps = con.prepareStatement(SQL);) {
            ps.setString(1, enco.getOrigen());
            ps.setString(2, enco.getDestino());
            ps.setString(3, enco.getRecibe());
            ps.setString(4, enco.getDescripcion());
            ps.setString(5, enco.getIdRemitente());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en postEncomienda:\n" + e.getMessage());
            return false;
        }
    }

    public List<Encomienda> getByRemitente(String idRemitente) {
        List<Encomienda> encoRemi = new ArrayList<>();

        String SQL = "SELECT * FROM encomineda WHERE id_remitente = ?";

        try (
                 Connection con = Conexion.getConnection();  PreparedStatement ps = con.prepareStatement(SQL);) {
            ps.setString(1, idRemitente);
            try ( ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Encomienda enco = new Encomienda();

                    enco.setIdEncomienda(rs.getInt("id_encomienda"));
                    enco.setOrigen(rs.getString("origen"));
                    enco.setDestino(rs.getString("destino"));
                    enco.setRecibe(rs.getString("recibe"));
                    enco.setDescripcion(rs.getString("descripcion"));
                    enco.setIdRemitente(rs.getString("id_remitente"));

                    encoRemi.add(enco);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en getByRemitente:\n" + e.getMessage());
        }
        return encoRemi;
    }

    public List<Encomienda> getByRecibe(String idRecibe) {
        List<Encomienda> listaRecibe = new ArrayList<>();

        String SQL = "SELECT * FROM encomineda WHERE recibe = ?";

        try (
                 Connection con = Conexion.getConnection();  PreparedStatement ps = con.prepareStatement(SQL);) {
            ps.setString(1, idRecibe);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Encomienda encomienda = new Encomienda();

                    encomienda.setIdEncomienda(rs.getInt("id_encomienda"));
                    encomienda.setOrigen(rs.getString("origen"));
                    encomienda.setDestino(rs.getString("destino"));
                    encomienda.setRecibe(rs.getString("recibe"));
                    encomienda.setDescripcion(rs.getString("descripcion"));
                    encomienda.setIdRemitente(rs.getString("id_remitente"));
                    
                    listaRecibe.add(encomienda);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en getByRecibe:\n" + e.getMessage());
        }
        return listaRecibe;
    }

}
