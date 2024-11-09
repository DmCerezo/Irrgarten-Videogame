module Irrgarten
  class GameState
    # Atributos de instancia privados
    def initialize(labyrinth, players, monsters, current_player, winner, log)
      @labyrinth = labyrinth     # Representa el laberinto del juego
      @players = players         # Representa los jugadores
      @monsters = monsters       # Representa los monstruos
      @current_player = current_player # Índice del jugador actual
      @winner = winner           # Indica si hay un ganador
      @log = log                 # Registro de eventos del juego
    end

    # Métodos consultores (getters)

    def get_labyrinth
      @labyrinth
    end

    def get_players
      @players
    end

    def get_monsters
      @monsters
    end

    def get_current_player
      @current_player
    end

    def get_winner
      @winner
    end

    def get_log
      @log
    end

  end
end