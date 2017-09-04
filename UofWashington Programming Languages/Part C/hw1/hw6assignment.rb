# University of Washington, Programming Languages, Homework 6, hw6runner.rb

# This is the only file you turn in, so do not modify the other files as
# part of your solution.

class MyPiece < Piece
  # The constant All_My_Pieces should be declared here
  def initialize(point_array, board)
    super(point_array, board)
  end

  attr_reader :Cheat_Block

  # your enhancements here

  def self.next_piece (board)
    MyPiece.new(All_My_Pieces.sample, board)
  end

  My_Cheat_Block = [[[0, 0]]]

  All_My_Pieces  = [[[[0, 0], [1, 0], [0, 1], [1, 1]]],  # square (only needs one)
  rotations([[0, 0], [-1, 0], [1, 0], [0, -1]]), # T
  [[[0, 0], [-1, 0], [1, 0], [2, 0]], # long (only needs two)
  [[0, 0], [0, -1], [0, 1], [0, 2]]],
  rotations([[0, 0], [0, -1], [0, 1], [1, 1]]), # L
  rotations([[0, 0], [0, -1], [0, 1], [-1, 1]]), # inverted L
  rotations([[0, 0], [-1, 0], [0, -1], [1, -1]]), # S
  rotations([[0, 0], [1, 0], [0, -1], [-1, -1]]), # Z
  [[[0, 0], [-1, 0], [1, 0], [2, 0], [3, 0]], # longlong (new addition)
  [[0, 0], [0, -1], [0, 1], [0, 2], [0, 3]]],
  rotations([[0, 0], [0, 1], [0, 2], [1, 0], [1, 1]]),
  rotations([[0, 0], [0, 1], [1, 0]])]
end

class MyBoard < Board
  # your enhancements here
  def initialize game
    super game
    @current_block = MyPiece.next_piece(self)
    @cheat = false
  end

  attr_accessor :cheat

  def rotate_180
    if !game_over? and @game.is_running?
      @current_block.move(0, 0, 2)
    end
    draw
  end

  def next_piece
    if @cheat && @score >= 100      
      @current_block = MyPiece.new(MyPiece::My_Cheat_Block, self)
      @score -= 100
    else
      @current_block = MyPiece.next_piece(self)
    end

    @current_pos = nil
    @cheat = false
  end

  # gets the information from the current piece about where it is and uses this
  # to store the piece on the board itself.  Then calls remove_filled.
  def store_current
    locations = @current_block.current_rotation
    displacement = @current_block.position
    (0..@current_block.current_rotation.length - 1).each{|index| 
      current = locations[index];
      @grid[current[1]+displacement[1]][current[0]+displacement[0]] = 
      @current_pos[index]
    }
    remove_filled
    @delay = [@delay - 2, 80].max
  end
end

class MyTetris < Tetris
  # your enhancements here
  def initialize
    super
    @root.bind('u', proc {@board.rotate_180})
    @root.bind('c', proc {@board.cheat = true})
  end

  def set_board
    @canvas = TetrisCanvas.new
    @board = MyBoard.new(self)
    @canvas.place(@board.block_size * @board.num_rows + 3,
                  @board.block_size * @board.num_columns + 6, 24, 80)
    @board.draw
  end 
end


