/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author USUARIO
 */
public class LibroAutor {
    private int idLibro;
    private int idAutor;
    
    public LibroAutor(){
        
    }

    public LibroAutor(int idLibro, int idAutor) {
        this.idLibro = idLibro;
        this.idAutor = idAutor;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    
    
}
