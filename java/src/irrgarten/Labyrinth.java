/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;
import java.util.ArrayList;


import static irrgarten.Directions.DOWN;
import static irrgarten.Directions.LEFT;
import static irrgarten.Directions.RIGHT;
import static irrgarten.Directions.UP;

/**
 *
 * @author D. Martin Cerezo
 */
public class Labyrinth {
    // Constantes de clase
    static private final char BLOCK_CHAR = 'X';
    static private final char EMPTY_CHAR = '-';
    static private final char MONSTER_CHAR = 'M';
    static private final char COMBAT_CHAR = 'C';  
    static private final char EXIT_CHAR = 'E';
    static private final char PLAYER_CHAR = 'P'; // Caracter para representar al jugador
    static private final int ROW = 0; 
    static private final int COL = 1;  
    
    // Atributos de instancia
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    // Tablas de estado
    private Monster[][] monsters;
    private Player[][] players;
    private char[][] labyrinth;

   /**
     * Constructor del laberinto
     * @param nRows Número de filas
     * @param nCols Número de columnas
     * @param exitRow Número de fila de la casilla de salida
     * @param exitCol Número de columna de la casilla de salida
     */
       
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
     
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        
        // Inicializar las tablas
        this.monsters = new Monster[nRows+1][nCols+1];
        this.players = new Player[nRows+1][nCols+1];
        this.labyrinth = new char[nRows+1][nCols+1];
        
