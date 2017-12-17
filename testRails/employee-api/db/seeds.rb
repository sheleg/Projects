# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)


# Chief.create(name: 'Chief').employees.create({name: 'Vlada', position: 'junior'})
Chief.find_by(name: 'Chief').employees.create({name: 'Sasha', position: 'junior'})
c = Chief.new(name: 'VALERA')
e = Employee.new({name: 'Dasha', position: 'middle'})
c.employees.push(e)

c.save
