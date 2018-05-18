# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20171217153216) do

  create_table 'categories', force: :cascade do |t|
    t.string 'name'
    t.integer 'restaurant_id'
    t.datetime 'created_at', null: false
    t.datetime 'updated_at', null: false
    t.index ['restaurant_id'], name: 'index_categories_on_restaurant_id'
  end

  create_table 'cities', force: :cascade do |t|
    t.string 'name'
    t.boolean 'active'
    t.integer 'region_id'
    t.datetime 'created_at', null: false
    t.datetime 'updated_at', null: false
    t.index ['region_id'], name: 'index_cities_on_region_id'
  end

  create_table 'dishes', force: :cascade do |t|
    t.string 'name'
    t.string 'weight'
    t.string 'price'
    t.string 'composition'
    t.integer 'category_id'
    t.datetime 'created_at', null: false
    t.datetime 'updated_at', null: false
    t.string 'image'
    t.index ['category_id'], name: 'index_dishes_on_category_id'
  end

  create_table 'regions', force: :cascade do |t|
    t.string 'name'
    t.datetime 'created_at', null: false
    t.datetime 'updated_at', null: false
  end

  create_table 'restaurants', force: :cascade do |t|
    t.string 'name'
    t.string 'address'
    t.string 'worktime'
    t.string 'phone'
    t.string 'average'
    t.boolean 'bookable'
    t.boolean 'orderable'
    t.integer 'city_id'
    t.datetime 'created_at', null: false
    t.datetime 'updated_at', null: false
    t.string 'images'
    t.string 'logo'
    t.string 'lng'
    t.string 'lat'
    t.index ['city_id'], name: 'index_restaurants_on_city_id'
  end

end
