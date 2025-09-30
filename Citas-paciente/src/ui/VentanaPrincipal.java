package ui;
import java.util.Arrays;
import modelo.Cita;
import modelo.Especialidad;
import modelo.Paciente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// RNF2: Interfaz intuitiva (Se intenta con componentes Swing básicos)
public class VentanaPrincipal extends JFrame {

    // Simulación de la "Base de Datos" (RNF4: Escalabilidad, usando estructuras simples)
    private static final Map<String, Paciente> repositorioPacientes = new HashMap<>();
    private static final List<Cita> repositorioCitas = new ArrayList<>();
    private Paciente pacienteActual = null; // Usuario autenticado

    // Componentes de la interfaz
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Componentes de Autenticación (RF1)
    private JTextField txtCedula;
    private JPasswordField txtClave;
    private JButton btnLogin;

    // Componentes de Registro (RF2)
    private JTextField regTxtNombre;
    private JTextField regTxtCedula;
    private JPasswordField regTxtClave;
    private JButton btnRegistrar;

    // Componentes del Menú Principal
    private JButton btnAgendar;
    private JButton btnGestionar;
    private JButton btnLogout;

    public VentanaPrincipal() {
        super("Sistema de Gestión de Citas Médicas");

        // Configuración inicial de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null); // Centrar

        // Inicializar datos de prueba
        repositorioPacientes.put("12345678", new Paciente("Juan Pérez", "12345678", "clave123"));
        repositorioCitas.add(new Cita("12345678", Especialidad.CARDIOLOGIA, "2025-11-10", "10:00"));

        // Inicializar CardLayout para cambiar entre vistas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear las diferentes "pantallas"
        mainPanel.add(crearPanelAutenticacion(), "Autenticacion");
        mainPanel.add(crearPanelRegistro(), "Registro");
        mainPanel.add(crearPanelMenuPaciente(), "MenuPaciente");

        // Inicialmente, mostrar la autenticación
        add(mainPanel);
        cardLayout.show(mainPanel, "Autenticacion");
    }

    // -----------------------------------------------------------------------------------
    // --- 1. PANELES DE LA UI ---
    // -----------------------------------------------------------------------------------

    private JPanel crearPanelAutenticacion() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(new JLabel("Bienvenido al Sistema de Citas"));
        panel.add(new JLabel(""));

        panel.add(new JLabel("Cédula (RF1):"));
        txtCedula = new JTextField("12345678");
        panel.add(txtCedula);

        panel.add(new JLabel("Clave (RF1/RNF3):"));
        txtClave = new JPasswordField("clave123");
        panel.add(txtClave);

        // Botones de acción
        btnLogin = new JButton("Ingresar");
        JButton btnIrRegistro = new JButton("Registrarse (RF2)");

        panel.add(btnLogin);
        panel.add(btnIrRegistro);

        // Añadir listeners
        btnLogin.addActionListener(new AutenticacionListener());
        btnIrRegistro.addActionListener(e -> cardLayout.show(mainPanel, "Registro"));

        return panel;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(new JLabel("Registro de Nuevo Paciente (RF2)"));
        panel.add(new JLabel(""));

        panel.add(new JLabel("Nombre Completo:"));
        regTxtNombre = new JTextField();
        panel.add(regTxtNombre);

        panel.add(new JLabel("Cédula:"));
        regTxtCedula = new JTextField();
        panel.add(regTxtCedula);

        panel.add(new JLabel("Clave (RNF3):"));
        regTxtClave = new JPasswordField();
        panel.add(regTxtClave);

        // Botones de acción
        btnRegistrar = new JButton("Registrarme");
        JButton btnVolver = new JButton("Volver al Login");

        panel.add(btnRegistrar);
        panel.add(btnVolver);

