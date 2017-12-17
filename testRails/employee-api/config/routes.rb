Rails.application.routes.draw do
  get 'chief/index'

  resources :chiefs do
    resources :employees
  end
end


# Rails.application.routes.draw do
#   get 'employee/index'
#
#
#   resources :employee
#
# end
