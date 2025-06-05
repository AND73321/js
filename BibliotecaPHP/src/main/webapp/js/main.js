// js/main.js

$(document).ready(function () {
  // BASE de la API (AJUSTA la ruta a tu carpeta PHP)
  // Ejemplo: si tu PHP está en http://localhost/biblioteca-api/api, déjalo así:
  const API_BASE = 'http://localhost/biblioteca-api/api';

  const API_AUTORES = `${API_BASE}/autores.php`;
  const API_LIBROS  = `${API_BASE}/libros.php`;
  const API_REL     = `${API_BASE}/libro_autor.php`;

  let autores    = [];
  let libros     = [];
  let relaciones = [];

  /** =====================================================
   *  FUNCIONES DE CARGA (devuelven promesas cuando corresponde)
   *  - loadAutores
   *  - loadLibros
   *  - loadRelaciones
   *  ===================================================== */
  function loadAutores() {
    return $.ajax({
      url: API_AUTORES,
      method: 'GET',
      dataType: 'json'
    })
    .done(function(data) {
      autores = data;
      renderAutoresTable();
      populateAutoresSelect();
    })
    .fail(function(xhr) {
      alert('Error al cargar autores: ' + xhr.responseText);
    });
  }

  function loadLibros() {
    return $.ajax({
      url: API_LIBROS,
      method: 'GET',
      dataType: 'json'
    })
    .done(function(data) {
      libros = data;
      renderLibrosTable();
      populateLibrosSelect();
    })
    .fail(function(xhr) {
      alert('Error al cargar libros: ' + xhr.responseText);
    });
  }

  function loadRelaciones() {
    $.ajax({
      url: API_REL,
      method: 'GET',
      dataType: 'json'
    })
    .done(function(data) {
      relaciones = data;
      renderRelacionesTable();  // Ya existen "libros" y "autores"
    })
    .fail(function(xhr) {
      alert('Error al cargar relaciones: ' + xhr.responseText);
    });
  }

  // Al inicio: primero cargar autores y libros en paralelo,
  // y solo cuando ambas promesas se resuelvan, llamar a loadRelaciones().
  $.when(loadAutores(), loadLibros()).done(function() {
    loadRelaciones();
  });

  
  /** =====================================================
   *  FUNCIONES DE POBLADO DE SELECTS
   *  - populateAutoresSelect
   *  - populateLibrosSelect
   *  ===================================================== */
  function populateAutoresSelect() {
    const sel = $('#selectAutor');
    sel.empty();
    sel.append(`<option value="">-- Seleccione Autor --</option>`);
    autores.forEach(a => {
      sel.append(`<option value="${a.id_autor}">${a.nombre} ${a.apellido}</option>`);
    });
  }

  function populateLibrosSelect() {
    const sel = $('#selectLibro');
    sel.empty();
    sel.append(`<option value="">-- Seleccione Libro --</option>`);
    libros.forEach(l => {
      sel.append(`<option value="${l.id_libro}">${l.titulo}</option>`);
    });
  }


  /** =====================================================
   *  RENDERIZAR TABLAS
   *  - renderAutoresTable
   *  - renderLibrosTable
   *  - renderRelacionesTable
   *  ===================================================== */
  function renderAutoresTable() {
    const tbody = $('#autoresTable tbody');
    tbody.empty();

    autores.forEach(a => {
      const tr = $(`
        <tr>
          <td>${a.id_autor}</td>
          <td>${a.nombre}</td>
          <td>${a.apellido}</td>
          <td>
            <button class="btn btn-sm btn-warning edit-autor" data-id="${a.id_autor}">Editar️</button>
            <button class="btn btn-sm btn-danger delete-autor" data-id="${a.id_autor}">Eliminar️</button>
          </td>
        </tr>
      `);
      tbody.append(tr);
    });
  }

  function renderLibrosTable() {
    const tbody = $('#librosTable tbody');
    tbody.empty();

    libros.forEach(l => {
      const tr = $(`
        <tr>
          <td>${l.id_libro}</td>
          <td>${l.titulo}</td>
          <td>
            <button class="btn btn-sm btn-warning edit-libro" data-id="${l.id_libro}">Editar️️</button>
            <button class="btn btn-sm btn-danger delete-libro" data-id="${l.id_libro}">Eliminar️️</button>
          </td>
        </tr>
      `);
      tbody.append(tr);
    });
  }

  function renderRelacionesTable() {
    const tbody = $('#relTable tbody');
    tbody.empty();

    relaciones.forEach(r => {
      // Ahora "libros" y "autores" ya están poblados
      const libro = libros.find(l => l.id_libro == r.id_libro) || {};
      const autor = autores.find(a => a.id_autor == r.id_autor) || {};

      const tr = $(`
        <tr>
          <td>${r.id_libro}</td>
          <td>${libro.titulo || '(desconocido)'}</td>
          <td>${r.id_autor}</td>
          <td>${autor.nombre || ''} ${autor.apellido || ''}</td>
          <td>
            <button class="btn btn-sm btn-danger delete-rel"
                    data-lib="${r.id_libro}"
                    data-autor="${r.id_autor}">
              Eliminar️
            </button>
          </td>
        </tr>
      `);
      tbody.append(tr);
    });
  }


  /** =====================================================
   *  EVENTOS PARA AUTORES
   *  - Nuevo Autor
   *  - Guardar (crear o editar)
   *  - Editar
   *  - Eliminar
   *  ===================================================== */
  $('#btnNewAutor').click(function () {
    $('#autorForm')[0].reset();
    $('#autorId').val('');
    $('#autorModalLabel').text('Nuevo Autor');
    $('#autorModal').modal('show');
  });

  $('#autorForm').submit(function (e) {
    e.preventDefault();
    const idAutor = $('#autorId').val().trim();
    const payload = {
      nombre: $('#autorNombre').val().trim(),
      apellido: $('#autorApellido').val().trim()
    };

    if (idAutor === '') {
      // CREAR
      $.ajax({
        url: API_AUTORES,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function () {
          $('#autorModal').modal('hide');
          // recargar autores (y luego relaciones)
          loadAutores().done(function() {
            loadRelaciones();
          });
        },
        error: function (xhr) {
          alert('No se pudo crear autor: ' + xhr.responseText);
        }
      });
    } else {
      // EDITAR
      payload.id_autor = idAutor;
      $.ajax({
        url: API_AUTORES,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function () {
          $('#autorModal').modal('hide');
          loadAutores().done(function() {
            loadRelaciones();
          });
        },
        error: function (xhr) {
          alert('No se pudo actualizar autor: ' + xhr.responseText);
        }
      });
    }
  });

  // Editar autor
  $('#autoresTable').on('click', '.edit-autor', function () {
    const id = $(this).data('id');
    const autor = autores.find(a => a.id_autor == id);
    if (!autor) return;

    $('#autorId').val(autor.id_autor);
    $('#autorNombre').val(autor.nombre);
    $('#autorApellido').val(autor.apellido);
    $('#autorModalLabel').text('Editar Autor');
    $('#autorModal').modal('show');
  });

  // Eliminar autor
  $('#autoresTable').on('click', '.delete-autor', function () {
    const id = $(this).data('id');
    if (!confirm('¿Eliminar autor con ID ' + id + '?')) return;

    $.ajax({
      url: API_AUTORES,
      method: 'DELETE',
      contentType: 'application/json',
      data: JSON.stringify({ id_autor: id }),
      success: function () {
        loadAutores().done(function() {
          loadRelaciones();
        });
      },
      error: function (xhr) {
        alert('No se pudo eliminar autor: ' + xhr.responseText);
      }
    });
  });


  /** =====================================================
   *  EVENTOS PARA LIBROS
   *  - Nuevo Libro
   *  - Guardar (crear o editar)
   *  - Editar
   *  - Eliminar
   *  ===================================================== */
  $('#btnNewLibro').click(function () {
    $('#libroForm')[0].reset();
    $('#libroId').val('');
    $('#libroModalLabel').text('Nuevo Libro');
    $('#libroModal').modal('show');
  });

  $('#libroForm').submit(function (e) {
    e.preventDefault();
    const idLibro = $('#libroId').val().trim();
    const payload = {
      titulo: $('#libroTitulo').val().trim()
    };

    if (idLibro === '') {
      // CREAR
      $.ajax({
        url: API_LIBROS,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function () {
          $('#libroModal').modal('hide');
          loadLibros().done(function() {
            loadRelaciones();
          });
        },
        error: function (xhr) {
          alert('No se pudo crear libro: ' + xhr.responseText);
        }
      });
    } else {
      // EDITAR
      payload.id_libro = idLibro;
      $.ajax({
        url: API_LIBROS,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function () {
          $('#libroModal').modal('hide');
          loadLibros().done(function() {
            loadRelaciones();
          });
        },
        error: function (xhr) {
          alert('No se pudo actualizar libro: ' + xhr.responseText);
        }
      });
    }
  });

  // Editar libro
  $('#librosTable').on('click', '.edit-libro', function () {
    const id = $(this).data('id');
    const libro = libros.find(l => l.id_libro == id);
    if (!libro) return;

    $('#libroId').val(libro.id_libro);
    $('#libroTitulo').val(libro.titulo);
    $('#libroModalLabel').text('Editar Libro');
    $('#libroModal').modal('show');
  });

  // Eliminar libro
  $('#librosTable').on('click', '.delete-libro', function () {
    const id = $(this).data('id');
    if (!confirm('¿Eliminar libro con ID ' + id + '?')) return;

    $.ajax({
      url: API_LIBROS,
      method: 'DELETE',
      contentType: 'application/json',
      data: JSON.stringify({ id_libro: id }),
      success: function () {
        loadLibros().done(function() {
          loadRelaciones();
        });
      },
      error: function (xhr) {
        alert('No se pudo eliminar libro: ' + xhr.responseText);
      }
    });
  });


  /** =====================================================
   *  EVENTOS PARA RELACIONES
   *  - Crear relación
   *  - Eliminar relación
   *  ===================================================== */
  $('#relForm').submit(function (e) {
    e.preventDefault();
    const idLibro = $('#selectLibro').val();
    const idAutor = $('#selectAutor').val();
    if (!idLibro || !idAutor) {
      alert('Seleccione libro y autor.');
      return;
    }

    const payload = {
      id_libro: parseInt(idLibro),
      id_autor: parseInt(idAutor)
    };

    $.ajax({
      url: API_REL,
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(payload),
      success: function () {
        loadRelaciones();
      },
      error: function (xhr) {
        alert('No se pudo crear relación: ' + xhr.responseText);
      }
    });
  });

  // Eliminar relación
  $('#relTable').on('click', '.delete-rel', function () {
    const idLibro = $(this).data('lib');
    const idAutor = $(this).data('autor');
    if (!confirm(`¿Eliminar relación Libro=${idLibro} Autor=${idAutor}?`)) return;

    $.ajax({
      url: API_REL,
      method: 'DELETE',
      contentType: 'application/json',
      data: JSON.stringify({
        id_libro: parseInt(idLibro),
        id_autor: parseInt(idAutor)
      }),
      success: function () {
        loadRelaciones();
      },
      error: function (xhr) {
        alert('No se pudo eliminar relación: ' + xhr.responseText);
      }
    });
  });

}); // fin de $(document).ready

