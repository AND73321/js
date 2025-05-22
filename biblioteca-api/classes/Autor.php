<?php
require_once __DIR__ . '/../config/database.php';

class Autor {
    public static function getAll() {
        $db = Database::connect();
        $stmt = $db->query("SELECT * FROM autores");
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public static function insert($nombre, $apellido, $nacionalidad) {
        $db = Database::connect();
        $stmt = $db->prepare("INSERT INTO autores (nombre, apellido, nacionalidad) VALUES (?, ?, ?)");
        return $stmt->execute([$nombre, $apellido, $nacionalidad]);
    }

    public static function update($id, $nombre, $apellido, $nacionalidad) {
        $db = Database::connect();
        $stmt = $db->prepare("UPDATE autores SET nombre = ?, apellido = ?, nacionalidad = ? WHERE id_autor = ?");
        return $stmt->execute([$nombre, $apellido, $nacionalidad, $id]);
    }

    public static function delete($id) {
        $db = Database::connect();
        $stmt = $db->prepare("DELETE FROM autores WHERE id_autor = ?");
        return $stmt->execute([$id]);
    }
}
