require_relative 'directions'
require_relative 'weapon'
require_relative 'game_state'
require_relative 'dice'
require_relative 'player'
require_relative 'labyrinth'
require_relative 'player_square'

module Irrgarten
  class TestP1

    def main

      lab = Labyrinth.new(2,3,7,9)


      dir= Directions::DOWN
      puts "El enumerado es: "+ dir.to_s

      weapon = Weapon.new(0, 0)
      weapon.to_s
      x = weapon
      puts"X contiene: " +x.to_s

      for i in 1..10
        puts "Inteligencia: " + Dice.random_intelligence.to_s
      end

      for i in 1..10
        puts "Inteligencia: " + Dice.uses_left.to_s
      end
      # 1. Crear instancias de la clase GameState y probar sus métodos


    end


  end
  TestP1.new.main
end