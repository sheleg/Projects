require 'nokogiri'
require 'open-uri'
require 'csv'

def get_all_items_page(link)
  page = Nokogiri::HTML(open(link))

  link += '?'
  xpath = '//*[@id="pagination_bottom"]/form/div/input[1]'
  link = get_attribute_for_link(page, link, xpath) + '&'
  xpath = '//*[@id="pagination_bottom"]/form/div/input[2]'
  link = get_attribute_for_link(page, link, xpath) + '&'
  xpath = '//*[@id="pagination_bottom"]/form/div/input[3]'
  link = get_attribute_for_link(page, link, xpath)

  Nokogiri::HTML(open(link))
end

def get_attribute_for_link(page, link, xpath)
  name = page.xpath(xpath + '/@name')
  value = page.xpath(xpath + '/@value')
  link += name.text + '=' + value.text
  link
end


category_link = ARGV[0]
output_csv = ARGV[1]
category_page = get_all_items_page(category_link)

category_page.xpath('//a[@class="product_img_link"]').each do |item_info|

  item_attributes = []
  item_link = item_info.xpath('@href').text
  item_name = item_info.xpath('@title').text

  item_page = Nokogiri::HTML(open(item_link))
  item_img = item_page.xpath('//a[@class="jqzoom"]/@href')
  item_page.xpath('//ul[@class="attribute_labels_lists"]//span[@class="attribute_name" or @class="attribute_price"]').each do |item_data|
    item_attributes.push(item_data.text.strip)
  end
  CSV.open(output_csv, 'a') do |csv_out|
    item_attributes.each_slice(2) do |count, price|
      csv_out <<  ["#{item_name} - #{count}", price, item_img]
    end
  end
  item_attributes.clear
end



