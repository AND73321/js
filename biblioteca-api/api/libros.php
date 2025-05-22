<?php
require_once '../classes/Libro.php';
header('Content-Type: application/json');

switch ($_SERVER['REQUEST_METHOD']) {
    case 'GET':
        echo json_encode(Libro::getAll());
        break;

    case 'POST':
        $data = json_decode(file_get_contents("php://input"), true);
        Libro::insert($data['titulo'], $data['anio']);
        echo json_encode(["message" => "Libro creado"]);
        break;

    case 'PUT':
        $data = json_decode(file_get_contents("php://input"), true);
        Libro::update($data['id_libro'], $data['titulo'], $data['anio']);
        echo json_encode(["message" => "Libro actualizado"]);
        break;

    case 'DELETE':
        $data = json_decode(file_get_contents("php://input"), true);
        Libro::delete($data['id_libro']);
        echo json_encode(["message" => "Libro eliminado"]);
        break;
}
