class RestaurantsController < ApplicationController
  def index
    restaurants = Restaurant.where(city_id: params[:city_id])
    # render json: {data: params}, status: :ok
    render :json => restaurants.to_json(:except => [:created_at, :updated_at])
  end

  def show
    restaurant = Restaurant.find(params[:id])
    render :json => restaurant.to_json(:except => [:created_at, :updated_at])
  end
end
