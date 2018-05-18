source("functions.R")
n = 10000
pi = 0.4
alpha = 0.9
betta = 0.9

get_error_plot(pi, alpha, betta)
get_entropy_plot(n, pi)

en = get_entropy_pair(n, pi, alpha, betta)
# cat("en = ", en["entropy"], "\nrel_en = ", en["relative_entropy"], "\nplus_en = ", en["plus_entropy"], "\ncircle_plus_entropy = ", en["circle_plus_entropy"])
print("en = ")
print(en["entropy"])

print("rel_en = ")
print(en["relative_entropy"])

print("plus_en = ")
print(en["plus_entropy"])

print("rel_plus_en = ")
print(en["rel_plus_entropy"])

print("circle_plus_en = ")
print(en["circle_plus_entropy"])

print("rel_circle_plus_en = ")
print(en["rel_circle_plus_entropy"])

# get_entropy_for_circle_plus(first_sequence, second_sequence, pi, alpha, betta)
# get_entropy_for_plus(first_sequence, second_sequence, pi, alpha, betta)
# 
# conditional_entropy = get_conditional_entropy(pi, alpha, betta)
# relative_conditional_entropy = get_conditional_entropy(relative_pi, relative_alpha, relative_betta)
# 
# cat(conditional_entropy, " ", relative_conditional_entropy)