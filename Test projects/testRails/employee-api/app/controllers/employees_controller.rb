class EmployeesController < ApplicationController

  def index
    employees = Employee.where(chief_id: params[:chief_id])
    # employees = Employee.all
    render json: {data: employees}, status: :ok
  end

  def show
    employee = Employee.find(params[:id])
    render json: {data: params}, status: :ok
  end
end
