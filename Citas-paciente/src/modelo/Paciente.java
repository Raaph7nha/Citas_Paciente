package modelo;

// RF2: Registro de nuevos pacientes con datos personales.
public class Paciente {
    private String nombre;
    private String cedula;
    private String clave; // RNF3: Seguridad y cifrado (simulado)

    public Paciente(String nombre, String cedula, String clave) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.clave = clave;
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getClave() { return clave; }
}