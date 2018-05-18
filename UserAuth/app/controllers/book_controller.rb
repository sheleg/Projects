class BookController < ApplicationController
  before_action :authenticate_user!

  def index
    books = Book.all
    render :json => books.to_json(:except => [:created_at, :updated_at])
  end

  def show
  end
end
