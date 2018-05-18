class CenerateJob < ApplicationJob
  queue_as :default

  def perform
    restaurants = Restaurant.all
    restaurants.each do |rest|
      puts rest.id
      addrHash = ""

      puts rest.address
      unless rest.address.empty?
        # binding.pry
        if rest.address.include? "lng" or rest.address.include? "lat"
            addrHash = eval(rest.address)
            if addrHash["lng"] != nil
              rest.lng = addrHash["lng"]
              end

            if addrHash["lat"] != nil
              rest.lng = addrHash["lat"]
            end

            rest.address = addrHash["address_name"]
            rest.save
        end
      end
    end
  end
end
