/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.utils;

import javaapplication1.Ticket;
import java.io.*;
import java.util.List;

public class TicketSerializationUtil {
    
    // Guardar lista de tickets en archivo
    public static void guardarTickets(String rutaArchivo, List<Ticket> tickets) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(tickets);
        }
    }

    // Cargar lista de tickets desde archivo
    public static List<Ticket> cargarTickets(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<Ticket>) ois.readObject();
        }
    }
}
