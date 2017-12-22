class Category < ApplicationRecord
  belongs_to :restaurant
  has_many :dishes


  def category_params
    params.require(:category).permit(:name)
  end
end
