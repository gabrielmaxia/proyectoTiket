package javaapplication1;

import java.util.Stack;
import javaapplication1.models.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ag045
 */
public class Ticket {
    private String id;
    private String title;
    private String estado;
    private String date;
    private int usuarioId;
    private int departamentoId;
    private String descripcion;
    private String usuarioNombre;
     private String departamentoNombre;
     private String prioridad;
     private String fechaCierre;
     

    
    public Ticket(String id, String title) {
        this(id, title, "Pendiente", java.time.LocalDate.now().toString(), "");
    }
public Ticket(String id, String title, String status, String date, String description) {
    
    
        // Validación de datos
        if(id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID no puede estar vacío");
        }
        this.id = id;
        this.title = title;
        this.estado = status;
        this.date = date;
        this.descripcion = description;
    }
public Ticket(int id, String titulo, String descripcion, String estado, 
              String prioridad, int usuarioId, int departamentoId) {
    this(String.valueOf(id), titulo, estado, "", descripcion);
    // Asigna los demás campos si son necesarios
    this.prioridad = prioridad;
    this.usuarioId = usuarioId;
    this.departamentoId = departamentoId;
}

public void aplicarCambioEstado(Usuario usuario, String nuevoEstado) {
    usuario.cambiarEstadoTicket(this, nuevoEstado); // Polimorfismo aquí
}

public StringProperty idProperty() {
        return new SimpleStringProperty(id);
    }

    public StringProperty titleProperty() {
        return new SimpleStringProperty(title);
    }

    public StringProperty statusProperty() {
        return new SimpleStringProperty(estado);
    }

    public StringProperty dateProperty() {
        return new SimpleStringProperty(date);
    }

    public StringProperty priorityProperty() {
        return new SimpleStringProperty(prioridad);
    }

    public StringProperty departamentoNombreProperty() {
        return new SimpleStringProperty(departamentoNombre);
    }

 
    public String getId() { return id; }
    

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Título no puede estar vacío");
        }
        this.title = title;
    }
    
    // Getters y Setters
    
    
    public String getStatus() { return estado; }
    public String getDate() { return date; }
    
    public void setId(String id) { this.id = id; }
    //public void setTitle(String title) { this.title = title; }
    public void setStatus(String status) { this.estado = status; }
    public void setDate(String date) { this.date = date; }
    
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(int departamentoId) { this.departamentoId = departamentoId; }

    public String getDescription() { return descripcion; }
    public void setDescription(String description) { this.descripcion = description; }
    
    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }
    
    public String getDepartamentoNombre() { return departamentoNombre; }
    public void setDepartamentoNombre(String departamentoNombre) { 
        this.departamentoNombre = departamentoNombre; 
    }
    
    public String getPriority() { return prioridad; }
    public void setPriority(String priority) { this.prioridad = priority; }
    
    public String getFechaCierre() {
    return fechaCierre;
}
    public void setFechaCierre(String fechaCierre) {
    this.fechaCierre = fechaCierre;
}
    private Stack<String> historialEstados = new Stack<>();

public void agregarAlHistorial(String cambio) {
    historialEstados.push(cambio);
}

public Stack<String> getHistorial() {
    return historialEstados;
}
    

}