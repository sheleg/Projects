class CreateRestaurants < ActiveRecord::Migration[5.1]
  def change
    create_table :restaurants do |t|
      t.string :name
      t.string :address
      t.string :worktime
      t.string :phone, limit: 17
      t.string :average, limit: 10
      t.boolean :bookable
      t.boolean :orderable
      t.references :city, foreign_key: true

      t.timestamps
    end
  end
end
