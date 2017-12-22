Rails.application.routes.draw do
  get 'dishes/index'

  get 'dishes/show'

  get 'categories/index'

  get 'categories/show'

  get 'cities/index'

  get 'cities/show'

  get 'regions/index'

  get 'regions/:region_id/cities/:city_id/restaurants/:restaurant_id/dishes', to: 'categories#all'

  resources :regions do
    resources :cities do
      resources :restaurants do
        resources :categories do
          resources :dishes
        end
      end
    end
  end
end
