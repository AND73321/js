/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Models.Autor;
import Models.Libro;
import Models.LibroAutor;
import Service.ApiService;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private List<Libro> librosOriginales;
    private List<Autor> autoresOriginales;
    private List<LibroAutor> relacionesOriginales;

    public MainFrame() {
        initComponents();
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }
        });
        cargarTabla();
    }

    /*private void cargarTabla() {
        try {
            List<Libro> libros = ApiService.obtenerLibros();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Título");
            modelo.addColumn("Año");

            for (Libro libro : libros) {
                modelo.addRow(new Object[]{
                    libro.id_libro,
                    libro.titulo,
                    libro.anio
                });
            }

            this.tablaLibros.setModel(modelo);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar libros");
        }
    }*/
 /*private void cargarTabla() {
        try {
            List<Libro> libros = ApiService.obtenerLibros();
            List<Autor> autores = ApiService.obtenerAutores();
            List<LibroAutor> relaciones = ApiService.obtenerRelaciones();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Título");
            modelo.addColumn("Año");
            modelo.addColumn("Autores");

            for (Libro libro : libros) {
                // Buscar autores relacionados a este libro
                StringBuilder autoresStr = new StringBuilder();
                for (LibroAutor rel : relaciones) {
                    if (rel.id_libro == libro.id_libro) {
                        // Buscar autor por id
                        for (Autor autor : autores) {
                            if (autor.id_autor == rel.id_autor) {
                                autoresStr.append(autor.nombre).append(" ").append(autor.apellido).append(", ");
                            }
                        }
                    }
                }

                // Elimina la última coma si es necesario
                String autoresFinal = autoresStr.length() > 0
                        ? autoresStr.substring(0, autoresStr.length() - 2)
                        : "Sin autor";

                modelo.addRow(new Object[]{
                    libro.id_libro,
                    libro.titulo,
                    libro.anio,
                    autoresFinal
                });
            }

            this.tablaLibros.setModel(modelo);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar libros y autores");
        }
    }*/
    private void cargarTabla(List<Libro> libros, List<Autor> autores, List<LibroAutor> relaciones) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Año");
        modelo.addColumn("Autores");

        for (Libro libro : libros) {
            StringBuilder autoresStr = new StringBuilder();
            for (LibroAutor rel : relaciones) {
                if (rel.id_libro == libro.id_libro) {
                    for (Autor autor : autores) {
                        if (autor.id_autor == rel.id_autor) {
                            autoresStr.append(autor.nombre).append(" ").append(autor.apellido).append(", ");
                        }
                    }
                }
            }

            String autoresFinal = autoresStr.length() > 0
                    ? autoresStr.substring(0, autoresStr.length() - 2)
                    : "Sin autor";

            modelo.addRow(new Object[]{
                libro.id_libro,
                libro.titulo,
                libro.anio,
                autoresFinal
            });
        }

        tablaLibros.setModel(modelo);
    }

    private void cargarTabla() {
        try {
            librosOriginales = ApiService.obtenerLibros();
            autoresOriginales = ApiService.obtenerAutores();
            relacionesOriginales = ApiService.obtenerRelaciones();

            cargarTabla(librosOriginales, autoresOriginales, relacionesOriginales);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar libros y autores");
        }
    }

    /*private void filtrarTabla() {
        String texto = txtBuscar.getText().toLowerCase();

        List<Libro> librosFiltrados = librosOriginales.stream()
                .filter(libro -> libro.titulo.toLowerCase().contains(texto))
                .toList();

        cargarTabla(librosFiltrados, autoresOriginales, relacionesOriginales);
    }*/
    private void filtrarTabla() {
        String texto = txtBuscar.getText().toLowerCase();

        List<Libro> librosFiltrados = librosOriginales.stream()
                .filter(libro -> {
                    boolean coincideTitulo = libro.titulo.toLowerCase().contains(texto);
                    boolean coincideAnio = String.valueOf(libro.anio).contains(texto);

                    boolean coincideAutor = relacionesOriginales.stream()
                            .filter(rel -> rel.id_libro == libro.id_libro)
                            .anyMatch(rel -> {
                                return autoresOriginales.stream()
                                        .filter(autor -> autor.id_autor == rel.id_autor)
                                        .anyMatch(autor
                                                -> autor.nombre.toLowerCase().contains(texto)
                                        || autor.apellido.toLowerCase().contains(texto)
                                        );
                            });

                    return coincideTitulo || coincideAnio || coincideAutor;
                })
                .toList();

        cargarTabla(librosFiltrados, autoresOriginales, relacionesOriginales);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaLibros = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        botonInsertarLibros = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaLibros);

        botonInsertarLibros.setText("Insertar");
        botonInsertarLibros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInsertarLibrosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(botonInsertarLibros)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(botonInsertarLibros)
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonInsertarLibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInsertarLibrosActionPerformed
        
    }//GEN-LAST:event_botonInsertarLibrosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonInsertarLibros;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaLibros;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
