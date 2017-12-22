class Menu_item
  attr_reader :section_name, :dishes

  def initialize(section_name, section_dishes)
    @section_name = section_name
    @dishes = section_dishes
  end

  def display
    puts "Section #@section_name consist of " + dishes.size.to_s
  end
end