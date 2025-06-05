<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, dao.ClienteDao, dao.TransaccionDao, models.Cliente, models.Transaccion" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <title>Sistema Bancario - Versión Simple</title>
</head>
<body>

  <h1>Sistema Bancario</h1>

  <!-- Mensajes (Cliente / Transacción) -->
  <%
    String msgC = (String) session.getAttribute("mensajeCliente");
    if (msgC != null) {
  %>
    <div style="border:1px solid #888; padding:8px; margin-bottom:10px;">
      <strong>Mensaje Cliente:</strong> <%= msgC %>
    </div>
  <%
        session.removeAttribute("mensajeCliente");
    }
    String msgT = (String) session.getAttribute("mensajeTransaccion");
    if (msgT != null) {
  %>
    <div style="border:1px solid #888; padding:8px; margin-bottom:10px;">
      <strong>Mensaje Transacción:</strong> <%= msgT %>
    </div>
  <%
        session.removeAttribute("mensajeTransaccion");
    }
  %>

  <hr />

  <!-- SECCIÓN 1: Crear / Listar CLIENTES -->
  <h2>Clientes</h2>
  <form action="crearCliente" method="post" style="margin-bottom:20px;">
    <label for="cedula">Cédula:</label>
    <input type="text" id="cedula" name="cedula" required />
    &nbsp;&nbsp;
    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre" required />
    &nbsp;&nbsp;
    <label for="apellido">Apellido:</label>
    <input type="text" id="apellido" name="apellido" required />
    &nbsp;&nbsp;
    <label for="saldo">Saldo inicial:</label>
    <input type="number" step="0.01" id="saldo" name="saldo" required />
    &nbsp;&nbsp;
    <button type="submit">Crear Cliente</button>
  </form>

  <%
    ClienteDao cd = new ClienteDao();
    List<Cliente> listaClientes = cd.getAllClients();
  %>
  <table border="1" cellpadding="4" cellspacing="0" style="margin-bottom:30px; width:100%;">
    <thead>
      <tr>
        <th>Cédula</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Saldo</th>
      </tr>
    </thead>
    <tbody>
      <%
        for (Cliente cl : listaClientes) {
      %>
        <tr>
          <td><%= cl.getCedula() %></td>
          <td><%= cl.getNombre() %></td>
          <td><%= cl.getApellido() %></td>
          <td>$<%= String.format("%.2f", cl.getSaldo()) %></td>
        </tr>
      <%
        }
        if (listaClientes.isEmpty()) {
      %>
        <tr>
          <td colspan="4" style="text-align:center;">No hay clientes registrados.</td>
        </tr>
      <%
        }
      %>
    </tbody>
  </table>

  <hr />

  <!-- SECCIÓN 2: Crear / Listar TRANSACCIONES -->
  <h2>Transacciones</h2>
  <form action="procesarTransaccion" method="post" style="margin-bottom:20px;">
    <label for="cedulaT">Cédula del cliente:</label>
    <input type="text" id="cedulaT" name="cedula" required />
    &nbsp;&nbsp;
    <label for="tipo">Tipo:</label>
    <select id="tipo" name="tipo" required>
      <option value="">-- Seleccione --</option>
      <option value="DEPOSITO">Depósito</option>
      <option value="RETIRO">Retiro</option>
    </select>
    &nbsp;&nbsp;
    <label for="monto">Monto ($):</label>
    <input type="number" step="0.01" id="monto" name="monto" required />
    &nbsp;&nbsp;
    <button type="submit">Ejecutar</button>
  </form>

  <%
    TransaccionDao td = new TransaccionDao();
    List<Transaccion> listaTran = td.getAllTransacciones();
  %>
  <table border="1" cellpadding="4" cellspacing="0" style="width:100%;">
    <thead>
      <tr>
        <th>ID</th>
        <th>Cédula</th>
        <th>Tipo</th>
        <th>Monto</th>
      </tr>
    </thead>
    <tbody>
      <%
        for (Transaccion trx : listaTran) {
      %>
        <tr>
          <td><%= trx.getIdTransaccion() %></td>
          <td><%= trx.getCedula() %></td>
          <td><%= trx.getTipo() %></td>
          <td>$<%= String.format("%.2f", trx.getMonto()) %></td>
        </tr>
      <%
        }
        if (listaTran.isEmpty()) {
      %>
        <tr>
          <td colspan="4" style="text-align:center;">No hay transacciones registradas.</td>
        </tr>
      <%
        }
      %>
    </tbody>
  </table>

</body>
</html>
