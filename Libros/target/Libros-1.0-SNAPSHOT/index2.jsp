<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dao.AutorDao, dao.LibroDao, dao.LibroAutorDao" %>
<%@ page import="models.Autor, models.Libro, models.LibroAutor" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <title>Sistema Biblioteca (versión simple)</title>
</head>
<body>

  <h1>Sistema Biblioteca</h1>

  <!-- Mensajes de sesión -->
  <%
    String msgA = (String) session.getAttribute("mensajeAutor");
    if (msgA != null) {
  %>
    <div style="border: 1px solid #888; padding: 8px; margin-bottom: 10px;">
      <strong>Mensaje Autores:</strong> <%= msgA %>
    </div>
  <%
      session.removeAttribute("mensajeAutor");
    }

    String msgL = (String) session.getAttribute("mensajeLibro");
    if (msgL != null) {
  %>
    <div style="border: 1px solid #888; padding: 8px; margin-bottom: 10px;">
      <strong>Mensaje Libros:</strong> <%= msgL %>
    </div>
  <%
      session.removeAttribute("mensajeLibro");
    }

    String msgAs = (String) session.getAttribute("mensajeAsignacion");
    if (msgAs != null) {
  %>
    <div style="border: 1px solid #888; padding: 8px; margin-bottom: 10px;">
      <strong>Mensaje Asignación:</strong> <%= msgAs %>
    </div>
  <%
      session.removeAttribute("mensajeAsignacion");
    }
  %>

  <hr />

  <!-- SECCIÓN AUTORES -->
  <h2>Autores</h2>
  <form action="crearAutor" method="post">
    <label>Nombre:</label>
    <input type="text" name="nombre" required />
    <label>Apellido:</label>
    <input type="text" name="apellido" required />
    <button type="submit">Crear Autor</button>
  </form>

  <%
    AutorDao ad = new AutorDao();
    List<Autor> listaAutores = ad.getAllAutores();
  %>
  <br />
  <table border="1" cellpadding="4" cellspacing="0">
    <tr>
      <th>ID</th>
      <th>Nombre</th>
      <th>Apellido</th>
    </tr>
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
        <td colspan="3" style="text-align:center;">No hay autores registrados.</td>
      </tr>
    <%
      }
    %>
  </table>

  <hr />

  <!-- SECCIÓN LIBROS -->
  <h2>Libros</h2>
  <form action="crearLibro" method="post">
    <label>Título del libro:</label>
    <input type="text" name="titulo" required />
    <button type="submit">Crear Libro</button>
  </form>

  <%
    LibroDao ld = new LibroDao();
    List<Libro> listaLibros = ld.getAllLibros();
  %>
  <br />
  <table border="1" cellpadding="4" cellspacing="0">
    <tr>
      <th>ID</th>
      <th>Título</th>
    </tr>
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
        <td colspan="2" style="text-align:center;">No hay libros registrados.</td>
      </tr>
    <%
      }
    %>
  </table>

  <hr />

  <!-- SECCIÓN ASIGNAR AUTOR A LIBRO -->
  <h2>Asignar Autor a Libro</h2>
  <%
    // Asegurarse de que listaAutores y listaLibros existan
    if (listaAutores == null) {
      listaAutores = new AutorDao().getAllAutores();
    }
    // listaLibros ya está definida arriba
    LibroAutorDao lad = new LibroAutorDao();
    List<LibroAutor> listaLA = lad.getAllAsignaciones();
  %>
  <form action="asignarAutor" method="post">
    <label>Libro:</label>
    <select name="id_libro" required>
      <option value="">-- Seleccione Libro --</option>
      <%
        for (Libro l : listaLibros) {
      %>
        <option value="<%= l.getIdLibro() %>"><%= l.getTitulo() %></option>
      <%
        }
      %>
    </select>

    <label>Autor:</label>
    <select name="id_autor" required>
      <option value="">-- Seleccione Autor --</option>
      <%
        for (Autor a : listaAutores) {
      %>
        <option value="<%= a.getIdAutor() %>"><%= a.getNombre() %> <%= a.getApellido() %></option>
      <%
        }
      %>
    </select>

    <button type="submit">Asignar</button>
  </form>

  <br />
  <table border="1" cellpadding="4" cellspacing="0">
    <tr>
      <th>Libro</th>
      <th>Autor</th>
    </tr>
    <%
      for (LibroAutor la : listaLA) {
        // Obtener título del libro
        String titulo = "-";
        for (Libro l : listaLibros) {
          if (l.getIdLibro() == la.getIdLibro()) {
            titulo = l.getTitulo();
            break;
          }
        }
        // Obtener nombre completo del autor
        String nombreAutor = "-";
        for (Autor a : listaAutores) {
          if (a.getIdAutor() == la.getIdAutor()) {
            nombreAutor = a.getNombre() + " " + a.getApellido();
            break;
          }
        }
    %>
      <tr>
        <td><%= titulo      %></td>
        <td><%= nombreAutor %></td>
      </tr>
    <%
      }
      if (listaLA.isEmpty()) {
    %>
      <tr>
        <td colspan="2" style="text-align:center;">No hay asignaciones registradas.</td>
      </tr>
    <%
      }
    %>
  </table>

</body>
</html>
