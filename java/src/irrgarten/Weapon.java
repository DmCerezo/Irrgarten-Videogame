package irrgarten;

public class Weapon {
    private float power;
    private int uses;
    
    // Atributo de clase (estático) para contar el número de armas creadas
    private static int numWeapons = 0;

    // Constructor para inicializar los atributos
    public Weapon(float power, int uses) {
        this.power = power;
        this.uses = uses;
        numWeapons++;  // Incrementa el contador cada vez que se crea una nueva instancia
    }
    
    public Weapon(){
        this(power = Dice.weaponPower(), uses = Dice.usesLeft());
    }

    // Método para realizar un ataque
    public float attack() {
        if (this.uses > 0) {
            this.uses--;
            return this.power;
        }
        return 0;
    }
    
 
    public boolean discard(){
        return false;
    }
    // Método toString para mostrar el objeto en el formato "W[power,uses]"
    @Override
    public String toString() {
        return "W[" + power + "," + uses + "]";
    }

    // Método de clase (estático) para obtener el número total de armas creadas
    public static int getNumWeapons() {
        return numWeapons;
    }
    
    
}
