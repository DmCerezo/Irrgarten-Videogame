# frozen_string_literal: true
require_relative 'player'
require_relative 'game_state'
require_relative 'game_character'
require_relative 'directions'
require_relative 'orientation'
require_relative 'labyrinth'
require_relative 'dice'
require_relative 'monster'

module Irrgarten
  class Game
    attr_reader :current_player_index, :log, :current_player, :labyrinth
    @@MAX_ROUNDS = 10

    def initialize(nplayers)
      @players = []
      @monsters = []
      @log = ""

      nplayers.times do |i|
        @players << Player.new(i,Dice.random_intelligence.round(3), Dice.random_strength.round(3))
      end

      @current_player_index = Dice.who_starts(nplayers)
      @current_player = @players[@current_player_index]

    end

    def finished
      @labyrinth.have_a_winner
    end

    def get_game_state
      GameState.new(@labyrinth.to_s, @players.to_s, @monsters.to_s, @current_player_index, finished, log)
    end

    def configure_labyrinth
      tam_total = @labyrinth.rows * @labyrinth.cols
      n_monstruos = tam_total / 5

      # Inicialización y añadido de bloques al laberinto
      @labyrinth.add_block(Orientation::HORIZONTAL, 1,0,2)
      @labyrinth.add_block(Orientation::VERTICAL, 2,1,2)
      @labyrinth.add_block(Orientation::HORIZONTAL, @labyrinth.rows, 0, @labyrinth.cols+1)

      # Inicialización y añadido de monstros al laberinto
      (0..n_monstruos).each do |i|
        monstruo = Monster.new("##{i}", Dice.random_intelligence.round(3), Dice.random_strength.round(3))
        @monsters << monstruo
        @labyrinth.add_monster(Dice.random_pos(@labyrinth.rows), Dice.random_pos(@labyrinth.cols), monstruo)
      end

    end

    def next_player
      @current_player_index = (@current_player_index + 1) % @players.size
      @current_player = @players[@current_player_index]
    end

  end
end