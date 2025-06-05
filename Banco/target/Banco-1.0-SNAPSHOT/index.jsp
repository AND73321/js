<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, dao.ClienteDao, dao.TransaccionDao, models.Cliente, models.Transaccion" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8" />
    <title>Sistema Bancario - Clientes y Transacciones</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
      crossorigin="anonymous"
    />
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
      crossorigin="anonymous"
    ></script>
  </head>
  <body class="bg-light">
    <div class="container py-4">

      <h1 class="mb-4">Sistema Bancario</h1>

      <!-- MENSAJES (Cliente / Transacción) -->
      <%
        String msgC = (String) session.getAttribute("mensajeCliente");
        if (msgC != null) {
      %>
        <div class="alert alert-info alert-dismissible fade show" role="alert">
          <%= msgC %>
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      <%
          session.removeAttribute("mensajeCliente");
        }
        String msgT = (String) session.getAttribute("mensajeTransaccion");
        if (msgT != null) {
      %>
        <div class="alert alert-info alert-dismissible fade show" role="alert">
          <%= msgT %>
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      <%
          session.removeAttribute("mensajeTransaccion");
        }
      %>

      <!-- SECCIÓN 1: Crear / Listar CLIENTES -->
      <div class="card mb-5">
        <div class="card-header bg-primary text-white">
          <h4>Clientes</h4>
        </div>
        <div class="card-body">
          <form action="crearCliente" method="post" class="row g-3 mb-4">
            <div class="col-md-3">
              <input type="text" name="cedula" class="form-control" placeholder="Cédula" required />
            </div>
            <div class="col-md-3">
              <input type="text" name="nombre" class="form-control" placeholder="Nombre" required />
            </div>
            <div class="col-md-3">
              <input type="text" name="apellido" class="form-control" placeholder="Apellido" required />
            </div>
            <div class="col-md-2">
              <input
                type="number"
                step="0.01"
                name="saldo"
                class="form-control"
                placeholder="Saldo inicial"
                required
              />
            </div>
            <div class="col-md-1">
              <button type="submit" class="btn btn-success w-100">Crear</button>
            </div>
          </form>

          <%
            ClienteDao cd = new ClienteDao();
            List<Cliente> listaClientes = cd.getAllClients();
          %>
          <table class="table table-striped table-hover">
            <thead class="table-dark">
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
                  <td colspan="4" class="text-center">No hay clientes registrados.</td>
                </tr>
              <%
                }
              %>
            </tbody>
          </table>
        </div>
      </div>

      <!-- SECCIÓN 2: Crear / Listar TRANSACCIONES -->
      <div class="card mb-5">
        <div class="card-header bg-success text-white">
          <h4>Transacciones</h4>
        </div>
        <div class="card-body">
          <form action="procesarTransaccion" method="post" class="row g-3 mb-4">
            <div class="col-md-3">
              <input type="text" name="cedula" class="form-control" placeholder="Cédula del cliente" required />
            </div>
            <div class="col-md-3">
              <select name="tipo" class="form-select" required>
                <option value="">– Seleccione Tipo –</option>
                <option value="DEPOSITO">Depósito</option>
                <option value="RETIRO">Retiro</option>
              </select>
            </div>
            <div class="col-md-3">
              <input
                type="number"
                step="0.01"
                name="monto"
                class="form-control"
                placeholder="Monto ($)"
                required
              />
            </div>
            <div class="col-md-3">
              <button type="submit" class="btn btn-success w-100">Ejecutar</button>
            </div>
          </form>

          <%
            TransaccionDao td = new TransaccionDao();
            List<Transaccion> listaTran = td.getAllTransacciones();
          %>
          <table class="table table-striped table-hover">
            <thead class="table-dark">
              <tr>
                <th>ID</th>
                <th>Cédula</th>
                <th>Tipo</th>
                <th>Monto</th>
                <!-- Eliminamos la columna “Fecha” -->
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
                  <!-- Quitamos la celda de fecha -->
                </tr>
              <%
                }
                if (listaTran.isEmpty()) {
              %>
                <tr>
                  <td colspan="4" class="text-center">No hay transacciones registradas.</td>
                </tr>
              <%
                }
              %>
            </tbody>
          </table>
        </div>
      </div>

    </div>
  </body>
</html>
