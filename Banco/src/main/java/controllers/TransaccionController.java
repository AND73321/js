/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.ClienteDao;
import dao.TransaccionDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Cliente;
import models.Transaccion;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "TransaccionController", urlPatterns = {"/procesarTransaccion"})
public class TransaccionController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final long serialVersionUID = 1L;
    private ClienteDao clienteDao         = new ClienteDao();
    private TransaccionDao transaccionDao = new TransaccionDao();
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TransaccionController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransaccionController at " + request.getContextPath() + "</h1>");
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
        String cedula = request.getParameter("cedula");
        String tipo   = request.getParameter("tipo");
        String montoStr = request.getParameter("monto");

        String mensaje;
        double monto = 0;
        try {
            monto = Double.parseDouble(montoStr);
        } catch (NumberFormatException e) {
            mensaje = "Monto inválido.";
            request.getSession().setAttribute("mensajeTransaccion", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        if (cedula == null || cedula.isEmpty() || tipo == null || tipo.isEmpty()) {
            mensaje = "Todos los campos de transacción son obligatorios.";
            request.getSession().setAttribute("mensajeTransaccion", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        Cliente c = clienteDao.buscarPorCedula(cedula);
        if (c == null) {
            mensaje = "Cliente con cédula " + cedula + " no existe.";
            request.getSession().setAttribute("mensajeTransaccion", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        double saldoActual = c.getSaldo();
        double nuevoSaldo;

        if ("DEPOSITO".equalsIgnoreCase(tipo)) {
            nuevoSaldo = saldoActual + monto;
        } else if ("RETIRO".equalsIgnoreCase(tipo)) {
            if (monto > saldoActual) {
                mensaje = "Saldo insuficiente para retirar $ " + monto;
                request.getSession().setAttribute("mensajeTransaccion", mensaje);
                response.sendRedirect("index.jsp");
                return;
            }
            nuevoSaldo = saldoActual - monto;
        } else {
            mensaje = "Tipo de transacción inválido.";
            request.getSession().setAttribute("mensajeTransaccion", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        boolean okUpdate = false;
        try {
            okUpdate = clienteDao.actualizarSaldo(cedula, nuevoSaldo);
        } catch (Exception ex) {
            Logger.getLogger(TransaccionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!okUpdate) {
            mensaje = "No se pudo actualizar el saldo del cliente.";
            request.getSession().setAttribute("mensajeTransaccion", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        Transaccion t = new Transaccion();
        t.setCedula(cedula);
        t.setTipo(tipo.toUpperCase());
        t.setMonto(monto);
        // No seteamos fecha porque no la usamos

        boolean okInsert = false;
        try {
            okInsert = transaccionDao.insertTransaccion(t);
        } catch (Exception ex) {
            Logger.getLogger(TransaccionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!okInsert) {
            mensaje = "No se pudo registrar la transacción.";
            request.getSession().setAttribute("mensajeTransaccion", mensaje);
            response.sendRedirect("index.jsp");
            return;
        }

        mensaje = "Transacción realizada con éxito. Nuevo saldo: $" + String.format("%.2f", nuevoSaldo);
        request.getSession().setAttribute("mensajeTransaccion", mensaje);
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
