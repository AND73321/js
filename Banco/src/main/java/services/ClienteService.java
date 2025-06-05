/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.ClienteDao;
import dao.TransaccionDao;
import javax.swing.JOptionPane;
import models.Cliente;
import models.Transaccion;

/**
 *
 * @author USUARIO
 */
public class ClienteService {
     private ClienteDao clienteDao = new ClienteDao();
    private TransaccionDao transDao  = new TransaccionDao();

    /**
     * Devuelve true si logró procesar la transacción: 
     *   - buscar cliente
     *   - calcular nuevo saldo según tipo
     *   - actualizar saldo en tabla 'clientes'
     *   - registrar la fila en 'transacciones'
     */
    public boolean procesarTransaccion(Transaccion t) throws Exception {
        // 1) Leer cliente
        Cliente c = clienteDao.buscarPorCedula(t.getCedula());
        if (c == null) {
            JOptionPane.showMessageDialog(null, "Cliente con cédula " + t.getCedula() + " no existe.");
            return false;
        }

        double saldoActual = c.getSaldo();
        double monto = t.getMonto();
        double nuevoSaldo;

        // 2) Validar tipo y calcular nuevo saldo
        if ("DEPOSITO".equalsIgnoreCase(t.getTipo())) {
            nuevoSaldo = saldoActual + monto;
        } 
        else if ("RETIRO".equalsIgnoreCase(t.getTipo())) {
            if (monto > saldoActual) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente para retirar $" + monto);
                return false;
            }
            nuevoSaldo = saldoActual - monto;
        } 
        else {
            JOptionPane.showMessageDialog(null, "Tipo de transacción inválido: " + t.getTipo());
            return false;
        }

        // 3) Actualizar saldo del cliente
        boolean okSaldo = clienteDao.actualizarSaldo(t.getCedula(), nuevoSaldo);
        if (!okSaldo) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el saldo del cliente.");
            return false;
        }

        // 4) Insertar la transacción en la tabla 'transacciones'
        boolean okInsert = transDao.insertTransaccion(t);
        if (!okInsert) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar la transacción.");
            return false;
        }

        return true;
    }
}
