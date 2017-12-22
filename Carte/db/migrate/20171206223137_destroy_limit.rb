class DestroyLimit < ActiveRecord::Migration[5.1]
  def change
    change_column :restaurants, :phone, :string, :limit => nil
    change_column :restaurants, :average, :string, :limit => nil
  end
end
