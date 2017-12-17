class Menu
  attr_reader :menu

  def initialize(menu)
    @menu = menu
  end

  def display
    puts "Menu consist of " + menu.size.to_s + " sections"
  end
end