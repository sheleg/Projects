source("functions.R")
m = 50
n = 40
k = 20
nn = 10000

get_plot(m, n, k)
x = get_variables_vector(nn, m, n, k)
hist(x, xlab = "X", ylab = "Frequency")
p_relative = get_p_relative_vector(x, nn)
p = get_p_vector(m, n, k, max(x))

print(entropy(p))
print(entropy(p_relative))
