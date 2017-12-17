class CreateChiefs < ActiveRecord::Migration[5.1]
  def change
    create_table :chiefs do |t|
      t.string :name

      t.timestamps
    end
  end
end
