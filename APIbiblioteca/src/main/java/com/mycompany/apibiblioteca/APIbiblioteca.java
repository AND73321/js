/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.apibiblioteca;

import Models.Libro;
import Service.ApiService;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class APIbiblioteca {

    public static void main(String[] args) {
        try {
        List<Libro> libros = ApiService.obtenerLibros();
        for (Libro libro : libros) {
            System.out.println(libro.titulo); // Solo para ver si realmente hay datos
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
