<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión Biblioteca (Autores / Libros / Relaciones)</title>

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <!-- Tu archivo main.js con la lógica AJAX corregido -->
    <script src="js/main2.js"></script>
</head>
<body>

  <h1>Gestión Biblioteca</h1>

  <!-- ============================ -->
  <!-- 1. Sección AUTORES          -->
  <!-- ============================ -->
  <section id="seccion-autores">
    <h2>Autores</h2>
    <button id="btnNewAutor">Nuevo Autor</button>

    <table border="1" cellpadding="5" cellspacing="0" id="autoresTable">
      <thead>
        <tr>
          <th>ID Autor</th>
          <th>Nombre</th>
          <th>Apellido</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <!-- Se rellena vía JavaScript -->
      </tbody>
    </table>

    <!-- Formulario simple para crear/editar autor -->
    <div id="formAutor" style="display:none; margin-top: 1em;">
      <h3 id="tituloFormAutor">Nuevo Autor</h3>
      <form id="autorForm">
        <input type="hidden" id="autorId" name="id_autor">
        <label>
          Nombre:
          <input type="text" id="autorNombre" name="nombre" required>
        </label>
        <br>
        <label>
          Apellido:
          <input type="text" id="autorApellido" name="apellido" required>
        </label>
        <br>
        <button type="submit">Guardar</button>
        <button type="button" id="btnCancelarAutor">Cancelar</button>
      </form>
    </div>
  </section>

  <hr>

  <!-- ============================ -->
  <!-- 2. Sección LIBROS           -->
  <!-- ============================ -->
  <section id="seccion-libros">
    <h2>Libros</h2>
    <button id="btnNewLibro">Nuevo Libro</button>

    <table border="1" cellpadding="5" cellspacing="0" id="librosTable">
      <thead>
        <tr>
          <th>ID Libro</th>
          <th>Título</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <!-- Se rellena vía JavaScript -->
      </tbody>
    </table>

    <!-- Formulario simple para crear/editar libro -->
    <div id="formLibro" style="display:none; margin-top: 1em;">
      <h3 id="tituloFormLibro">Nuevo Libro</h3>
      <form id="libroForm">
        <input type="hidden" id="libroId" name="id_libro">
        <label>
          Título:
          <input type="text" id="libroTitulo" name="titulo" required>
        </label>
        <br>
        <button type="submit">Guardar</button>
        <button type="button" id="btnCancelarLibro">Cancelar</button>
      </form>
    </div>
  </section>

  <hr>

  <!-- ============================ -->
  <!-- 3. Sección RELACIONES        -->
  <!-- ============================ -->
  <section id="seccion-relaciones">
    <h2>Relación Libro ↔ Autor</h2>

    <!-- Formulario para crear relación -->
    <div id="formRelacion" style="margin-bottom: 1em;">
      <label>
        Libro:
        <select id="selectLibro" required>
          <option value="">-- Seleccione Libro --</option>
        </select>
      </label>
      &nbsp;&nbsp;
      <label>
        Autor:
        <select id="selectAutor" required>
          <option value="">-- Seleccione Autor --</option>
        </select>
      </label>
      &nbsp;&nbsp;
      <button id="btnCrearRelacion">Crear</button>
    </div>

    <table border="1" cellpadding="5" cellspacing="0" id="relTable">
      <thead>
        <tr>
          <th>ID Libro</th>
          <th>Título</th>
          <th>ID Autor</th>
          <th>Nombre Autor</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <!-- Se rellena vía JavaScript -->
      </tbody>
    </table>
  </section>

</body>
</html>
