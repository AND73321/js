<?php
require_once __DIR__ . '/../config/database.php';

class LibroAutor {
    public static function getAll() {
        $db = Database::connect();
        $stmt = $db->query("SELECT * FROM libro_autor");
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public static function insert($id_libro, $id_autor) {
        $db = Database::connect();
        $stmt = $db->prepare("INSERT INTO libro_autor (id_libro, id_autor) VALUES (?, ?)");
        return $stmt->execute([$id_libro, $id_autor]);
    }

    public static function delete($id_libro, $id_autor) {
        $db = Database::connect();
        $stmt = $db->prepare("DELETE FROM libro_autor WHERE id_libro = ? AND id_autor = ?");
        return $stmt->execute([$id_libro, $id_autor]);
    }
}
