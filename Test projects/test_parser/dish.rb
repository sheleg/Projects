class Dish

  attr_reader :name, :weight, :price, :composition, :image

  def initialize(name, price, weight, composition, image)
    @name = name
    @composition = composition
    @price = price
    @weight = weight
    @image = image
  end

  def display
    puts "Dish #@name: #@price\n#@composition"
  end
end