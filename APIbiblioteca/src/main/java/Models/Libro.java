/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.List;

/**
 *
 * @author USUARIO
 */
public class Libro {
    public int id_libro;
    public String titulo;
    public int anio;
    public List<Autor> autores;
    
    @Override
public String toString() {
    return titulo;
}

}
