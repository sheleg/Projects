plane <- function(vec){
  i <- 1
  xCoord <- rep(0, 1)
  yCoord <- rep(0, 1)
  
  lambda <- 0
  if (length(vec) %% 2 == 1) {
    lambda <- 1
  }
    
  while(i <= length(vec) - lambda) {
    if((i %% 2) == 1)
      xCoord <- append(xCoord, vec[i])
    else
      yCoord <- append(yCoord, vec[i])
    i <- i + 1
  }
  return(list("x" = xCoord, "y" = yCoord))
}
  
plane1 = plane(random1)
plane2 = plane(random2)
plane3 = plane(random3)
plane6 = plane(random6)
planeRunif = plane(randRunif)


plot(plane1)
plot(plane2)
plot(plane3)
plot(plane6)
plot(planeRunif)