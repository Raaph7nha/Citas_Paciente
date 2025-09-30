package modelo;

public class Cita {
    private String pacienteCedula;
    private Especialidad especialidad;
    private String fecha;
    private String hora; // Para RF3: 40 minutos (simulado) y RF4: 7:00 am a 8:00 pm (simulado)
    private boolean activa;

    public Cita(String pacienteCedula, Especialidad especialidad, String fecha, String hora) {
        this.pacienteCedula = pacienteCedula;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.activa = true;
    }

    public String getPacienteCedula() { return pacienteCedula; }
    public Especialidad getEspecialidad() { return especialidad; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public boolean isActiva() { return activa; }

    // RF6: Cancelación con justificación (se simula la inactividad)
    public void cancelar(String justificacion) {
        this.activa = false;
        // Aquí se registraría la justificación en un sistema real
        System.out.println("Cita cancelada con justificación: " + justificacion);
    }

    // RF7: Reprogramación de cita
    public void reprogramar(String nuevaFecha, String nuevaHora) {
        this.fecha = nuevaFecha;
        this.hora = nuevaHora;
    }

    @Override
    public String toString() {
        return "Especialidad: " + especialidad.name() + " | Fecha: " + fecha + " | Hora: " + hora + " | Estado: " + (activa ? "Activa" : "Cancelada");
    }
}