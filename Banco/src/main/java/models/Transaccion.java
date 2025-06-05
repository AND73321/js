/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author USUARIO
 */
public class Transaccion {
    private int idTransaccion;
    private String cedula;       // FK a Cliente
    private String tipo;         // "DEPOSITO" o "RETIRO"
    private double monto;

    public Transaccion(){}
    
    public Transaccion(int idTransaccion, String cedula, String tipo, double monto) {
        this.idTransaccion = idTransaccion;
        this.cedula = cedula;
        this.tipo = tipo;
        this.monto = monto;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    
}
