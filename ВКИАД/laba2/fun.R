f <- function(y, x) {
  x_med = sum(x)/length(x)
  y_med = sum(y)/length(y)
  xsq_med = sum(x*x)/length(x)
  xy_med = sum(x*y)/length(x)
  table_A = matrix(c(1, x_med, x_med, xsq_med), ncol = 2)
  vector_b = matrix(c(y_med, xy_med), ncol = 1)
  res = solve(table_A, vector_b)
  return(res)
}