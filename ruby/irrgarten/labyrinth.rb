# frozen_string_literal: true
require_relative 'monster'
require_relative 'player'
require_relative 'dice'
require_relative 'shield'
require_relative 'weapon'
require_relative 'directions'
require_relative 'orientation'

module Irrigarten
  class Labyrinth
    attr_reader :n_rows, :n_cols
    @@BLOCK_CHAR = 'X'
    @@EMPTY_CHAR = '-'
    @@MONSTER_CHAR = 'M'
    @@COMBAT_CHAR = 'C'
    @@EXIT_CHAR = 'E'
    @@ROW = 0
    @@COL = 1

    def initialize(nRows, nCols, exitRow, exitCol)
      @nRows = nRows
      @nCols = nCols
      @exitRow = exitRow
      @exitCol = exitCol

      @monsters = Array.new(@nRows){Array.new(@nCols, nil)}
      @players = Array.new(@nRows){Array.new(@nCols, nil)}
      @labyrinth = Array.new(@nRows){Array.new(@nCols, @@EMPTY_CHAR)}

      @labyrinth[@exitRow][@exitCol] = @@EXIT_CHAR

    end

    def have_a_winner()
      @players[@exitRow][@exitCol] != nil
    end

    def to_s
      estado = ""

      (0...@n_rows).each do |i|
        (0...@n_cols).each do |j|
          estado += " [#{@labyrinth[i][j]}]"
        end
        estado += "\n"
      end

      estado += "Tamaño del laberinto: #{@n_rows}x#{@n_cols}\n"
      estado += "Casilla de salida: [#{@exit_row},#{@exit_col}]\n"
      estado
    end

    def add_monster(row, col, monster)
      if pos_ok(row, col)
        if empty_pos(row, col)

          @labyrinth[row][col] = @@MONSTER_CHAR
          @monsters[row][col] = monster

          if @labyrinth[row][col] == @@BLOCK_CHAR
            monster.set_pos(row,col)
          end
        end

      end
    end

    def pos_ok(row,col)
      (row >= 0) && (row <= @nRows) && (col >= 0) && (col <= @nCols)
    end
    def empty_pos(row, col)
      !(monster_pos(row,col)) || exit_pos(row,col) || combat_pos(row,col)
    end

    def monster_pos(row, col)
      @labyrinth[row][col] == @@MONSTER_CHAR
    end

    def exit_pos(row, col)
      @labyrinth[row][col] == @@EXIT_CHAR
    end

    def combat_pos(row, col)
      @labyrinth[row][col] == @@COMBAT_CHAR
    end

    def can_step_on(row, col)
      if @labyrinth[row][col] != @@BLOCK_CHAR
        pos_ok(row,col) and ((empty_pos(row,col) or monster_pos(row,col) or exit_pos(row, col)))
      end
    end

    def update_old_pos(row, col)

      if pos_ok(row,col)
        if @labyrinth[row][col] == @@COMBAT_CHAR
          @labyrinth[row][col] = @@MONSTER_CHAR
        else
          @labyrinth[row][col] = @@EMPTY_CHAR
        end
      end
    end

    def dir_2_pos(row, col, direction)

      new_row=row
      new_col=col

      case direction
      when Directions::UP
        new_row=new_row-1
      when Directions::DOWN
        new_row=new_row+1
      when Directions::LEFT
        new_col=new_col-1
      when Directions::RIGHT
        new_col=new_col+1
      end
      [new_row, new_col]

    end

    def random_empty_pos
      resultado = Array.new

      row = Dice.random_pos(@n_rows)
      col = Dice.random_pos(@n_cols)

      loop do
        if pos_ok(row,col)
          resultado[@@ROW] = row
          resultado[@@COL] = col
        end

        if empty_pos(resultado[@@ROW], resultado[@@COL]) && @labyrinth[@@ROW, @@COL] != @@BLOCK_CHAR
          break
        end
      end

      resultado
    end


  end
end