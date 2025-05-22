/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Models.Autor;
import Models.Libro;
import Models.LibroAutor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class ApiService {

    private static final String BASE_URL = "http://localhost/biblioteca-api/api";

    public static List<Libro> obtenerLibros() throws IOException {
        URL url = new URL(BASE_URL + "/libros.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            Type listType = new TypeToken<List<Libro>>() {
            }.getType();
            return new Gson().fromJson(reader, listType);
        }
    }

    public static List<Autor> obtenerAutores() throws IOException {
        URL url = new URL(BASE_URL + "/autores.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            Type listType = new TypeToken<List<Autor>>() {
            }.getType();
            return new Gson().fromJson(reader, listType);
        }
    }

    public static boolean insertarLibro(Libro libro) throws IOException {
        URL url = new URL(BASE_URL + "/libros.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String json = new Gson().toJson(libro);
        try ( OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    public static boolean insertarAutor(Autor autor) throws IOException {
        URL url = new URL(BASE_URL + "/autores.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String json = new Gson().toJson(autor);
        try ( OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    public static List<LibroAutor> obtenerRelaciones() throws IOException {
        URL url = new URL(BASE_URL + "/libro_autor.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            Type listType = new TypeToken<List<LibroAutor>>() {
            }.getType();
            return new Gson().fromJson(reader, listType);
        }
    }
    

    public static void asignarAutorALibro(int idLibro, int idAutor) throws IOException {
        URL url = new URL("http://localhost/biblioteca-api/api/libro_autor.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        String jsonInputString = String.format("{\"id_libro\": %d, \"id_autor\": %d}", idLibro, idAutor);

        try ( OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Respuesta de API: " + response.toString());
            }
        } else {
            System.out.println("Error al asignar autor al libro. Código HTTP: " + responseCode);
        }

        conn.disconnect();
    }

}
