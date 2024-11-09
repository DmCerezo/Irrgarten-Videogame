# frozen_string_literal: true
module Irrgarten
  class Player
    @@MAX_WEAPONS = 2
    @@MAX_SHIELDS = 3
    @@INITIAL_HEALTH = 10
    @@HITS2LOSE = 3

    def initialize(number, intelligence, strength)
      @number = number
      @name = "Player #" + @number
      @intelligence = intelligence
      @strength = strength
      @health = @@INITIAL_HEALTH
      @row = -1
      @col = -1
      @weapons = []
      @shields = []
    end
    private def sum_weapons()
      sum = 0
      for w in @weapons
        sum += w.attack
      end
      sum
    end
  end
end