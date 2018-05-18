class CategoriesController < ApplicationController
  def index
    categories = Category.where(restaurant_id: params[:restaurant_id])
    render :json => categories.to_json(:except => [:created_at, :updated_at])
  end

  def show
    category = Category.find(params[:id])
    render :json => category.to_json(:except => [:created_at, :updated_at])
  end

  def all
    dishes = []
    categories = Category.where(restaurant_id: params[:restaurant_id])
    categories.each do |category|
      dishes.push category.dishes
    end
    render :json => dishes.to_json(:except => [:created_at, :updated_at])
  end
end
