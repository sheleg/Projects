class AddStuffJob < ApplicationJob
  queue_as :default

  def perform(*args)
    Chief.create(name: 'Chief 2').employees.create({name: 'Pasha', position: 'junior'})
  end
end
