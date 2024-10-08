/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author David
 */
public class Weapon {
    private float power;
    private int uses;
    
    public Weapon(float power, int uses){
        this.power = power;
        this.uses = uses;
    }
    
    public float attack(){
        if(this.uses > 0){
            this.uses--;
            return this.power;
        }
        return 0;
    }
    
    public String toString(){
        String info;
        info = ("W["+power+","+uses+"]");
        return info;
    }

}
