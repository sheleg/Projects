table = read.table("data")
library(cluster)
cll <- clara(table,8)
plot(table, col = cll$clustering, xlab = "x", ylab = "y")

