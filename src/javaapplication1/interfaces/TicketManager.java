/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// En src/javaapplication1/interfaces/TicketManager.java
package javaapplication1.interfaces;

import javaapplication1.exceptions.DataLoadException;

public interface TicketManager {
    void loadTickets() throws DataLoadException;
}
