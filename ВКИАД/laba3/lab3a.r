rand <- function(m, a, c, max, n){
  res <- rep(0, 1)
  temp <- sample(1:m, 1)
  
  res[1] <- ((a*temp + c) %% m) %% max
  
  i <- 2
  while (i <= n) {
    res[i] <- ((a*res[i - 1] + c) %% m) %% max
    
    for(j in 1:(i - 1)) {
      if (res[j] == res[i]) { 
        return (res);
      }
    }
    
    i <- i + 1
  }
  return (res)
}

                #max - 4
random1 <- rand(7, 2, 4, 7, 1000)
random2 <- rand(125, 6, 17, 125, 1000)
random3 <- rand(2^15 - 1, 1664525, 1013904223, 1000, 1000)
#random4 <- rand(2^31 - 1, 48271, 0, 10000, 1000)
#random5 <- rand(2^32 - 1, 253801, 14519, 10000, 1000)
random6 <- rand(2^20, 3453, 2324323, 4000, 1000)

hist1 <- hist(random1, 25)
hist2 <- hist(random2, 25)
hist3 <- hist(random3, 25)
#hist4 <- hist(random4, 25)
#hist5 <- hist(random5, 25)
hist6 <- hist(random6, 25)

var1 <- var(random1)
var2 <- var(random2)
var3 <- var(random3)
#var4 <- var(random4)
#var5 <- var(random5)
var6 <- var(random6)

mean1 <- mean(random1)
mean2 <- mean(random2)
mean3 <- mean(random3)
#mean4 <- mean(random4)
#mean5 <- mean(random5)
mean6 <- mean(random6)