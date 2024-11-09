module Irrgarten
  class Dice
    # Atributos de clase privados (constantes)
    @@MAX_USES = 5
    @@MAX_INTELLIGENCE = 10.0 #
    @@MAX_STRENGTH = 10.0
    @@RESURRECT_PROB = 0.3
    @@WEAPONS_REWARD = 2
    @@SHIELDS_REWARD = 3
    @@HEALTH_REWARD = 5
    @@MAX_ATTACK = 3
    @@MAX_SHIELD = 2

    # Generador aleatorio
    @@generator = Random.new


      # Devuelve un número entre 0 y max (número de columnas o filas) - 1
      def self.random_pos(max)
        @@generator.rand(0...max)
      end

      # Devuelve un número entre 0 y el número de jugadores - 1
      def self.who_starts(nplayers)
        @@generator.rand(0...nplayers)
      end

      # Genera un número entre 0 y MAX_INTELLIGENCE
      def self.random_intelligence
        @@generator.rand(0.0..@@MAX_INTELLIGENCE)
      end

      # Genera un número entre 0 y MAX_STRENGTH
      def self.random_strength
        @@generator.rand(0.0 .. @@MAX_STRENGTH)
      end

      # Devuelve true con probabilidad RESURRECT_PROB
      def self.resurrect_player
        @@generator.rand < @@RESURRECT_PROB
      end

      # Genera un número entre 0 y WEAPONS_REWARD
      def self.weapons_reward
        @@generator.rand(0 .. @@WEAPONS_REWARD)
      end

      # Genera un número entre 0 y SHIELDS_REWARD
      def self.shield_reward
        @@generator.rand(0 .. @@SHIELDS_REWARD)
      end

      # Genera un número entre 0 y MAX_ATTACK
      def self.weapon_power
        @@generator.rand * @@MAX_ATTACK  # Multiplica por MAX_ATTACK para obtener un número flotante
      end

      # Genera un número entre 0 y MAX_SHIELD
      def self.shield_power
        @@generator.rand * @@MAX_SHIELD  # Multiplica por MAX_SHIELD para obtener un número flotante
      end

      # Genera un número entre 0 y MAX_USES
      def self.uses_left
        @@generator.rand(0 .. @@MAX_USES)
      end

      # Genera un número entre 0 y la competencia (competence)
      def self.intensity(competence)
        @@generator.rand(0.0 .. competence)
      end

      # Método que devuelve true con probabilidad inversa a los usos restantes
      def self.discard_element(uses_left)
        return false if uses_left >= @@MAX_USES # Si los usos restantes son máximos, no se descarta
        return true if uses_left <= 0         # Si no quedan usos, siempre se descarta

        # Calculamos la probabilidad inversa
        discard_probability = 1.0 - uses_left.to_f / @@MAX_USES

        # Comparamos la probabilidad con un número aleatorio entre 0 y 1
        @@generator.rand < discard_probability
      end
    end
end