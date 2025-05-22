<?php
require_once '../classes/LibroAutor.php';
header('Content-Type: application/json');

switch ($_SERVER['REQUEST_METHOD']) {
    case 'GET':
        echo json_encode(LibroAutor::getAll());
        break;

    case 'POST':
        $data = json_decode(file_get_contents("php://input"), true);
        LibroAutor::insert($data['id_libro'], $data['id_autor']);
        echo json_encode(["message" => "Relación creada"]);
        break;

    case 'DELETE':
        $data = json_decode(file_get_contents("php://input"), true);
        LibroAutor::delete($data['id_libro'], $data['id_autor']);
        echo json_encode(["message" => "Relación eliminada"]);
        break;
}
