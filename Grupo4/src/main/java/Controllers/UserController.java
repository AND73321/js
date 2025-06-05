/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.User;
import Services.UserService;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    @Override
    /*protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8"); 
            List<User> users = this.userService.getAll();
            String json = gson.toJson(users);
            response.getWriter().write(json);
    }*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    // Revisamos si el cliente envió “?cedula=…”
    String cedulaParam = request.getParameter("cedula");
    if (cedulaParam != null && !cedulaParam.isEmpty()) {
        User plantilla = new User();
        plantilla.setCedula(cedulaParam);

        User resultado = userService.getId(plantilla);
        if (resultado != null) {
            response.getWriter().write(gson.toJson(resultado));
        } else {
            // Si no se encontró, podemos devolver 404 o un JSON vacio/objeto nulo
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"message\":\"Usuario no encontrado\"}");
        }
    } else {
        // No vino “cedula” → devolvemos lista completa
        List<User> users = userService.getAll();
        response.getWriter().write(gson.toJson(users));
    }
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
            response.setContentType("text/html;charset=UTF-8");
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        try {
            boolean create = userService.save(user);
            if(create){
                sendResponse(response, HttpServletResponse.SC_CREATED, "Usuario creado con exito");
            } else {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Error al crear el usuario");
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendResponse(HttpServletResponse response, int status, String message) throws IOException{
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write("{\"message\":\""+message+"\"}");
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        try {
            boolean update = userService.update(user);
            if(update){
                sendResponse(response, HttpServletResponse.SC_CREATED, "Usuario actualizado con exito");
            } else {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Error al actualizar el usuario");
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    /*protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        try {
            boolean delete = userService.delete(user);
            if(delete){
                sendResponse(response, HttpServletResponse.SC_CREATED, "Usuario eliminado con exito");
            } else {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Error al eliminar el usuario");
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    BufferedReader reader = request.getReader();
    User user = null;
    try {
        user = new Gson().fromJson(reader, User.class);
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"message\":\"JSON inválido en DELETE\"}");
        return;
    }
    if (user == null || user.getCedula() == null || user.getCedula().isEmpty()) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"message\":\"Cedula requerida para eliminar\"}");
        return;
    }

    try {
        boolean deleted = userService.delete(user);
        if (deleted) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Usuario eliminado con éxito\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"message\":\"No se encontró usuario con cedula: " + user.getCedula() + "\"}");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("{\"message\":\"Error interno al eliminar: " + ex.getMessage() + "\"}");
    }
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