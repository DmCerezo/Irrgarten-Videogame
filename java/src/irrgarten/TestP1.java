/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten;

/**
 *
 * @author David
 */
public class TestP1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Player p1 = new Player('1', 3.3f, 4.8f);
                
        var dir = Directions.DOWN;
        System.out.println("El enumero es:" + dir);
        
        Weapon w = new Weapon(2.0f, 5);
        Shield s = new Shield(3.0f, 4);
        System.out.println(w.toString());
        System.out.println(s.toString());
        Weapon weapon1 = new Weapon(10.5f, 5);
        Weapon weapon2 = new Weapon(7.2f, 3);

        System.out.println(Weapon.getNumWeapons());
       
        for(int i = 0; i < 10; i++){
            System.out.print("inteligencia: " + Dice.randomIntelligence()+"\n");
                        System.out.print("Usos: " + Dice.usesLeft() +"\n");  
            
        }
        
    }
    
}
