class Restaurant

  attr_reader :city, :name, :url, :id, :address, :worktime, :telephone,
              :account, :booking, :order, :menu, :images, :logo


  def initialize(city, name, url, id, address, worktime, telephone, account, booking, order, logo)
    @name = name
    @id = id
    @url = url
    @address = address
    @worktime = worktime
    @telephone = telephone
    @city = city
    @account = account
    @booking = booking
    @order = order
    @menu = nil
    @logo = logo
  end


  def set_menu(menu)
    @menu = menu
  end

  def set_images(images)
    @images = images
  end

  def display_menu
    @menu.display
  end

  def display
    puts "#@name(#@url): #@address, #@worktime"
  end
end