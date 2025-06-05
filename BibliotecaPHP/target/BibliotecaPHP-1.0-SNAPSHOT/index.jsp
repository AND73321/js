<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Gestión Biblioteca (Autores / Libros / Relaciones)</title>

    <!-- Bootstrap 5 -->
    <link 
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
      rel="stylesheet"
    />

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <!-- Bootstrap JS Bundle (incluye Popper) -->
    <script 
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js">
    </script>

    <!-- Nuestro JavaScript -->
    <script src="js/main.js"></script>
</head>
<body class="p-4">

  <div class="container">
    <h1 class="text-center mb-5">Gestión Biblioteca</h1>

    <!-- ============================ -->
    <!-- 1. Sección AUTORES         -->
    <!-- ============================ -->
    <div class="card mb-5">
      <div class="card-header bg-primary text-white">
        <h3 class="h5">Autores</h3>
      </div>
      <div class="card-body">
        <button id="btnNewAutor" class="btn btn-success mb-3">
          <i class="bi bi-plus-circle"></i> Nuevo Autor
        </button>

        <table class="table table-bordered table-hover" id="autoresTable">
          <thead class="table-light">
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
      </div>
    </div>

    <!-- Modal para Crear/Editar Autor -->
    <div class="modal fade" id="autorModal" tabindex="-1" aria-labelledby="autorModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <form id="autorForm">
            <div class="modal-header">
              <h5 class="modal-title" id="autorModalLabel">Nuevo Autor</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <div class="modal-body">
              <!-- Campo oculto para almacenar id_autor al editar -->
              <input type="hidden" id="autorId" name="id_autor" />

              <div class="mb-3">
                <label for="autorNombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="autorNombre" name="nombre" required />
              </div>
              <div class="mb-3">
                <label for="autorApellido" class="form-label">Apellido</label>
                <input type="text" class="form-control" id="autorApellido" name="apellido" required />
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
              <button type="submit" class="btn btn-primary" id="saveAutorBtn">Guardar</button>
            </div>
          </form>
        </div>
      </div>
    </div>


    <!-- ============================ -->
    <!-- 2. Sección LIBROS          -->
    <!-- ============================ -->
    <div class="card mb-5">
      <div class="card-header bg-success text-white">
        <h3 class="h5">Libros</h3>
      </div>
      <div class="card-body">
        <button id="btnNewLibro" class="btn btn-primary mb-3">
          <i class="bi bi-plus-circle"></i> Nuevo Libro
        </button>

        <table class="table table-bordered table-hover" id="librosTable">
          <thead class="table-light">
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
      </div>
    </div>

    <!-- Modal para Crear/Editar Libro -->
    <div class="modal fade" id="libroModal" tabindex="-1" aria-labelledby="libroModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <form id="libroForm">
            <div class="modal-header">
              <h5 class="modal-title" id="libroModalLabel">Nuevo Libro</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <div class="modal-body">
              <!-- Campo oculto para editar -->
              <input type="hidden" id="libroId" name="id_libro" />

              <div class="mb-3">
                <label for="libroTitulo" class="form-label">Título</label>
                <input type="text" class="form-control" id="libroTitulo" name="titulo" required />
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
              <button type="submit" class="btn btn-success" id="saveLibroBtn">Guardar</button>
            </div>
          </form>
        </div>
      </div>
    </div>


    <!-- ============================ -->
    <!-- 3. Sección RELACIONES       -->
    <!-- ============================ -->
    <div class="card mb-5">
      <div class="card-header bg-info text-white">
        <h3 class="h5">Relación Libro ? Autor</h3>
      </div>
      <div class="card-body">
        <form id="relForm" class="row g-2 align-items-end mb-3">
          <div class="col-md-5">
            <label for="selectLibro" class="form-label">Libro</label>
            <select id="selectLibro" class="form-select" required>
              <!-- Llenado dinámico -->
            </select>
          </div>
          <div class="col-md-5">
            <label for="selectAutor" class="form-label">Autor</label>
            <select id="selectAutor" class="form-select" required>
              <!-- Llenado dinámico -->
            </select>
          </div>
          <div class="col-md-2">
            <button type="submit" class="btn btn-warning w-100">
              <i class="bi bi-link-45deg"></i> Crear
            </button>
          </div>
        </form>

        <table class="table table-bordered table-hover" id="relTable">
          <thead class="table-light">
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
      </div>
    </div>
  </div>

  <!-- Iconos Bootstrap (opcional) -->
  <link 
    rel="stylesheet" 
    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" 
  />
</body>
</html>
