entropy <- function(freq)
{
  -sum(freq * log2(freq))
}

get_variables_vector <- function(nn, m, n, k)
{
  rhyper(nn, m, n, k)
}

get_p_vector <- function(m, n, k, max_value)
{
  x = 0
  for (i in 0:max_value) x[i+1]=dhyper(i, m, n, k)
  x
}

get_p_relative_vector <- function(x, nn) 
{
  table(x)/nn
}

get_plot <- function(m, n, k)
{
  plot_arg = matrix(0, nrow = 2, ncol = 5)
  size = c(100, 1000, 2000, 5000, 10000)
  error = 0
  i = 1
  for (s in size)
  {
    x = get_variables_vector(s, m, n, k)
    p_relative = get_p_relative_vector(x, s)
    p = get_p_vector(m, n, k, max(x))
    en_relative = entropy(p_relative)
    en = entropy(p)
    
    error[i] = (en_relative - en) * (en_relative - en)
    i = i + 1
  }
  plot(size, error, type = 'b', xlab = "Length", ylab = "Error")
}
