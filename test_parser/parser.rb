class CenerateJob < ApplicationJob
  queue_as :default

  def perform
    page_rest = Nokogiri::HTML(HTTParty.get('https://carte.by/'))

    data_for_restaurants = []
    images = []
    menu_items = []
    section_dishes = []
    restaurants = []
    File.open("/Users/vladasheleg/Documents/Git/Projects/test_parser/data.txt", "r") do |file|
      file.each_line do |line|
        j = JSON.parse line.gsub('=>', ':')
        data_for_restaurants.push j
      end
    end

    #GET CITIES

    # _____________________________________________________________________

    regions = Hash.new{|hsh,key| hsh[key] = [] }
    page_rest.css(".vr-region").each do |region|
      region_name = region.css(".vr-region-name").text.strip
      region_model = Region.new({name: region_name})

      region.css(".vr-region-cities a").each do |city|
        href = city["href"]
        name = city.text

        regions[region_model].push [City.new({name: name, active: 1}), href]
      end
      region.css(".vr-region-cities span").each do |city|
        href = nil
        name = city.text

        regions[region_model].push [City.new({name: name, active: 0}), href]
      end
    end
    # _____________________________________________________________________


    #GET RESTAURANT FOR EACH CITY
    # _____________________________________________________________________
    regions.each do |key, values|
      values.select {|data| data[0]["active"] == true}.each do |city, url|
        page_rest = Nokogiri::HTML(HTTParty.get(url))

        page_rest.css(".stcomp-item").each do |show|
          name = show.css(".stcomp-name").text.strip
          id = show["data-id"]
          rest_url = show["data-url"]
          address_name = show.css(".stcomp-map-span").text
          account = show.css(".stcomp-receipt").text
          order = show.css(".stcomp-delivery-span").empty? ? 0 : 1
          book = show.css(".stcomp-book-span").empty? ? 0 : 1
          logo = ""
          show.css(".stcomp-logo-desktop img").each do |src|
            logo = src["data-lazy-src"].to_s.empty? ? src["src"] : src["data-lazy-src"]
          end
          lat = ""
          lng = ""
          address = ""
          worktime = ""
          phones = ""
          data_for_restaurants.select {|hs| hs["id"] == id.to_s }.each do |r|
            lat = r["lat"]
            lng = r["lng"]
            address = r["address"]
            worktime = r["worktime"]
            phones = r["phones"]
          end

          #__________________________________________________________________

          page = Nokogiri::HTML(HTTParty.get(rest_url))

          page.css(".gallery-item img").each do |item|
            i = item["src"]
            images.push(i)
          end

          page.css(".menu-section").each do |section|

            section_name = section.css(".menu-section-name").text.strip

            section.css(".menu-dish").each do |dish|
              dish_name = dish.css(".menu-dish-name").text.strip
              dish_price = dish.css(".menu-dish-price").text.strip
              dish_weight = dish.css(".menu-dish-weight").text.strip
              dish_composition = dish.css(".menu-dish-ingredients").text.strip
              dish_image = ""
              dish.css(".menu-dish-photo-desktop img").each do |p|
                dish_image =  p["data-source"]
              end
              section_dishes.push(Dish.new({name: dish_name,
                                            weight: dish_weight, price: dish_price, composition: dish_composition, image: dish_image}))
            end
            c = Category.new({name: section_name})
            c.dishes.push(section_dishes)
            menu_items.push(c)#.dishes.push(section_dishes))
            section_dishes = []
          end
          rest = Restaurant.new({name: name, address: address, worktime: worktime, phone: phones, average: account, bookable: book, orderable: order, logo: logo, images: images})
          rest.categories.push(menu_items)
          puts rest.to_s
          restaurants.push(rest)
          images = []
          menu_items = []
        end
        binding.pry
        # restaurants.each do |rest|
        #   rest.display_menu
        # end
      end
    end
  end
end
