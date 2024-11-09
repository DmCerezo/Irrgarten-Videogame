/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author dezak
 */
public class Monster {
    static private int INITIAL_HEALTH = 5;
    
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    public Monster (String name, float intelligence, float strength){
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH; // Salud inicial predeterminada
    }
    
    public boolean dead(){
        return health <= 0;
    }
    
    public float attack(){
        Dice dice = new Dice();
        return dice.intensity(strength);
    }
    
    public boolean defend(float receivedAttack){ // Hecho a ojo Para la practica 3
        if(receivedAttack > intelligence){
            gotWounded();
        }else if (dead()){
            return true;
        }
        return false;
    }
    
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    // Método toString para representar el estado del monstruo
    @Override
    public String toString() {
        return "Monster{" +
                "name='" + name + '\'' +
                ", intelligence=" + intelligence +
                ", strength=" + strength +
                ", health=" + health +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
    
    private void gotWounded() {
        if (health > 0) {
            health -= 1;
        }
    }
}
