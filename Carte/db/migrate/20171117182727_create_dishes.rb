class CreateDishes < ActiveRecord::Migration[5.1]
  def change
    create_table :dishes do |t|
      t.string :name
      t.string :weight
      t.string :price
      t.string :composition
      t.references :category, foreign_key: true

      t.timestamps
    end
  end
end
