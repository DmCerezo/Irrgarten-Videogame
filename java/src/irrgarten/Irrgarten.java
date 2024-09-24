/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten;

/**
 *
 * @author David
 */
public class Irrgarten {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Directions dir = Directions.DOWN;
        System.out.println("El enumero es:" + dir);
        
        Weapon w = new Weapon(2.0f,5);
        Shield s = new Shield(3.0f,4);
        System.out.println(w.toString());
        System.out.println(s.toString());

        
        
    }
    
}
