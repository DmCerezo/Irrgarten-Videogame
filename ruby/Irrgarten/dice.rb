# frozen_string_literal: true
module Irrgarten
  class Dice
    # Atributos de clase privados
    MAX_USES = 5
    MAX_INTELLIGENCE = 10.0
    MAX_STRENGTH = 10.0
    RESURRECT_PROB = 0.3
    WEAPONS_REWARD = 2
    SHIELDS_REWARD = 3
    HEALTH_REWARD = 5
    MAX_ATTACK = 3
    MAX_SHIELD = 2

    # Generador aleatorio
    @generator = Random.new

  end
end
