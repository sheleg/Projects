class CitiesController < ApplicationController
  def index
    cities = City.where(region_id: params[:region_id])
    render :json => cities.to_json(:except => [:created_at, :updated_at])
  end

  def show
    city = City.find(params[:id])
    # j = city.as_json(:except => [:created_at, :updated_at])
    # render :plain => city.as_json
    render :json => city.to_json(:except => [:created_at, :updated_at])
  end

end
