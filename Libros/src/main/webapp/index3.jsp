<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, java.util.Map, java.util.HashMap" %>
<%@ page import="dao.AutorDao, dao.LibroDao, dao.LibroAutorDao" %>
<%@ page import="models.Autor, models.Libro, models.LibroAutor" %>

<%
  /////////////////////////////////////////////////////////
  // 0) LEER PARÁMETROS DE BÚSQUEDA
  /////////////////////////////////////////////////////////
  String buscarAutor       = request.getParameter("buscarAutor");
  String buscarLibro       = request.getParameter("buscarLibro");
  String buscarAsignLibro  = request.getParameter("buscarAsignLibro");
  String buscarAsignAutor  = request.getParameter("buscarAsignAutor");

  /////////////////////////////////////////////////////////
  // 1) CARGAR LISTAS DESDE LOS DAOs
  /////////////////////////////////////////////////////////
  AutorDao       ad  = new AutorDao();
  LibroDao       ld  = new LibroDao();
  LibroAutorDao  lad = new LibroAutorDao();

  List<Autor>       listaAutoresOrig      = ad.getAllAutores();
  List<Libro>       listaLibrosOrig      = ld.getAllLibros();
  List<LibroAutor>  listaLAOrig          = lad.getAllAsignaciones();

  /////////////////////////////////////////////////////////
  // 2) FILTRAR LISTA DE AUTORES (SECCIÓN AUTORES)
  /////////////////////////////////////////////////////////
  List<Autor> listaAutores = new ArrayList<>();
  if (buscarAutor != null && !buscarAutor.trim().isEmpty()) {
    String termA = buscarAutor.trim().toLowerCase();
    for (Autor a : listaAutoresOrig) {
      // Si el término es numérico, filtrar por ID exacto
      if (termA.matches("\\d+")) {
        if (a.getIdAutor() == Integer.parseInt(termA)) {
          listaAutores.add(a);
        }
      }
      // Sino, filtrar si nombre o apellido contienen el término
      else {
        String nombreLow   = a.getNombre().toLowerCase();
        String apellidoLow = a.getApellido().toLowerCase();
        if (nombreLow.contains(termA) || apellidoLow.contains(termA)) {
          listaAutores.add(a);
        }
      }
    }
  } else {
    // Si no hay término de búsqueda, usamos la lista completa
    listaAutores.addAll(listaAutoresOrig);
  }

  /////////////////////////////////////////////////////////
  // 3) FILTRAR LISTA DE LIBROS (SECCIÓN LIBROS)
  /////////////////////////////////////////////////////////
  List<Libro> listaLibros = new ArrayList<>();
  if (buscarLibro != null && !buscarLibro.trim().isEmpty()) {
    String termL = buscarLibro.trim().toLowerCase();
    for (Libro l : listaLibrosOrig) {
      if (termL.matches("\\d+")) {
        if (l.getIdLibro() == Integer.parseInt(termL)) {
          listaLibros.add(l);
        }
      }
      else {
        String tituloLow = l.getTitulo().toLowerCase();
        if (tituloLow.contains(termL)) {
          listaLibros.add(l);
        }
      }
    }
  } else {
    listaLibros.addAll(listaLibrosOrig);
  }

  /////////////////////////////////////////////////////////
  // 4) FILTRAR LISTA DE ASIGNACIONES (SECCIÓN ASIGNAR)
  /////////////////////////////////////////////////////////
  // Para filtrar, necesitamos los datos “join”: título del libro + nombre completo del autor.
  List<LibroAutor> listaLA = new ArrayList<>();
  for (LibroAutor la : listaLAOrig) {
    // Obtener título
    String titulo = "";
    for (Libro l : listaLibrosOrig) {
      if (l.getIdLibro() == la.getIdLibro()) {
        titulo = l.getTitulo();
        break;
      }
    }
    // Obtener nombre completo de autor
    String nombreComp = "";
    for (Autor a : listaAutoresOrig) {
      if (a.getIdAutor() == la.getIdAutor()) {
        nombreComp = a.getNombre() + " " + a.getApellido();
        break;
      }
    }

    boolean okLibro  = true;
    boolean okAutor  = true;

    // Si se pide filtrar por libro:
    if (buscarAsignLibro != null && !buscarAsignLibro.trim().isEmpty()) {
      String t = buscarAsignLibro.trim().toLowerCase();
      // Coincidencia por ID o por título parcial
      if (t.matches("\\d+")) {
        okLibro = (la.getIdLibro() == Integer.parseInt(t));
      } else {
        okLibro = titulo.toLowerCase().contains(t);
      }
    }

    // Si se pide filtrar por autor:
    if (buscarAsignAutor != null && !buscarAsignAutor.trim().isEmpty()) {
      String t = buscarAsignAutor.trim().toLowerCase();
      // Coincidencia por ID o por nombre completo parcial
      if (t.matches("\\d+")) {
        okAutor = (la.getIdAutor() == Integer.parseInt(t));
      } else {
        okAutor = nombreComp.toLowerCase().contains(t);
      }
    }

    // Solo añadimos si ambos filtros (si existen) se cumplen
    if (okLibro && okAutor) {
      listaLA.add(la);
    }
  }

  /////////////////////////////////////////////////////////
  // 5) PREPARAR DATOS PARA LA TABLA “LIBROS | AUTORES”
  /////////////////////////////////////////////////////////
  // Construimos un mapa id_libro → lista de nombres completos de autores
  Map<Integer, List<String>> mapaLibroAutores = new HashMap<>();
  for (LibroAutor la : listaLAOrig) {
    int idL = la.getIdLibro();
    int idA = la.getIdAutor();
    // Encontrar nombre completo del autor en lista original
    String nombreComp = "";
    for (Autor a : listaAutoresOrig) {
      if (a.getIdAutor() == idA) {
        nombreComp = a.getNombre() + " " + a.getApellido();
        break;
      }
    }
    if (!mapaLibroAutores.containsKey(idL)) {
      mapaLibroAutores.put(idL, new ArrayList<String>());
    }
    mapaLibroAutores.get(idL).add(nombreComp);
  }
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <title>Sistema Biblioteca (versión simple)</title>
  <style>
    table { border-collapse: collapse; width: 80%; margin-bottom: 1em; }
    th, td { border: 1px solid #666; padding: 6px; }
    th { background: #eee; }
    .buscador { margin: 0.5em 0; }
  </style>
</head>
<body>

  <h1>Sistema Biblioteca</h1>

  <!-- Mensajes de sesión (igual que antes) -->
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

  <!-- ================================================== -->
  <!-- 1) SECCIÓN AUTORES CON BUSCADOR                  -->
  <!-- ================================================== -->
  <h2>Autores</h2>
  <!-- Formulario para crear nuevo autor -->
  <form action="crearAutor" method="post">
    <label>Nombre:</label>
    <input type="text" name="nombre" required />
    <label>Apellido:</label>
    <input type="text" name="apellido" required />
    <button type="submit">Crear Autor</button>
  </form>

  <!-- Buscador: ID / nombre / apellido -->
  <div class="buscador">
    <form action="index3.jsp" method="get">
      <!-- Preservar otros parámetros de búsqueda -->
      <input type="hidden" name="buscarLibro" value="<%= (buscarLibro!=null?buscarLibro:"") %>"/>
      <input type="hidden" name="buscarAsignLibro" value="<%= (buscarAsignLibro!=null?buscarAsignLibro:"") %>"/>
      <input type="hidden" name="buscarAsignAutor" value="<%= (buscarAsignAutor!=null?buscarAsignAutor:"") %>"/>

      <label>Buscar Autor (ID / Nombre / Apellido):</label>
      <input type="text" name="buscarAutor" value="<%= (buscarAutor!=null?buscarAutor:"") %>" placeholder="Ej. 3 ó Pérez"/>
      <button type="submit">Buscar</button>
      <button type="button" onclick="window.location='index3.jsp'">Limpiar</button>
    </form>
  </div>

  <!-- Tabla de autores filtrada -->
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Apellido</th>
      </tr>
    </thead>
    <tbody>
      <%
        if (listaAutores.isEmpty()) {
      %>
        <tr>
          <td colspan="3" style="text-align:center;">No se encontraron autores.</td>
        </tr>
      <%
        } else {
          for (Autor a : listaAutores) {
      %>
        <tr>
          <td><%= a.getIdAutor() %></td>
          <td><%= a.getNombre()   %></td>
          <td><%= a.getApellido() %></td>
        </tr>
      <%
          }
        }
      %>
    </tbody>
  </table>

  <hr />

  <!-- ================================================== -->
  <!-- 2) SECCIÓN LIBROS CON BUSCADOR                   -->
  <!-- ================================================== -->
  <h2>Libros</h2>
  <!-- Formulario para crear nuevo libro -->
  <form action="crearLibro" method="post">
    <label>Título del libro:</label>
    <input type="text" name="titulo" required />
    <button type="submit">Crear Libro</button>
  </form>

  <!-- Buscador: ID / título -->
  <div class="buscador">
    <form action="index3.jsp" method="get">
      <!-- Preservar otros parámetros de búsqueda -->
      <input type="hidden" name="buscarAutor" value="<%= (buscarAutor!=null?buscarAutor:"") %>"/>
      <input type="hidden" name="buscarAsignLibro" value="<%= (buscarAsignLibro!=null?buscarAsignLibro:"") %>"/>
      <input type="hidden" name="buscarAsignAutor" value="<%= (buscarAsignAutor!=null?buscarAsignAutor:"") %>"/>

      <label>Buscar Libro (ID / Título):</label>
      <input type="text" name="buscarLibro" value="<%= (buscarLibro!=null?buscarLibro:"") %>" placeholder="Ej. 5 ó 'Patrones'" />
      <button type="submit">Buscar</button>
      <button type="button" onclick="window.location='index3.jsp'">Limpiar</button>
    </form>
  </div>

  <!-- Tabla de libros filtrada -->
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Título</th>
      </tr>
    </thead>
    <tbody>
      <%
        if (listaLibros.isEmpty()) {
      %>
        <tr>
          <td colspan="2" style="text-align:center;">No se encontraron libros.</td>
        </tr>
      <%
        } else {
          for (Libro l : listaLibros) {
      %>
        <tr>
          <td><%= l.getIdLibro() %></td>
          <td><%= l.getTitulo()  %></td>
        </tr>
      <%
          }
        }
      %>
    </tbody>
  </table>

  <hr />

  <!-- ================================================== -->
  <!-- 3) SECCIÓN ASIGNAR AUTOR A LIBRO CON BUSCADORES   -->
  <!-- ================================================== -->
  <h2>Asignar Autor a Libro</h2>
  <form action="asignarAutor" method="post">
    <label>Libro:</label>
    <select name="id_libro" required>
      <option value="">-- Seleccione Libro --</option>
      <%
        for (Libro l : listaLibrosOrig) {
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
        for (Autor a : listaAutoresOrig) {
      %>
        <option value="<%= a.getIdAutor() %>"><%= a.getNombre() %> <%= a.getApellido() %></option>
      <%
        }
      %>
    </select>

    <button type="submit">Asignar</button>
  </form>

  <!-- Buscador para la tabla de asignaciones -->
  <div class="buscador">
    <form action="index3.jsp" method="get">
      <!-- Preservar otros parámetros de búsqueda -->
      <input type="hidden" name="buscarAutor" value="<%= (buscarAutor!=null?buscarAutor:"") %>"/>
      <input type="hidden" name="buscarLibro" value="<%= (buscarLibro!=null?buscarLibro:"") %>"/>

      <label>Filtrar Asignaciones — Libro (ID/Título):</label>
      <input type="text" name="buscarAsignLibro" value="<%= (buscarAsignLibro!=null?buscarAsignLibro:"") %>" placeholder="Ej. 2 ó 'Patrones'" />
      &nbsp;&nbsp;
      <label>Autor (ID/Nombre):</label>
      <input type="text" name="buscarAsignAutor" value="<%= (buscarAsignAutor!=null?buscarAsignAutor:"") %>" placeholder="Ej. 4 ó 'Andrade'" />
      <button type="submit">Filtrar</button>
      <button type="button" onclick="window.location='index3.jsp'">Limpiar</button>
    </form>
  </div>

  <!-- Tabla de asignaciones filtrada -->
  <table>
    <thead>
      <tr>
        <th>Libro</th>
        <th>Autor</th>
      </tr>
    </thead>
    <tbody>
      <%
        if (listaLA.isEmpty()) {
      %>
        <tr>
          <td colspan="2" style="text-align:center;">No hay asignaciones registradas.</td>
        </tr>
      <%
        } else {
          for (LibroAutor la : listaLA) {
            // Extraer título y nombre completo (podríamos haberlos calculado arriba, pero lo hacemos de nuevo aquí)
            String titulo     = "-";
            String nombreComp = "-";
            for (Libro l : listaLibrosOrig) {
              if (l.getIdLibro() == la.getIdLibro()) {
                titulo = l.getTitulo();
                break;
              }
            }
            for (Autor a : listaAutoresOrig) {
              if (a.getIdAutor() == la.getIdAutor()) {
                nombreComp = a.getNombre() + " " + a.getApellido();
                break;
              }
            }
      %>
        <tr>
          <td><%= titulo %></td>
          <td><%= nombreComp %></td>
        </tr>
      <%
          }
        }
      %>
    </tbody>
  </table>

  <hr />

  <!-- ================================================== -->
  <!-- 4) TABLA “LIBROS  |  AUTORES”                     -->
  <!-- ================================================== -->
  <h2>Libros y sus Autores</h2>
  <table>
    <thead>
      <tr>
        <th>Libro</th>
        <th>Autores</th>
      </tr>
    </thead>
    <tbody>
      <%
        if (listaLibrosOrig.isEmpty()) {
      %>
        <tr>
          <td colspan="2" style="text-align:center;">No se encontraron libros.</td>
        </tr>
      <%
        } else {
          for (Libro l : listaLibrosOrig) {
            List<String> autoresDelLibro = mapaLibroAutores.get(l.getIdLibro());
            String concatenado;
            if (autoresDelLibro == null || autoresDelLibro.isEmpty()) {
              concatenado = "— sin autores registrados —";
            } else {
              // Unir todos los nombres con comas
              concatenado = String.join(", ", autoresDelLibro);
            }
      %>
        <tr>
          <td><%= l.getTitulo() %></td>
          <td><%= concatenado %></td>
        </tr>
      <%
          }
        }
      %>
    </tbody>
  </table>

</body>
</html>
