require 'httparty'
require 'nokogiri'
require 'open-uri'

page = HTTParty.get("https://newyork.craigslist.org/search/pet?s=0")
doc = Nokogiri::HTML(page)

pets_array = []

tem = doc.css('.content').css('row').css('hdrlnk').map do |a|
  post_name = a.text
  pets_array.push(post_name)
end

puts pets_array
