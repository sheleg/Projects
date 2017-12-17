class City
  attr_reader :region, :name, :url, :active, :restaurants

  def initialize(region, name, url, active)
    @region = region
    @name = name
    @url = url
    @active = active
  end

  def set_rest(restaurants)
    @restaurants = restaurants
  end

end