class TestTableName < ActiveRecord::Migration[5.1]
  def change
    create_table :regions do |c|
      c.string 'name'
      c.integer 'restaurant_id'
    end
  end
end
