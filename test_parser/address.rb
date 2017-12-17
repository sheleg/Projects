class Address
  attr_reader :street, :lat, :lng

  def initialize(street, lat, lng)
    @street = street
    @lat = lat
    @lng = lng
  end

  def display
    puts "#@street: #@lat - #@lng"
  end
end