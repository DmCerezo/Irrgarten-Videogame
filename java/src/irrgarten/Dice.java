/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;

/**
 *
 * @author dezak
 */
public class Dice {
    // Atributos de clase privados (constantes)
    static private final int MAX_USES = 5;//Numero max de usos de armas y escudos
    static private final float MAX_INTELLIGENCE = 10.0f;//Valor max para inteligencia de jugadores y monstruos
    static private final float MAX_STRENGTH = 10.0f;
    static private final float RESURRECT_PROB = 0.3f;
    static private final int WEAPONS_REWARD = 2;
    static private final int SHIELDS_REWARD = 3;
    static private final int HEALTH_REWARD = 5;
    static private final int MAX_ATTACK = 3;
    static private final int MAX_SHIELD = 2;
    
    //Random Generator
    static private  final Random GENERATOR = new Random();

    static public int randomPos(int max){
        return Dice.GENERATOR.nextInt(max); //Devulve un numero entre 0 y max (Num de columnas o filas)-1
    }
    
    static public int whoStarts(int nplayers){
        return Dice.GENERATOR.nextInt(nplayers); //Devulve un numero entre 0 y numero de jugadores -1
    }
    
    static public float randomIntelligence(){
        return Dice.GENERATOR.nextFloat() * Dice.MAX_INTELLIGENCE; // Genera un número entre 0 y MAX_INTELLIGENCE   
    }
    
    static public float randomStrength(){
        
        return Dice.GENERATOR.nextFloat() * Dice.MAX_STRENGTH; // Genera un número entre 0 y MAX_STRENGTH   
    }
    
    static public boolean resurrectPlayer(){
        if (Dice.GENERATOR.nextFloat()< RESURRECT_PROB){
            return true;
        }else{
            return false;
        }
    }
    
     /**
     * Genera una cantidad aleatoria de salud a recibir como recompensa.
     * @return La recompensa de salud.
     */
    
    public static int healthReward(){
        return GENERATOR.nextInt(HEALTH_REWARD + 1);
    }
    
    static public int weaponsReward(){
        return Dice.GENERATOR.nextInt(WEAPONS_REWARD + 1); // Genera un número entre 0 y WEAPONS_REWARD    
    }

    static public int shieldsReward(){
        return Dice.GENERATOR.nextInt(SHIELDS_REWARD + 1); // Genera un número entre 0 y SHIELDS_REWARD    
    }
    
    static public float weaponPower(){
        return Dice.GENERATOR.nextFloat() * MAX_ATTACK; // Genera un número entre 0 y MAX_ATTACK   
    }
    
    static public float shieldPower(){
        return Dice.GENERATOR.nextFloat() * MAX_SHIELD; // Genera un número entre 0 y MAX_SHIELD   
    }
    
    static public int usesLeft(){
        return Dice.GENERATOR.nextInt(MAX_USES + 1); // Genera un número entre 0 y MAX_USES            
    }
    
    static public float intensity(float comeptence){
        return Dice.GENERATOR.nextFloat(comeptence + 1.0f); // Genera un número entre 0 y comeptence   
    }
    
    static public boolean discardElement(int usesLeft) {
        if (usesLeft >= MAX_USES) {
            return false; // Si los usos restantes son máximos, nunca se descarta
        }
        if (usesLeft <= 0) {
            return true; // Si no quedan usos, siempre se descarta
        }
        
        // Calculamos la probabilidad inversa
        float discardProbability = 1.0f - (float) usesLeft / MAX_USES;
        
        // Generamos un número aleatorio entre 0 y 1, y lo comparamos con la probabilidad
        return Dice.GENERATOR.nextFloat() < discardProbability;
    }
    
    
}
       
