package javaapplication1;

import javaapplication1.models.Usuario;

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

public void aplicarCambioEstado(Usuario usuario, String nuevoEstado) {
    usuario.cambiarEstadoTicket(this, nuevoEstado); // Polimorfismo aquí
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
    
    

}