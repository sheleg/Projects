class AddLocation < ActiveRecord::Migration[5.1]
  def change
    add_column :restaurants, :lng, :string
    add_column :restaurants, :lat, :string
  end
end
