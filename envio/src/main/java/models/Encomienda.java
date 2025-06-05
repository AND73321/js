/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author USUARIO
 */
public class Encomienda {
    private int idEncomienda;
    private String origen;
    private String destino;
    private String recibe;
    private String descripcion;
    private String idRemitente;
    
    public Encomienda(){}

    public Encomienda(int idEncomienda, String origen, String destino, String recibe, String descripcion, String idRemitente) {
        this.idEncomienda = idEncomienda;
        this.origen = origen;
        this.destino = destino;
        this.recibe = recibe;
        this.descripcion = descripcion;
        this.idRemitente = idRemitente;
    }

    public int getIdEncomienda() {
        return idEncomienda;
    }

    public void setIdEncomienda(int idEncomienda) {
        this.idEncomienda = idEncomienda;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(String idRemitente) {
        this.idRemitente = idRemitente;
    }

    
    
    
}
