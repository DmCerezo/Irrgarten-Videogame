/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.main;

/**
 *
 * @author David Martín Cerezo
 */

import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;
import irrgarten.Game;

/**
 * Programa main para ejecutar el juego en modo Debug
 */

public class Main {

    public static void main(String[] args) {
        
        /**
         * @brief Creación del juego, la vista y el controlador
         */
        
        Game game = new Game(1);    // Creación del juego
        TextUI view = new TextUI(); // Creación de la vista textual
        Controller controlador = new Controller(game, view);    // Creación del controlador
                // Añadir un jugador
                
        /**
         * @brief Muestra del juego y el controlador por la terminal
         */
        
        view.showGame(game.getGameState()); // Mostrar juego
        controlador.play(); 
        
        /**
         * @brief Comprobación de ganador y finalización del juego.
         */
        
        if(game.finished()){
            System.out.println("Juego finalizado.");
        }
        
        
    }
    
}