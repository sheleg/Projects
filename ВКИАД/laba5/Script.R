install.packages("arules")
library(arules)

transaction = read.transactions("data", format = "basket", sep = ",")

inspect(transaction)
image(transaction)
itemFrequencyPlot(transaction, support = 0.1)


rules <- apriori(transaction, parameter= list(supp=0.5, conf=0.5))
