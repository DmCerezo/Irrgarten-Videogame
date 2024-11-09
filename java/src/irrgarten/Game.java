/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author david
 */


/**
 * Clase juego. Se encarga de crear y gestionar todo el juego mientras se mantenga una partida
 */

public class Game {
    
    //*****************************************************************
    // Atributos
    //*****************************************************************
    
    private static int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
   
    public Player currentPlayer;
    private ArrayList<Player> players;  // lista de jugadores
    private ArrayList<Monster> monsters;    // lista de monstruos
    private Labyrinth labyrinth;    // laberinto
    
    //*****************************************************************
    // Constructor
    //*****************************************************************
    
    /**
     * Constructor que genera el juego en sí valiéndose del resto de clases.
     * @param nplayers Número de jugadores.
     */
    
    public Game(int nplayers)   
    {
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.log = "";
        
        for (int i = 0; i <= nplayers; i++){
            players.add(new Player((char)(i+48),Dice.randomIntelligence(),Dice.randomStrength()));
        }
        
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        this.currentPlayer = players.get(currentPlayerIndex);
        
        this.labyrinth = new Labyrinth(10,10,9,0);        
        configureLabyrinth();
        labyrinth.spreadPlayers(players);
    }
    
    /**
     * Cálculo booleano que se vale de si hay un ganador o no, para finalizar
     * la partida.
     * @return true o false dependiendo del valor devuelto por el método haveAWinner del laberinto.
     */
    
    public boolean finished()
    {
        return labyrinth.haveAWinner();
    }
    
    
    public Directions actualDirection(Directions preferredDirection){
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        
        ArrayList <Directions> validMoves = labyrinth.validMoves(currentRow, currentCol);
        Directions output = currentPlayer.move(preferredDirection, validMoves);
        
        return output;
    }
    
    /**
     * Método que calcula el próximo paso a dar por el juego en partida.
     * @param preferredDirection Dirección de movimiento deseada.
     * @return Siguiente paso a seguir por el juego, si finaliza o continúa.
     */
    
    public boolean nextStep(Directions preferredDirection){
        this.log = "";
        boolean dead = currentPlayer.dead();
        
        if(!dead){
            
            Directions direction = actualDirection(preferredDirection);
            
            if(direction !=preferredDirection){ 
                this.logPlayerNoOrders(); 
            }
            
            Monster monster = labyrinth.putPlayer(direction, currentPlayer);
            
            if(monster == null){
                this.logNoMonster();
            }else{
                GameCharacter winner = this.combat(monster);
                this.manageReward(winner);
            }
            
        }
        manageResurrection();
        boolean endGame = finished();
        
        if(!endGame){
            nextPlayer();
        }
        
        return endGame;
    }
    
    /**
     * Método que gestiona los combates.
     * @param monster Monstruo que se encuentra en la casilla de combate.
     * @return Ganador del combate: ¿monstruo o jugador?
     */
    
    public GameCharacter combat(Monster monster){
        
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        float playerAttack = currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        
        while((!lose)&&(rounds<MAX_ROUNDS)){
            winner = GameCharacter.MONSTER;
            rounds++;
            float monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);
            
            if(!lose){
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }
        this.logRounds(rounds, MAX_ROUNDS);
        return winner;
    }
    
    
    /**
     * Método para ver si se resucita a un jugador o no aleatoriamente.
     */
    public void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();
        if(resurrect){
            currentPlayer.resurrect();
            logResurrected();
        }else{
            logPlayerSkipTurn();
        }
    }
    
    
    /**
     * Estado del juego.
     * @return El estado actual del juego: laberinto, jugadores, monstruos, jugador actual, etc.
     */
    
    public GameState getGameState()
    {
        GameState gameState = new GameState(labyrinth.toString(),
                players.toString(),monsters.toString(),currentPlayerIndex,
                finished(),log);  
        
        return gameState;
    }
    
    /**
     * Método usado para configurar el laberinto y rellenar sus posiciones de 
     * bloques y monstruos (exceptuando la casilla de salida) para el Usuario.
     */
    
    private void configureLabyrinth()
    {
        int tamTotal = labyrinth.getRows() * labyrinth.getCols();       
        int nMonstruos = tamTotal / 5;
        
        // Generación aleatoria de bloques por el mapa
        int nBloques = 20;
        for (int i = 0; i < nBloques; i++){
            labyrinth.addBlock(Orientation.HORIZONTAL, Dice.randomPos(labyrinth.getRows()),
                      Dice.randomPos(labyrinth.getCols()), 1);
        }
        
        //Inicializa y añade los monstruos
        for (int i = 0; i < nMonstruos; i++){
            Monster monstruo = new Monster("#"+(i),Dice.randomIntelligence(),Dice.randomStrength());       
            monsters.add(monstruo);
            labyrinth.addMonster(Dice.randomPos(labyrinth.getRows()), 
                              Dice.randomPos(labyrinth.getCols()), monstruo);
        }
            
    }
    
    
    /**
     * Calcula el próximo jugador del que será turno en partida.
     */
    
    private void nextPlayer()
    {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }
    
    
    /**
     * Método gestor de las recompensas de combate para los jugadores.
     * @param winner Ganador del combate.
     */
    
    private void manageReward(GameCharacter winner)
    {
    //pendiente de implementar
    }
    
    
    
    
    //*****************************************************************
    // LOGS
    //*****************************************************************

    
    private void logPlayerWon()
    {
        log+="Jugador #"+currentPlayerIndex+", has ganado el combate!!\n"; //también podría ponerse quizás el player.getNumber()?
    }
    
    private void logMonsterWon()
    {
        log+="Jugador #"+currentPlayerIndex+", has perdido contra el monstruo!!\n";
    }
    
    private void logResurrected()
    {
        log+="Jugador #"+currentPlayerIndex+", has sido resucitado!!\n";
    }
    
    private void logPlayerSkipTurn()
    {
        log+="Jugador #"+currentPlayerIndex+", estás muerto. Se pasa tu turno\n";
    }
    
    private void logPlayerNoOrders()
    {
        log+="No es posible seguir la instrucción\n";
    }
    
    private void logNoMonster()
    {
        log+="Jugador "+currentPlayerIndex+", acabas de caer en una celda vacía"
                + ", o en la casilla de salida\n";
    }
    
    private void logRounds(int rounds, int max)
    {
        log+="Se han producido "+rounds+" de "+max+" rondas de combate.\n";
    }

    
}