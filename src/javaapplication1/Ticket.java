package javaapplication1;

/**
 *
 * @author ag045
 */
public class Ticket {
    private String id;
    private String title;
    private String status;
    private String date;
    private String description;
     private int usuarioId;
    private int departamentoId;
    private String priority;

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
        this.status = status;
        this.date = date;
        this.description = description;
    }

    // Implementación de POO: Getters y Setters con validación
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Título no puede estar vacío");
        }
        this.title = title;
    }
    
    // Getters y Setters
    public String getStatus() { return status; }
    public String getDate() { return date; }
    
    public void setId(String id) { this.id = id; }
    //public void setTitle(String title) { this.title = title; }
    public void setStatus(String status) { this.status = status; }
    public void setDate(String date) { this.date = date; }

    String getTitulo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String getEstado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setUsuarioNombre(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setDepartamentoNombre(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}