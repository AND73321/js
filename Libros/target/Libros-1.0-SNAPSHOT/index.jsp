<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="dao.AutorDao, dao.LibroDao, dao.LibroAutorDao" %>
<%@ page import="models.Autor, models.Libro, models.LibroAutor" %>

<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8" />
    <title>Sistema Biblioteca</title>
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

      <h1 class="mb-4">Sistema Biblioteca</h1>

      <!-- =================================================================== -->
      <!--                              MENSAJES                                 -->
      <!-- =================================================================== -->
      <%
        String msgA = (String) session.getAttribute("mensajeAutor");
        if (msgA != null) {
      %>
        <div class="alert alert-info alert-dismissible fade show" role="alert">
          <%= msgA %>
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      <%
          session.removeAttribute("mensajeAutor");
        }

        String msgL = (String) session.getAttribute("mensajeLibro");
        if (msgL != null) {
      %>
        <div class="alert alert-info alert-dismissible fade show" role="alert">
          <%= msgL %>
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      <%
          session.removeAttribute("mensajeLibro");
        }

        String msgAs = (String) session.getAttribute("mensajeAsignacion");
        if (msgAs != null) {
      %>
        <div class="alert alert-info alert-dismissible fade show" role="alert">
          <%= msgAs %>
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      <%
          session.removeAttribute("mensajeAsignacion");
        }
      %>

      <!-- =================================================================== -->
      <!--                           SECCIÓN AUTORES                            -->
      <!-- =================================================================== -->
      <div class="card mb-5">
        <div class="card-header bg-primary text-white">
          <h4>Autores</h4>
        </div>
        <div class="card-body">
          <!-- Formulario para crear nuevo autor -->
          <form action="crearAutor" method="post" class="row g-3 mb-4">
            <div class="col-md-4">
              <input type="text" name="nombre" class="form-control" placeholder="Nombre" required />
            </div>
            <div class="col-md-4">
              <input type="text" name="apellido" class="form-control" placeholder="Apellido" required />
            </div>
            <div class="col-md-4">
              <button type="submit" class="btn btn-success w-100">Crear Autor</button>
            </div>
          </form>

          <%
            AutorDao ad = new AutorDao();
            List<Autor> listaAutores = ad.getAllAutores();
          %>
          <table class="table table-striped table-hover">
            <thead class="table-dark">
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Apellido</th>
              </tr>
            </thead>
            <tbody>
              <%
                for (Autor a : listaAutores) {
              %>
                <tr>
                  <td><%= a.getIdAutor() %></td>
                  <td><%= a.getNombre()   %></td>
                  <td><%= a.getApellido() %></td>
                </tr>
              <%
                }
                if (listaAutores.isEmpty()) {
              %>
                <tr>
                  <td colspan="3" class="text-center">No hay autores registrados.</td>
                </tr>
              <%
                }
              %>
            </tbody>
          </table>
        </div>
      </div>

      <!-- =================================================================== -->
      <!--                           SECCIÓN LIBROS                             -->
      <!-- =================================================================== -->
      <%
        LibroDao ld = new LibroDao();
        List<Libro> listaLibros = ld.getAllLibros();
      %>
      <div class="card mb-5">
        <div class="card-header bg-success text-white">
          <h4>Libros</h4>
        </div>
        <div class="card-body">
          <!-- Formulario para crear nuevo libro -->
          <form action="crearLibro" method="post" class="row g-3 mb-4">
            <div class="col-md-8">
              <input type="text" name="titulo" class="form-control" placeholder="Título del libro" required />
            </div>
            <div class="col-md-4">
              <button type="submit" class="btn btn-success w-100">Crear Libro</button>
            </div>
          </form>

          <table class="table table-striped table-hover">
            <thead class="table-dark">
              <tr>
                <th>ID</th>
                <th>Título</th>
              </tr>
            </thead>
            <tbody>
              <%
                for (Libro l : listaLibros) {
              %>
                <tr>
                  <td><%= l.getIdLibro() %></td>
                  <td><%= l.getTitulo()   %></td>
                </tr>
              <%
                }
                if (listaLibros.isEmpty()) {
              %>
                <tr>
                  <td colspan="2" class="text-center">No hay libros registrados.</td>
                </tr>
              <%
                }
              %>
            </tbody>
          </table>
        </div>
      </div>

      <!-- =================================================================== -->
      <!--                    SECCIÓN ASIGNACIONES LIBRO–AUTOR                 -->
      <!-- =================================================================== -->
      <%
        // Si listaAutores estuviera a null (por alguna razón), recargarla
        if (listaAutores == null) {
            listaAutores = new AutorDao().getAllAutores();
        }
        // Ya tenemos listaLibros arriba
        LibroAutorDao lad = new LibroAutorDao();
        List<LibroAutor> listaLA = lad.getAllAsignaciones();
      %>
      <div class="card mb-5">
        <div class="card-header bg-warning text-white">
          <h4>Asignar Autor a Libro</h4>
        </div>
        <div class="card-body">
          <!-- Formulario para asignar autor a libro -->
          <form action="asignarAutor" method="post" class="row g-3 mb-4">
            <div class="col-md-4">
              <select name="id_libro" class="form-select" required>
                <option value="">– Seleccione Libro –</option>
                <%
                  for (Libro l : listaLibros) {
                %>
                  <option value="<%= l.getIdLibro() %>"><%= l.getTitulo() %></option>
                <%
                  }
                %>
              </select>
            </div>
            <div class="col-md-4">
              <select name="id_autor" class="form-select" required>
                <option value="">– Seleccione Autor –</option>
                <%
                  for (Autor a : listaAutores) {
                %>
                  <option value="<%= a.getIdAutor() %>"><%= a.getNombre() %> <%= a.getApellido() %></option>
                <%
                  }
                %>
              </select>
            </div>
            <div class="col-md-4">
              <button type="submit" class="btn btn-success w-100">Asignar</button>
            </div>
          </form>

          <table class="table table-striped table-hover">
            <thead class="table-dark">
              <tr>
                <th>Libro</th>
                <th>Autor</th>
              </tr>
            </thead>
            <tbody>
              <%
                for (LibroAutor la : listaLA) {
                  // 1) Obtener título del libro
                  String titulo = "-";
                  for (Libro l : listaLibros) {
                      if (l.getIdLibro() == la.getIdLibro()) {
                          titulo = l.getTitulo();
                          break;
                      }
                  }
                  // 2) Obtener nombre completo del autor
                  String nombreAutor = "-";
                  for (Autor a : listaAutores) {
                      if (a.getIdAutor() == la.getIdAutor()) {
                          nombreAutor = a.getNombre() + " " + a.getApellido();
                          break;
                      }
                  }
              %>
                <tr>
                  <td><%= titulo        %></td>
                  <td><%= nombreAutor   %></td>
                </tr>
              <%
                }
                if (listaLA.isEmpty()) {
              %>
                <tr>
                  <td colspan="2" class="text-center">No hay asignaciones registradas.</td>
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


