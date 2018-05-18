class Restaurant < ApplicationRecord
  belongs_to :city
  has_many :categories

  def restaurant_params
    params.require(:restaurant).permit(:name, :address, :worktime, :phone, :average,
                                       :bookable, :orderable, :images, :logo)
  end
end




