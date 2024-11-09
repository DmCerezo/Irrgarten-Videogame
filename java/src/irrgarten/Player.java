/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author dezak
 */
import java.util.ArrayList;

public class Player {
    // Constantes de clase
    static private final int MAX_WEAPONS = 2;
    static private final int MAX_SHIELDS = 3;
    static private final int INITIAL_HEALTH = 10;
    static private final int HITS2LOSE = 3;

    // Atributos de instancia
    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits;

    // Atributos de relaciones
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    // Constructor
    public Player(char number, float intelligence, float strength) {
        this.number = number;
        this.name = "Player #" + this.number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.consecutiveHits = 0;
        this.health = Player.INITIAL_HEALTH;
        this.row = -1; // Valores iniciales fuera del tablero
        this.col = -1;
        this.weapons = new ArrayList<>();
        this.shields = new ArrayList<>();
    }

    // Método para resucitar al jugador
    public void resurrect() {
        this.weapons.clear();
        this.shields.clear();
        this.health = INITIAL_HEALTH;
        this.consecutiveHits = 0;
    }

    // Método para establecer la posición del jugador
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Método que indica si el jugador está muerto
    public boolean dead() {
        return health <= 0;
    }

    // Método que calcula el ataque del jugador
    public float attack() {
        return strength + sumWeapons();
    }

    // Método de defensa
    public boolean defend(float receivedAttack) {
        return manageHit(receivedAttack);
    }

    // Método para crear una nueva arma
    public Weapon newWeapon() {
        Dice dice = new Dice();
        return new Weapon(dice.weaponPower(), dice.usesLeft());
    }

    // Método para crear un nuevo escudo
    public Shield newShield() {
        Dice dice = new Dice();
        return new Shield(dice.shieldPower(), dice.usesLeft());
    }

    // Energía defensiva
    public float defensiveEnergy() {
        return intelligence + sumShields();
    }

    // Reiniciar contador de impactos consecutivos
    public void resetHits() {
        this.consecutiveHits = 0;
    }

    // Método que reduce la salud del jugador
    public void gotWounded() {
        if (health > 0) {
            health --;
        }
    }
    
    /**
     * Getter de la fila del jugador.
     * @return La fila en la que se encuentra el jugador.
     */
    
    public int getRow()
    {
        return this.row;
    }
    
    /**
     * Getter de la columna del jugador.
     * @return La columna donde se encuentra el jugador.
     */
    
    
    public int getCol()
    {
        return this.col;
    }
    
    /**
     * Getter del número del jugador.
     * @return El número del jugador actual.
     */
    
    public char getNumber()
    {
        return this.number;
    }

    // Incrementa el contador de impactos consecutivos
    public void incConsecutiveHits() {
        this.consecutiveHits ++;
    }

    // Suma los ataques de todas las armas
    private float sumWeapons() {
        float sum = 0.0f;
        for (Weapon w : weapons) {
            sum += w.attack();
        }
        return sum;
    }

    // Suma la protección de todos los escudos
    private float sumShields() {
        float sum = 0.0f;
        for (Shield s : shields) {
            sum += s.protect();
        }
        return sum;
    }

    // Representación completa del estado del jugador
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", intelligence=" + intelligence +
                ", strength=" + strength +
                ", health=" + health +
                ", row=" + row +
                ", col=" + col +
                ", consecutiveHits=" + consecutiveHits +
                ", weapons=" + weapons +
                ", shields=" + shields +
                '}';
    }
    
    
    Directions move(Directions directions, ArrayList<Directions> validMoves){
        int size = validMoves.size();
        boolean contained =  validMoves.contains(directions);
        
        if((size>0) && (!contained)){
             return validMoves.get(0);
        }else{
            return directions;
        }
    }
    
    public void receiveWeapon(Weapon w){
        for(int i = weapons.size()-1;i >= 0 ;i--) {
            Weapon wi = weapons.get(i);
            boolean discard = wi.discard();
            if(discard){
                weapons.remove(wi);
            }
            int size = weapons.size();
            if(size < MAX_WEAPONS){
                weapons.add(w);
            }
                    
        }
    }
    
    public void receiveShield(Shield s){
        for(int i = shields.size()-1;i >= 0 ;i--) {
            Shield si = shields.get(i);
            boolean discard = si.discard();
            if(discard){
                shields.remove(si);
            }
            int size = shields.size();
            if(size < MAX_SHIELDS){
                shields.add(s);
            }
                    
        }
    }   
       
    void receiveReward(){
        int wReward = Dice.weaponsReward();
        int wShield = Dice.shieldsReward();
        
        for(int i = 1; i < wReward; i++){
            Weapon wnew = new Weapon();
            this.receiveWeapon(wnew);
        }
        
        for(int i = 1; i < wShield; i++){
            Shield snew = new Shield();
            this.receiveShield(snew);
        }
        int extraHealth = Dice.healthReward();
        float mihealth = this.health;
        
        this.health = extraHealth+mihealth;
    }
    
    
    boolean manageHit(float receivedAttack){
        float defense = this.defensiveEnergy();
        if(defense < receivedAttack){
            this.gotWounded();
            this.incConsecutiveHits();
        }else{
            this.resetHits();
        }
        
        if((consecutiveHits == HITS2LOSE)||(dead())){
            this.resetHits();
            return true;
        }else{
            return false;
        }
        
    }

    
}

