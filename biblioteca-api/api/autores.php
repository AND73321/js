<?php
require_once '../classes/Autor.php';
header('Content-Type: application/json');

switch ($_SERVER['REQUEST_METHOD']) {
    case 'GET':
        echo json_encode(Autor::getAll());
        break;

    case 'POST':
        $data = json_decode(file_get_contents("php://input"), true);
        Autor::insert($data['nombre'], $data['apellido'], $data['nacionalidad']);
        echo json_encode(["message" => "Autor creado"]);
        break;

    case 'PUT':
        $data = json_decode(file_get_contents("php://input"), true);
        Autor::update($data['id_autor'], $data['nombre'], $data['apellido'], $data['nacionalidad']);
        echo json_encode(["message" => "Autor actualizado"]);
        break;

    case 'DELETE':
        $data = json_decode(file_get_contents("php://input"), true);
        Autor::delete($data['id_autor']);
        echo json_encode(["message" => "Autor eliminado"]);
        break;
}
