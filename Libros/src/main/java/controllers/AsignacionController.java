/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.AutorDao;
import dao.LibroAutorDao;
import dao.LibroDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Autor;
import models.Libro;
import models.LibroAutor;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "AsignacionController", urlPatterns = {"/asignarAutor"})
public class AsignacionController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LibroAutorDao laDao = new LibroAutorDao();
    private AutorDao autorDao = new AutorDao();
    private LibroDao libroDao = new LibroDao();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AsignacionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AsignacionController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idLibroStr = request.getParameter("id_libro");
        String idAutorStr = request.getParameter("id_autor");

        String mensaje;
        int idLibro = 0, idAutor = 0;
        try {
            idLibro = Integer.parseInt(idLibroStr);
            idAutor = Integer.parseInt(idAutorStr);
        } catch (NumberFormatException e) {
            mensaje = "Libro y Autor deben ser válidos.";
            request.getSession().setAttribute("mensajeAsignacion", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        // 1) Obtener listas completas de libros y autores
        List<Libro> listaLibros = libroDao.getAllLibros();
        List<Autor> listaAutores = autorDao.getAllAutores();

        // 2) Verificar existencia de libro
        boolean libroExiste = false;
        for (Libro l : listaLibros) {
            if (l.getIdLibro() == idLibro) {
                libroExiste = true;
                break;
            }
        }

        // 3) Verificar existencia de autor
        boolean autorExiste = false;
        for (Autor a : listaAutores) {
            if (a.getIdAutor() == idAutor) {
                autorExiste = true;
                break;
            }
        }

        // 4) Si alguno no existe, redirigir con mensaje
        if (!libroExiste || !autorExiste) {
            mensaje = "Libro o Autor no existen.";
            request.getSession().setAttribute("mensajeAsignacion", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        // 5) Crear la asignación y guardarla
        LibroAutor la = new LibroAutor(idLibro, idAutor);
        boolean ok = laDao.asignarAutor(la);
        mensaje = ok ? "Autor asignado al libro con éxito."
                : "Error al asignar autor.";

        request.getSession().setAttribute("mensajeAsignacion", mensaje);
        response.sendRedirect("index.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
