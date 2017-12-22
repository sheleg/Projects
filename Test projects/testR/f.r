randomFunction <- function(m, a, c, max, n){
  s = rep(0, n)
  i = 1
  x0 = sample(1:m, 1)
  x1 = (a*x0 + c) %% m
  while(i <= n){
    s[i] = x1 %% max
    x1 = (a*x1 + c) %% m
    i = i + 1
  }
  return (s)
}



check <- function(arr){
  i = 1
  x = rep(0, 1)
  y = rep(0, 1)
  while(i <= length(arr)){
    ifelse((i%%2) == 0, 
           y = append(y, arr[i]), 
           x = append(x, arr[i]))
    i = i + 1
  }
  s <- list("x"=x, "y"=y)
  return(s)
}