class DropChiefsTable < ActiveRecord::Migration[5.1]
  def up
  drop_table :chiefs
  end

  def down
    raise ActiveRecord::IrreversibleMigration
  end
end
