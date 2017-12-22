class City < ApplicationRecord
  belongs_to :region
  has_many :restaurants

  def city_params
    params.require(:city).permit(:name, :active)
  end

  # def as_json
  #   # { :name => self.name}.delete('\\')
  #   { :name => String(self.name)}
  # end
end
