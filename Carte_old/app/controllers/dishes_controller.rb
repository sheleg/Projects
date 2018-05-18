class DishesController < ApplicationController
  def index
    dishes = Dish.where(category_id: params[:category_id])
    render :json => dishes.to_json(:except => [:created_at, :updated_at])
  end

  def show
    dish = Dish.find(params[:id])
    render :json => dish.to_json(:except => [:created_at, :updated_at])
  end
end
