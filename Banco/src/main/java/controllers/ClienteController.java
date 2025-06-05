/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.ClienteDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Cliente;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "ClienteController", urlPatterns = {"/crearCliente"})
public class ClienteController extends HttpServlet {

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
            out.println("<title>Servlet ClienteController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClienteController at " + request.getContextPath() + "</h1>");
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
    
    private static final long serialVersionUID = 1L;
    private ClienteDao clienteDao = new ClienteDao();
    
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
        // 1) Leer parámetros del formulario de cliente
        String cedula   = request.getParameter("cedula");
        String nombre   = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String saldoStr = request.getParameter("saldo");

        String mensaje;
        double saldo = 0;
        try {
            saldo = Double.parseDouble(saldoStr);
        } catch (NumberFormatException e) {
            mensaje = "Saldo inválido.";
            request.getSession().setAttribute("mensajeCliente", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        // 2) Validar que no vengan vacíos
        if (cedula == null || cedula.isEmpty() ||
            nombre == null || nombre.isEmpty() ||
            apellido == null || apellido.isEmpty()) {
            mensaje = "Todos los campos de cliente son obligatorios.";
            request.getSession().setAttribute("mensajeCliente", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        // 3) Construir objeto Cliente e intentar insertarlo
        Cliente cliente = new Cliente(cedula, nombre, apellido, saldo);
        boolean ok = clienteDao.crearCliente(cliente);

        if (ok) {
            mensaje = "Cliente creado con éxito.";
        } else {
            mensaje = "Error al crear cliente. Verifica que la cédula no esté duplicada.";
        }

        // 4) Guardar mensaje en sesión y redirigir a index.jsp
        request.getSession().setAttribute("mensajeCliente", mensaje);
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
