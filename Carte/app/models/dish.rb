class Dish < ApplicationRecord
  belongs_to :category

  def dish_params
    params.require(:dish).permit(:name, :weight, :price, :composition, :image)
  end
end
