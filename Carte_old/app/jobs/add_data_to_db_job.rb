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

          lat = ""
          lng = ""
          address = ""

          data_for_restaurants.select {|hs| hs["id"] == id.to_s }.each do |r|
            lat = r["lat"]
            lng = r["lng"]
            address = r["address"]
          end

          #__________________________________________________________________
          rest = Restaurant.find_by_name(name)
          binding.pry
        end
      end
    end

    regions.each do |region, data|
      binding.pry
      puts region.save
    end
  end
end
