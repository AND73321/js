/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author USUARIO
 */
public class Autor {
    public int id_autor;
    public String nombre;
    public String apellido;
    public String nacionalidad;
    
    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}