        // Añadir listeners
        btnRegistrar.addActionListener(new RegistroListener());
        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, "Autenticacion"));

        return panel;
    }

    private JPanel crearPanelMenuPaciente() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(new JLabel("Menú de Paciente"));

        btnAgendar = new JButton("1. Agendar Cita (RF3, RF4, RF5, RF9)");
        btnGestionar = new JButton("2. Gestionar Citas (RF6, RF7)");
        btnLogout = new JButton("3. Cerrar Sesión");

        panel.add(btnAgendar);
        panel.add(btnGestionar);
        panel.add(btnLogout);

        // Añadir listeners
        btnAgendar.addActionListener(e -> mostrarVentanaAgendar());
        btnGestionar.addActionListener(e -> mostrarVentanaGestionar());
        btnLogout.addActionListener(e -> {
            pacienteActual = null;
            cardLayout.show(mainPanel, "Autenticacion");
        });

        return panel;
    }

    // -----------------------------------------------------------------------------------
    // --- 2. LISTENERS (LÓGICA) ---
    // -----------------------------------------------------------------------------------

    private class AutenticacionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cedula = txtCedula.getText();
            String clave = new String(txtClave.getPassword());

            // Simular RNF5: Respuesta rápida
            simularOperacionCritica("Autenticación");

            Paciente p = repositorioPacientes.get(cedula);

            if (p != null && p.getClave().equals(clave)) {
                pacienteActual = p;
                JOptionPane.showMessageDialog(null, "¡Bienvenido, " + pacienteActual.getNombre() + "!", "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "MenuPaciente");
            } else {
                JOptionPane.showMessageDialog(null, "Cédula o Clave incorrecta (RF1).", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RegistroListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = regTxtNombre.getText();
            String cedula = regTxtCedula.getText();
            String clave = new String(regTxtClave.getPassword());

            if (nombre.isEmpty() || cedula.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios (RF2).", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (repositorioPacientes.containsKey(cedula)) {
                JOptionPane.showMessageDialog(null, "La cédula ya se encuentra registrada.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Paciente nuevo = new Paciente(nombre, cedula, clave);
            repositorioPacientes.put(cedula, nuevo);

            // Simular RNF5
            simularOperacionCritica("Registro");

            JOptionPane.showMessageDialog(null, "Registro exitoso. Por favor ingrese con su cédula y clave (RF2).", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Autenticacion");
        }
    }

    // -----------------------------------------------------------------------------------
    // --- 3. VENTANAS DE FUNCIONALIDAD (RF3 - RF9) ---
    // -----------------------------------------------------------------------------------

    private void mostrarVentanaAgendar() {
        JFrame frameAgendar = new JFrame("Agendar Cita (RF3, RF4, RF5, RF9)");
        frameAgendar.setSize(400, 300);
        frameAgendar.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // RF9 & RF5: Filtrar especialidades ya agendadas
        List<Especialidad> especialidadesDisponibles = Arrays.asList(Especialidad.values());

        // Lógica RF5: Excluir especialidad si ya tiene una cita activa
        List<String> especialidadesAgendadas = repositorioCitas.stream()
                .filter(c -> c.getPacienteCedula().equals(pacienteActual.getCedula()) && c.isActiva())
                .map(c -> c.getEspecialidad().name())
                .collect(Collectors.toList());

        Especialidad[] opcionesEspecialidad = especialidadesDisponibles.stream()
                .filter(e -> !especialidadesAgendadas.contains(e.name()))
                .toArray(Especialidad[]::new);

        if (opcionesEspecialidad.length == 0) {
            JOptionPane.showMessageDialog(frameAgendar, "Ya ha agendado una cita por cada especialidad disponible (RF5).", "Límite Alcanzado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        panel.add(new JLabel("Especialidad (RF9, RF5):"));
        JComboBox<Especialidad> cmbEspecialidad = new JComboBox<>(opcionesEspecialidad);
        panel.add(cmbEspecialidad);

        panel.add(new JLabel("Fecha:"));
        JTextField txtFecha = new JTextField("2025-12-01");
        panel.add(txtFecha);

        panel.add(new JLabel("Hora (RF4: 07:00 a 20:00):"));
        JTextField txtHora = new JTextField("14:00");
        panel.add(txtHora);

        panel.add(new JLabel("Duración: 40 minutos (RF3)"));
        panel.add(new JLabel(""));

        JButton btnConfirmar = new JButton("Confirmar Cita");
        panel.add(btnConfirmar);

        btnConfirmar.addActionListener(e -> {
            String fecha = txtFecha.getText();
            String hora = txtHora.getText();
            Especialidad especialidad = (Especialidad) cmbEspecialidad.getSelectedItem();

            // Simulación de validación de horario (RF4)
            if (hora.compareTo("07:00") < 0 || hora.compareTo("20:00") > 0) {
                JOptionPane.showMessageDialog(frameAgendar, "Horario no disponible. Debe ser entre 07:00 y 20:00 (RF4).", "Error de Horario", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Simular RNF5
            simularOperacionCritica("Agendar Cita");

            Cita nuevaCita = new Cita(pacienteActual.getCedula(), especialidad, fecha, hora);
            repositorioCitas.add(nuevaCita);

            JOptionPane.showMessageDialog(frameAgendar, "Cita agendada con éxito:\n" + nuevaCita.toString(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            simularNotificacion(); // RF8 & RNF6
            frameAgendar.dispose();
        });

        frameAgendar.add(panel);
        frameAgendar.setVisible(true);
    }

    private void mostrarVentanaGestionar() {
        JFrame frameGestionar = new JFrame("Gestionar Citas (RF6, RF7)");
        frameGestionar.setSize(600, 400);
        frameGestionar.setLocationRelativeTo(this);
        frameGestionar.setLayout(new BorderLayout());

        // Filtrar citas activas del paciente actual
        List<Cita> citasPaciente = repositorioCitas.stream()
                .filter(c -> c.getPacienteCedula().equals(pacienteActual.getCedula()) && c.isActiva())
                .collect(Collectors.toList());

        String[] citasArray = citasPaciente.stream()
                .map(Cita::toString)
                .toArray(String[]::new);

        JList<String> listCitas = new JList<>(citasArray);
        listCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frameGestionar.add(new JScrollPane(listCitas), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton btnCancelar = new JButton("Cancelar Cita (RF6)");
        JButton btnReprogramar = new JButton("Reprogramar Cita (RF7)");

        panelBotones.add(btnCancelar);
        panelBotones.add(btnReprogramar);

        frameGestionar.add(panelBotones, BorderLayout.SOUTH);

        // Lógica de Cancelación (RF6)
        btnCancelar.addActionListener(e -> {
            int selectedIndex = listCitas.getSelectedIndex();
            if (selectedIndex != -1) {
                Cita citaSeleccionada = citasPaciente.get(selectedIndex);
                String justificacion = JOptionPane.showInputDialog(frameGestionar, "Ingrese justificación para cancelar (RF6):");

                if (justificacion != null && !justificacion.trim().isEmpty()) {
                    citaSeleccionada.cancelar(justificacion);
                    simularOperacionCritica("Cancelación"); // RNF5
                    JOptionPane.showMessageDialog(frameGestionar, "Cita cancelada con éxito.", "Éxito (RF6)", JOptionPane.INFORMATION_MESSAGE);
                    simularNotificacion(); // RF8 & RNF6
                    frameGestionar.dispose(); // Recargar ventana
                } else {
                    JOptionPane.showMessageDialog(frameGestionar, "Debe proporcionar una justificación (RF6).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frameGestionar, "Seleccione una cita.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Lógica de Reprogramación (RF7)
        btnReprogramar.addActionListener(e -> {
            int selectedIndex = listCitas.getSelectedIndex();
            if (selectedIndex != -1) {
                Cita citaSeleccionada = citasPaciente.get(selectedIndex);

                String nuevaFecha = JOptionPane.showInputDialog(frameGestionar, "Ingrese la nueva FECHA:");
                String nuevaHora = JOptionPane.showInputDialog(frameGestionar, "Ingrese la nueva HORA (RF4: 07:00 a 20:00):");

                if (nuevaFecha != null && nuevaHora != null) {
                    // Simulación de validación de horario (RF4)
                    if (nuevaHora.compareTo("07:00") < 0 || nuevaHora.compareTo("20:00") > 0) {
                        JOptionPane.showMessageDialog(frameGestionar, "Horario no disponible. Debe ser entre 07:00 y 20:00 (RF4).", "Error de Horario", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    citaSeleccionada.reprogramar(nuevaFecha, nuevaHora);
                    simularOperacionCritica("Reprogramación"); // RNF5
                    JOptionPane.showMessageDialog(frameGestionar, "Cita reprogramada con éxito (RF7).", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    simularNotificacion(); // RF8 & RNF6
                    frameGestionar.dispose(); // Recargar ventana
                }
            } else {
                JOptionPane.showMessageDialog(frameGestionar, "Seleccione una cita.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frameGestionar.setVisible(true);
    }

    // -----------------------------------------------------------------------------------
    // --- 4. SIMULACIÓN RNF ---
    // -----------------------------------------------------------------------------------

    // RNF5: Respuesta en menos de 3 segundos
    private void simularOperacionCritica(String operacion) {
        try {
            // Se simula un retraso de 1 segundo, cumpliendo RNF5 (< 3 segundos)
            Thread.sleep(1000);
            System.out.println("RNF5: Operación de " + operacion + " completada.");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // RF8 y RNF6: Notificación por correo electrónico en menos de 1 minuto
    private void simularNotificacion() {
        // En un sistema real se usaría un servicio de correo asíncrono.
        // Se simula la velocidad (< 1 minuto)
        JOptionPane.showMessageDialog(this, "RF8/RNF6: Notificación por correo electrónico enviada.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
    }
}