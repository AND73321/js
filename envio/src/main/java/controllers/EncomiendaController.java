/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.EncomiendaDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Encomienda;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "EncomiendaController", urlPatterns = {"/EncomiendaController"})
public class EncomiendaController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EncomiendaDao encomiendas = new EncomiendaDao();

    
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
        String origen = request.getParameter("origen");
        String destino = request.getParameter("destino");
        String recibe = request.getParameter("recibe");
        String descripcion = request.getParameter("descripcion");
        String idRemitente = request.getParameter("id_remitente");
        
        //Encomienda enco = new Encomienda(origen, destino, recibe, descripcion, id_remitente);
        Encomienda enco = new Encomienda();
        
        enco.setOrigen(origen);
        enco.setDestino(destino);
        enco.setRecibe(recibe);
        enco.setDescripcion(descripcion);
        enco.setIdRemitente(idRemitente);
        
        boolean ok = encomiendas.postEncomienda(enco);
        
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
