<?php
require_once __DIR__ . '/../config/database.php';

class Libro {
    public static function getAll() {
        $db = Database::connect();
        $stmt = $db->query("SELECT * FROM libros");
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public static function insert($titulo, $anio) {
        $db = Database::connect();
        $stmt = $db->prepare("INSERT INTO libros (titulo, anio) VALUES (?, ?)");
        return $stmt->execute([$titulo, $anio]);
    }

    public static function update($id, $titulo, $anio) {
        $db = Database::connect();
        $stmt = $db->prepare("UPDATE libros SET titulo = ?, anio = ? WHERE id_libro = ?");
        return $stmt->execute([$titulo, $anio, $id]);
    }

    public static function delete($id) {
        $db = Database::connect();
        $stmt = $db->prepare("DELETE FROM libros WHERE id_libro = ?");
        return $stmt->execute([$id]);
    }
}
