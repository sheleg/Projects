class ChiefsController < ApplicationController
  def index
    chief = Chief.all
    render json: {data: chief}, status: :ok
  end

  def show
    chief = Chief.find(params[:id])
    render json: {data: chief}, status: :ok
  end
end
