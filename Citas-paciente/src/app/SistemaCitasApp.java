package app;

import ui.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class SistemaCitasApp {
    public static void main(String[] args) {
        // RNF1: Disponibilidad 24/7 (Simulado: la aplicación inicia de inmediato)

        // Iniciar la interfaz gráfica en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal frame = new VentanaPrincipal();
            frame.setVisible(true);
        });
    }
}