        // Inicializar la tabla de celdas como vacías
        for (int i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++){
                labyrinth[i][j] = EMPTY_CHAR;
            }
        }
        
        labyrinth[exitRow][exitCol] = EXIT_CHAR;
        
    }
    
    /**
     * Método que comprueba si hay un ganador o no.
     * @return true si hay algun jugador en la casilla de salida.
     */
    
    public boolean haveAWinner(){
        return players[exitRow][exitCol] != null;
    }
    
    /**
     * Método toString
     * @return Cadena de caracteres con la matriz del laberinto y su estado actual
     */
    
    @Override
    public String toString()
    {
        String estado = "";
        
        for (int i=0; i < nRows; i++){
            for (int j=0; j < nCols; j++){
                estado += " ["+labyrinth[i][j]+"]";
            }
            estado += "\n";
        }
        estado +="\n";
        estado +="Tamaño del laberinto: "+getRows()+"x"+getCols()+"\n"
                + "Casilla de salida: ["+exitRow+","+exitCol+"]";
        return estado;
        
    }
    
    /**
     * Método que añade un monstruo al tablero.
     * @param row Fila en la que se añade el monstruo
     * @param col Columna en la que se añade el monstruo
     * @param monster Monstruo
     */    
    
    public void addMonster(int row, int col, Monster monster)
    {
        if(this.posOK(row, col)){
            while(!emptyPos(row,col)){
                row = Dice.randomPos(getRows());
                col = Dice.randomPos(getCols());
            }

            labyrinth[row][col] = MONSTER_CHAR;
            monsters[row][col] = monster;
            monster.setPos(row, col);
        }
    }
    
    /**
     * Método para comprobar si una posición es correcta en le tablero.
     * @param row Fila que se comprueba
     * @param col Columna que se compruba
     * @return True si la posición esta dentro del laberinto
     */  
    public boolean posOK(int row, int col){
        return (row >= 0) && (row <= nRows) && (col >= 0) && (col <= nCols);
    }

    
    /**
     * Calcula si la posición pasada como parámetro está o no vacía
     * @param row Fila
     * @param col Columna
     * @return true si la posición está vacía
     */
    
    private boolean emptyPos(int row, int col)
    {           
        return labyrinth[row][col] == EMPTY_CHAR;
    }
    
    /**
     * Calcula si la posición pasada es ocupada por un monstruo
     * @param row Fila
     * @param col Columna
     * @return true si la posición la ocupa un mosntruo
     */
    
    private boolean monsterPos(int row, int col)
    {           
        return labyrinth[row][col] == MONSTER_CHAR;
    }
    
    /**
     * Calcula si la posición pasada es la de salida
     * @param row Fila
     * @param col Columna
     * @return true si es la casilla de salida
     */
    
    private boolean exitPos(int row, int col)
    {           
        return (labyrinth[row][col] == EXIT_CHAR);
    }    
    
    
    /**
     * Calcula si la posición pasada contiene tanto un monstruo como un jugador.
     * @param row Fila
     * @param col Columna
     * @return true si hay combate en esa posición.
     */
    public boolean combatPos(int row, int col) {
        return players[row][col] != null && monsters[row][col] != null;
    }
    
    /**
     * Indica si la posición es válida para moverse: casilla vacía, con un monstruo, o de salida.
     * @param row Fila
     * @param col Columna
     * @return true si se puede pisar la casilla.
     */
    public boolean canStepOn(int row, int col) {
        if (!posOK(row, col)) {
            return false;
        }else{
            return (emptyPos(row, col) || monsterPos(row, col) || exitPos(row, col));
        }
    }

    /**
     * Actualiza la casilla al estado correcto cuando un jugador la abandona.
     * @param row Fila
     * @param col Columna
     */
    public void updateOldPos(int row, int col) {
        
        if (posOK(row, col)) {
            // Si había combate, pasa a mostrar solo un monstruo; si no, queda vacía
            if (combatPos(row, col)) {
                labyrinth[row][col] = MONSTER_CHAR;
            }else {
                labyrinth[row][col] = EMPTY_CHAR;
            }
        }
        
        // Eliminar el jugador de la tabla de jugadores en esta posición
        players[row][col] = null;
    }

    /**
     * Determina la posición a la que se va a mover un jugador en función de 
     * la dirección que tome
     * @param row Fila
     * @param col Columna
     * @param direction Dirección de movimiento
     * @return La nueva posición a la que se moverá el jugador
     */
    
    private ArrayList<Integer> dir2Pos(int row, int col, Directions direction)
    {
        if (posOK(row,col)){
            switch (direction)
            {
                case UP:
                    row--;
                    break;
                case DOWN:
                    row++;
                    break;
                case LEFT:
                    col--;
                    break;
                case RIGHT:
                    col++;
                    break;
            }
        }
        
        ArrayList<Integer> newPos = new ArrayList<>();
        newPos.add(row);
        newPos.add(col);
        
        return newPos;
    }

    /**
     * Genera una posición aleatoria en el laberinto que esté vacía.
     * @return Array {fila, columna} de una posición vacía.
     */
    public int[] randomEmptyPos() {
        int row, col;

        do {
            row = Dice.randomPos(nRows);
            col = Dice.randomPos(nCols);
        } while (emptyPos(row, col));

        return new int[]{row, col};
    }

        
    /**
     * Método que se encarga de esparcir a los jugadores por las posiciones
     * válidas del laberinto que quedan libres tras la configuración
     * @param players Lista de jugadores
    **/
    
    public void spreadPlayers(ArrayList<Player> players)
    {
        int oldRow = -1,
            oldCol = -1;
        for(int i=0;i<players.size()-1; i++){
            Player p = players.get(i);
            int pos[] = this.randomEmptyPos();
            
            this.putPlayers2D(oldRow, oldCol, pos[ROW], pos[COL], p );
        }
    }
    
     /**
     * Método que coloca jugadores en posiciones bidimensionales en el laberinto
     * y actualiza el estado de la posición en función de donde éste haya caído.
     * @param oldRow Fila de la casilla anterior del jugador
     * @param oldCol Estado anterior de la columna de la casilla
     * @param row Fila de la nueva casilla
     * @param col Columna de la nueva casilla
     * @param player Jugador
     * @return un output en forma de monstruo, nulo si no lo había, monstruo si lo había
     */
    
    Monster putPlayers2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output = null;
        if(canStepOn(row,col)){
            if(posOK(oldRow,oldCol)){
                Player p = players[oldRow][oldCol];
                if(p == player){
                    this.updateOldPos(oldRow, oldCol);
                    player.setPos(oldRow, oldCol);
                }
            }
            
            boolean monsterPos = this.monsterPos(row, col);
            
            if(monsterPos){
                labyrinth[row][col] = COMBAT_CHAR;
               output = monsters[row][col];
            }else{
                char number = player.getNumber();
                labyrinth[row][col] = number;
            }
            
            players[row][col] = player;
            player.setPos(row, col);
        }  
        return output;
    }
    
    /**
     * Mueve un jugador a una posición determinada y devuelve un posible monstruo
     * si el movimiento resulta en la presencia de uno en la nueva posición del
     * jugador
     * @param direction Dirección de movimiento del jugador
     * @param player Jugador
     * @return El posible monstruo.
     */
    
    Monster putPlayer(Directions direction, Player player){
        int oldRow = this.getRows();
        int oldCol = this.getCols();
        
        ArrayList<Integer> newPos = dir2Pos(oldRow, oldCol, direction);
        Monster monster = putPlayers2D(oldRow, oldCol, newPos.get(ROW), newPos.get(COL), player);
        return monster;
    }
    
    /**
     * Método que gestiona la creación y adición de un bloque o una fila
     * de éstos en hileras verticales u horizontales en el laberinto
     * @param orientation Orientación de la hilera de bloques
     * @param startRow Fila de inicio de la hilera
     * @param startCol Columna de inicio de la hilera
     * @param length Longitud de la hilera
     */ 
    void addBlock(Orientation orientation, int startRow,int startCol, int length){
        int incRow,incCol;
        if(orientation == Orientation.VERTICAL){
            incRow = 1;
            incCol = 0;
        }else{
            incRow = 0;
            incCol = 1;
        }
        int row = startRow;
        int col = startCol;
        
        while ((posOK(row, col)) && (emptyPos(row,col)) && (length > 0))
        {
            labyrinth[row][col] = BLOCK_CHAR;
            length -= 1;
            row += incRow;
            col += incCol;
        }
     
    }    
    
    /**
     * Método que crea una lista con todos los posibles movimientos válidos que
     * puede tomar un jugador antes de que éste introduzca una dirección de
     * movimiento por teclado
     * @param row Fila
     * @param col Columna
     * @return La lista de movimientos válidos mencionada
     */
    public ArrayList<Directions> validMoves(int row, int col)
    {
        ArrayList<Directions> output = new ArrayList <>();
        
        if(canStepOn(row+1,col)){
            output.add(Directions.DOWN);
        }
        if(canStepOn(row-1,col)){
            output.add(Directions.UP);
        }
        if(canStepOn(row,col+1)){
            output.add(Directions.RIGHT);
        }
        if(canStepOn(row,col-1)){
            output.add(Directions.LEFT);
        }
        return output;
    }
        
        

    
    
    //*****************************************************************
    // Getters 
    //*****************************************************************
    
    /**
     * Row getter
     * @return Número de filas del laberinto
     */
    
    public int getRows()
    {
        return nRows;
    }
    
    /**
     * Column getter
     * @return Número de columnas del laberinto
     */
    
    public int getCols()
    {
        return nCols;
    }
    
    
}
