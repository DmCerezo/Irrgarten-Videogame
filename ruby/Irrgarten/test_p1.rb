require_relative 'directions'
require_relative 'weapon'

module Irrgarten
  class TestP1

    def main
      dir= Directions::DOWN
      puts "El enumerado es: "+ dir.to_s

      weapon = Weapon.new(0, 0)
      weapon.to_s
      x = weapon
      puts"X contiene: " +x.to_s
    end


  end
  TestP1.new.main
end