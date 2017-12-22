class Region < ApplicationRecord
  has_many :cities

  def region_params
    params.require(:region).permit(:name)
  end
end
