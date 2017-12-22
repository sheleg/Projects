class TestJsonJob < ApplicationJob
  queue_as :default

  def get_json(data)
    uri = URI('https://carte.by/map/')
    http = Net::HTTP.new(uri.host, uri.port)
    http.use_ssl = true
    req = Net::HTTP::Post.new(uri.path, 'Content-Type' => 'application/x-www-form-urlencoded; charset=UTF-8')
    req.body = { data: data }.to_json
    res = http.request(req)
    puts res.body
    # js = JSON.parse(res.body)['data']['html']
    # js
  end

  def perform(*args)
    data = 'react_modal=map&ajax=map-get&company=1254&city=0&delivery=0&book=1&delivery_append=0&book_append=1&tab=map'
    json = Nokogiri::HTML(get_json(data))
  end
end
