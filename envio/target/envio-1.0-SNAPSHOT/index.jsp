<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dao.ClienteDao, dao.EncomiendaDao" %>
<%@ page import="models.Cliente, models.Encomienda" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Sistema Encomienda</h1>
        <h2>Cliente</h2>
        <form action="ClienteController" method="post">
            <label>Cedula:</label>
            <input type="text" name="cedula" required>
            <label>Nombre:</label>
            <input type="text" name="nombre" required>
            <button type="submit">Guardar</button>
        </form>
        <%
            ClienteDao clienteDao = new ClienteDao();
            List<Cliente> clientes = clienteDao.getAllClients();
        %>
        <br>
        <table border="1" cellpadding="4" cellspacing="0">
            <thead>
                <tr>
                    <th>Cedula</th>
                    <th>Nombre</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Cliente c : clientes) {
                %>
                <tr>
                    <td><%= c.getCedula()%></td>
                    <td><%= c.getNombre()%></td>
                </tr>
                <%
                    }
                    if (clientes.isEmpty()) {
                %>
                <tr>
                    <td colspan="2" style="text-align: center">No hay clientes</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <hr><!-- comment -->
        <h2>Encomiendas</h2>

        <form action="EncomiendaController" method="post">
            <label>Origen</label>
            <input type="text" name="origen">
            <label>Destino</label>
            <input type="text" name="destino">
            <label>Recibe</label>
            <input type="text" name="recibe">
            <label>Descripcion</label>
            <input type="text" name="descripcion">
            <label>Remitente</label>
            <input type="text" name="id_remitente">
            <button type="submit">Guardar</button>
        </form>
        <br><!-- comment -->
        <form action="index.jsp" method="get">
            <label>Buscar por Remitente</label>
            <input type="text" name="buscar">
            <button type="submit">Buscar</button>
        </form>
        <br><!-- comment -->
        <form action="index.jsp" method="get">
            <label>Buscar por quien Recibe:</label>
            <input type="text" name="buscarRecibe">
            <button type="submit">Buscar</button>
        </form>
        <br><!-- comment -->
        <%
            String filtroRemitente = request.getParameter("buscar");
            String filtroRecibe = request.getParameter("buscarRecibe");

            EncomiendaDao encoDao = new EncomiendaDao();
            List<Encomienda> encomiendas;

            if (filtroRemitente != null && !filtroRemitente.trim().isEmpty()) {
                // Si se buscó por remitente, ignoramos buscarRecibe
                encomiendas = encoDao.getByRemitente(filtroRemitente.trim());
            } else if (filtroRecibe != null && !filtroRecibe.trim().isEmpty()) {
                // Si no vino remitente pero sí “quien recibe”
                encomiendas = encoDao.getByRecibe(filtroRecibe.trim());
            } else {
                // Ningún filtro: listar todo
                encomiendas = encoDao.getAllEncomiendas();
            }
        %>
        <table border="1" cellpadding="4" cellspacing="0">
            <tr>
                <th>ID</th>
                <th>Origen</th>
                <th>Destino</th>
                <th>Recibe</th>
                <th>Descripcion</th>
                <th>Remitente</th>
            </tr>
            <%                    for (Encomienda e : encomiendas) {
            %>
            <tr>
                <td><%= e.getIdEncomienda()%></td>
                <td><%= e.getOrigen()%></td>
                <td><%= e.getDestino()%></td>
                <td><%= e.getRecibe()%></td>
                <td><%= e.getDescripcion()%></td>
                <td><%= e.getIdRemitente()%></td>
            </tr>
            <%
                }
                if (encomiendas.isEmpty()) {
            %>
            <tr>
                <td colspan="6" style="text-align: center">No hay encomiendas</td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
