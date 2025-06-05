$(document).ready(function () {
  // 1) Al cargar la página, mostrará todos los usuarios
  cargarDatos();

  // 2) Listener para "Insertar Usuario" (igual que antes)
  $("#btn-insertar").on("click", function () {
    $("#usuarioModalLabel").text("Add User");
    $("#form-usuario")[0].reset();
    $("#form-usuario").attr("data-modo", "crear");
    $('input[name="cedula"]').prop("readonly", false);
  });

  // 3) Listener para "Editar Usuario" (igual que antes)
  $(document).on("click", ".edit-btn", function () {
    const row = $(this).closest("tr");
    $("#usuarioModalLabel").text("Editar Usuario");
    $("#form-usuario")[0].reset();

    $('input[name="cedula"]').val(row.data("cedula")).prop("readonly", true);
    $('input[name="nombre"]').val(row.find("td:eq(1)").text());
    $('input[name="apellido"]').val(row.find("td:eq(2)").text());
    $('input[name="direccion"]').val(row.find("td:eq(3)").text());
    $('input[name="telefono"]').val(row.find("td:eq(4)").text());

    $("#form-usuario").attr("data-modo", "editar");
  });

  // 4) Listener para "Guardar" (Submit del formulario) (igual que antes)
  $("#form-usuario").on("submit", function (e) {
    e.preventDefault();
    const modo = $(this).attr("data-modo");
    const formData = {
      cedula: $('input[name="cedula"]').val().trim(),
      nombre: $('input[name="nombre"]').val().trim(),
      apellido: $('input[name="apellido"]').val().trim(),
      direccion: $('input[name="direccion"]').val().trim(),
      telefono: $('input[name="telefono"]').val().trim(),
    };

    // Validación rápida de campos vacíos
    if (
      !formData.cedula ||
      !formData.nombre ||
      !formData.apellido ||
      !formData.direccion ||
      !formData.telefono
    ) {
      alert("Todos los campos son obligatorios.");
      return;
    }

    const cerrarModal = () => {
      const modalElement = document.getElementById("usuarioModal");
      const modal = bootstrap.Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    };

    if (modo === "editar") {
      // ----> PUT para actualizar
      $.ajax({
        url: "http://localhost:8080/Grupo4/UserController",
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(formData),
        success: function () {
          console.log("Usuario actualizado");
          $("#form-usuario")[0].reset();
          cargarDatos();
          cerrarModal();
        },
        error: function (xhr, status, error) {
          console.error("Error al actualizar usuario:", error);
          alert("No se pudo actualizar el usuario.");
        },
      });
    } else {
      // ----> POST para crear
      $.ajax({
        url: "http://localhost:8080/Grupo4/UserController",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(formData),
        success: function () {
          console.log("Usuario agregado");
          $("#form-usuario")[0].reset();
          cargarDatos();
          cerrarModal();
        },
        error: function (xhr, status, error) {
          console.error("Error al agregar usuario:", error);
          alert("No se pudo agregar el usuario.");
        },
      });
    }
  });

  // 5) NUEVO: Listener para "Buscar por cédula"
  $("#btn-buscar").on("click", function (e) {
    e.preventDefault();
    const cedulaBuscada = $("#input-buscar-cedula").val().trim();

    if (!cedulaBuscada) {
      alert("Por favor, ingresa una cédula para buscar.");
      return;
    }

    // Hacemos GET /UserController?cedula=cedulaBuscada
    $.ajax({
      url:
        "http://localhost:8080/Grupo4/UserController?cedula=" +
        encodeURIComponent(cedulaBuscada),
      type: "GET",
      dataType: "json",

      success: function (user) {
        // user es un objeto con las propiedades { cedula, nombre, apellido, direccion, telefono }
        // Si existe, mostramos SOLO esa fila en la tabla:
        const tbody = $("#tabla-bootstrap tbody");
        tbody.empty(); // limpiamos el contenido actual

        // Puede que user sea null (si el backend devolvió 404), pero aquí 'success' solo corre si status = 200.
        // Verificamos que efectivamente traiga propiedades:
        if (user && user.cedula) {
          const row = `
            <tr data-cedula="${user.cedula}">
              <td>${user.cedula}</td>
              <td>${user.nombre}</td>
              <td>${user.apellido}</td>
              <td>${user.direccion}</td>
              <td>${user.telefono}</td>
              <td>
                <button class="btn btn-sm btn-warning edit-btn" data-bs-toggle="modal" data-bs-target="#usuarioModal">
                  <i class="bi bi-pen-fill"></i>
                </button>
                <button class="btn btn-sm btn-danger" onclick="deleteUser('${user.cedula}')">
                  <i class="bi bi-trash-fill"></i>
                </button>
              </td>
            </tr>
          `;
          tbody.append(row);
        } else {
          // Si backend devolvió un JSON vacío (o null), aunque esto no debería suceder con status=200
          alert("Usuario no encontrado.");
        }
      },

      error: function (xhr, status, error) {
        console.error("Error al buscar usuario:", error);
        if (xhr.status === 404) {
          alert("No se encontró un usuario con esa cédula.");
        } else {
          alert("Error al buscar usuario.");
        }
      },
    });
  });

  // 6) NUEVO: Listener para "Restablecer" (mostrar todos de nuevo)
  $("#btn-restablecer").on("click", function (e) {
    e.preventDefault();
    $("#input-buscar-cedula").val(""); // limpio el input
    cargarDatos(); // vuelvo a cargar toda la lista
  });
});

// Función para cargar todos los usuarios (igual que antes)
function cargarDatos() {
  $.ajax({
    url: "http://localhost:8080/Grupo4/UserController",
    type: "GET",
    dataType: "json",
    success: function (data) {
      const tbody = $("#tabla-bootstrap tbody");
      tbody.empty();

      data.forEach(function (item) {
        const row = `
          <tr data-cedula="${item.cedula}">
            <td>${item.cedula}</td>
            <td>${item.nombre}</td>
            <td>${item.apellido}</td>
            <td>${item.direccion}</td>
            <td>${item.telefono}</td>
            <td>
              <button class="btn btn-sm btn-warning edit-btn" data-bs-toggle="modal" data-bs-target="#usuarioModal">
                <i class="bi bi-pen-fill"></i>
              </button>
              <button class="btn btn-sm btn-danger" onclick="deleteUser('${item.cedula}')">
                <i class="bi bi-trash-fill"></i>
              </button>
            </td>
          </tr>
        `;
        tbody.append(row);
      });
    },
    error: function (xhr, status, error) {
      console.error("Error al cargar datos:", error);
      alert("No se pudieron cargar los usuarios.");
    },
  });
}

// Función para eliminar usuario (igual que la vimos antes)
function deleteUser(cedula) {
  if (!confirm("¿Seguro que quieres eliminar al usuario con cédula " + cedula + "?")) {
    return;
  }
  $.ajax({
    url: "http://localhost:8080/Grupo4/UserController",
    type: "DELETE",
    contentType: "application/json",
    data: JSON.stringify({ cedula: cedula }),
    success: function () {
      console.log("Usuario eliminado correctamente");
      cargarDatos();
    },
    error: function (xhr, status, error) {
      console.error("Error al eliminar usuario:", error);
      if (xhr.status === 404) {
        alert("No se encontró el usuario para eliminar.");
      } else {
        alert("Error al eliminar usuario.");
      }
    },
  });
}

