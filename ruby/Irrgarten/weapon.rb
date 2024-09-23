module Irrgarten
  class Weapon
    def initialize(power, uses)
      @power = power
      @uses = uses
    end
    def attack
      if
      @uses > 0
        @uses -=1
        return @power
      end
      0
    end
  end
end
