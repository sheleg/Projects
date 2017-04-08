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
