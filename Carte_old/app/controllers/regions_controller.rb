class RegionsController < ApplicationController
  def index
    regions = Region.all
    render :json => regions.to_json(:except => [:created_at, :updated_at])
  end

  def show
    region = Region.find(params[:id])
    render :json => region.to_json(:except => [:created_at, :updated_at])
  end
end
