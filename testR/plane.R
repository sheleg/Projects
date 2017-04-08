plane <- function(arr){
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