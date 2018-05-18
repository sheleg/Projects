get_variables_first_vector <- function(n, pi)
{
  rbinom(n, 1, pi)
}

get_relative_vector <- function(variables_first, alpha, betta)
{
  variables_second = vector(mode="numeric", length=0)
  count_for_pi = 0
  count_for_alpha = 0
  count_for_betta = 0
  for (var in variables_first) {
    if (var == 0) {
      temp_var = rbinom(1, 1, alpha)
      if (temp_var == 0) {
        count_for_alpha = count_for_alpha + 1
      }
      variables_second = c(variables_second, temp_var) 
    }
    else {
      count_for_pi = count_for_pi + 1
      temp_var = rbinom(1, 1, betta)
      if (temp_var == 1) {
        count_for_betta = count_for_betta + 1
      }
      variables_second = c(variables_second, temp_var)
    }
  }
  list(v2 = variables_second, relative_pi = count_for_pi/length(variables_first), relative_alpha = count_for_alpha/(length(variables_first)-count_for_pi), relative_betta = count_for_betta/count_for_pi)
}

get_conditional_entropy <- function(p, a, b) {
  -((1 - p)*(a*log2(a) + (1 - a)*log2(1 - a)) + p*(b*log2(b) + (1 - b)*log2(1 - b)))
}

get_error_plot <- function(pi, alpha, betta) {
  size = c(100, 1000, 2000, 5000, 10000)
  error = 0
  i = 1
  m = 1000
  for (s in size) {
    sum_entropy = 0
    for (k in 1:m) {
      entropy_list = get_entropy_pair(s, pi, alpha, betta)
      
      conditional_entropy = as.numeric(entropy_list["entropy"])
      relative_conditional_entropy = as.numeric(entropy_list["relative_entropy"])
      
      sum_entropy = sum_entropy + (conditional_entropy - relative_conditional_entropy)*(conditional_entropy - relative_conditional_entropy)
    }
    
    error[i] = sum_entropy/m
    i = i + 1
  }
  plot(size, error, type = 'b', xlab = "Length", ylab = "Error")
}

get_entropy_plot <- function(n, pi) {
  alpha_vec = c(0.1, 0.2, 0.3, 0.4, 0.5)
  betta_vec = c(0.1, 0.2, 0.3, 0.4, 0.5)
  
  vec_difference = c()
  entropy = c()
  relative_entropy = c()
  
  for (b in betta_vec) {
    for (a in alpha_vec) {
      entropy_list = get_entropy_pair(n, pi, a, b)
      vec_difference = c(vec_difference, 1 - a - b)
      entropy = c(entropy, as.numeric(entropy_list["entropy"]))
      relative_entropy = c(relative_entropy, as.numeric(entropy_list["relative_entropy"]))
    }
  }
  
  plot(vec_difference, entropy, col = "red", xlab = "1-alpha-betta")
  points(vec_difference, relative_entropy, col="blue")
}

get_entropy_pair <- function (n, pi, alpha, betta) {
  first_sequence = get_variables_first_vector(n, pi)
  relative_vector = get_relative_vector(first_sequence, alpha, betta)
  
  second_sequence = unlist(relative_vector["v2"])
  relative_pi = as.numeric(relative_vector["relative_pi"])
  relative_alpha = as.numeric(relative_vector["relative_alpha"])
  relative_betta = as.numeric(relative_vector["relative_betta"])
  
  plus_en = get_entropy_for_plus(first_sequence, second_sequence, pi, alpha, betta)
  circle_plus_en = get_entropy_for_circle_plus(first_sequence, second_sequence, pi, alpha, betta)
  
  list (entropy = get_conditional_entropy(pi, alpha, betta),
        relative_entropy = get_conditional_entropy(relative_pi, relative_alpha, relative_betta),
        plus_entropy = unlist(plus_en["entropy"]),
        rel_plus_entropy = unlist(plus_en["relative_entropy"]),
        circle_plus_entropy = unlist(circle_plus_en["entropy"]),
        rel_circle_plus_entropy = unlist(circle_plus_en["relative_entropy"]))
}

get_entropy_for_plus <- function(variables_first, variables_second, pi, alpha, betta) {
  plus_sequence = as.numeric(variables_first) + as.numeric(variables_second)
  rel_posibilities = get_relative_posibilities_for_plus(plus_sequence, variables_second)

  posibilities = matrix(c(1, pi*(1 - betta)/(pi*(1 - betta) + (1 - pi)*(1 - alpha)), 0,
                          0, 1 - pi*(1 - betta)/(pi*(1 - betta) + (1 - pi)*(1 - alpha)), 1),
                        nrow = 3, ncol = 2)
  
  list(entropy = get_entropy4(pi, alpha, betta, rel_posibilities),
       relative_entropy = get_entropy4(pi, alpha, betta, posibilities))
}

get_relative_posibilities_for_plus <- function(plus_sequence, second_sequence) {
  posibilities = matrix(c(0,0,0,0,0,0), nrow = 3, ncol = 2)
  count1rel0 = 0
  count0in_first = 0
  count1rel1 = 0 
  count1in_first = 0
  count1rel2 = 0
  count2in_first = 0
  for (i in 1:length(plus_sequence)) {
    if (plus_sequence[i] == 0) {
      if (second_sequence[i] == 1) {
        count1rel0 = count1rel0 + 1
      }
      count0in_first = count0in_first + 1
    }
    
    if (plus_sequence[i] == 1) {
      if (second_sequence[i] == 1) {
        count1rel1 = count1rel1 + 1
      }
      count1in_first = count1in_first + 1
    }
    
    if (plus_sequence[i] == 2) {
      if (second_sequence[i] == 1) {
        count1rel2 = count1rel2 + 1
      }
      count2in_first = count2in_first + 1
    }
  }
  posibilities[1,2] = count1rel0/count0in_first
  posibilities[1,1] = 1 - posibilities[1,2]

  posibilities[2,2] = count1rel1/count1in_first
  posibilities[2,1] = 1 - posibilities[2,2]

  posibilities[3,2] = count1rel2/count2in_first
  posibilities[3,1] = 1 - posibilities[3,2]
  
  posibilities
}

get_entropy4 <- function(pi, alpha, betta, posibilities) {
  -((1 - pi)*alpha*posibilities[1,1]*log2(posibilities[1,1]) + 
      (1 - alpha + pi*alpha - pi*betta)*(posibilities[2,1]*log2(posibilities[2,1]) +
                                           posibilities[2,2]*log2(posibilities[2,2])) +
      pi*betta*posibilities[3,2]*log2(posibilities[3,2]))
}

get_entropy_for_circle_plus <- function(variables_first, variables_second, pi, alpha, betta) {
  circle_plus_sequence = as.numeric(variables_first) + as.numeric(variables_second)
  circle_plus_sequence = replace(circle_plus_sequence, circle_plus_sequence == 2, 0)

  rel_posibilities = get_relative_posibilities_for_circle_plus(circle_plus_sequence, variables_second)
  posibilities = matrix(c((1 - pi)*alpha/((1 - pi)*alpha + pi*betta),
                          1 - (1 - pi)*(1 - alpha)/(pi*(1 - betta) + (1 - pi)*(1 - alpha)),
                          1 - (1 - pi)*alpha/((1 - pi)*alpha + pi*betta),
                          (1 - pi)*(1 - alpha)/(pi*(1 - betta) + (1 - pi)*(1 - alpha))), 
                        nrow = 2, ncol = 2)

  list(entropy = get_entropy4_circle(pi, alpha, betta, rel_posibilities),
       relative_entropy = get_entropy4_circle(pi, alpha, betta, posibilities))
}

get_relative_posibilities_for_circle_plus <- function(circle_plus_sequence, second_sequence) {
  posibilities = matrix(c(0,0,0,0), nrow = 2, ncol = 2)
  
  count0rel0 = 0
  count0in_first = 0
  count1rel1 = 0
  count1in_first = 0
  
  for (i in 1:length(circle_plus_sequence)) {
    if (circle_plus_sequence[i] == 0) {
      if (second_sequence[i] == 0) {
        count0rel0 = count0rel0 + 1
      }
      count0in_first = count0in_first + 1
    }
    else {
      if (second_sequence[i] == 1) {
        count1rel1 = count1rel1 + 1
      }
      count1in_first = count1in_first + 1
    }
  }
  
  posibilities[1,1] = count0rel0 / count0in_first
  posibilities[1,2] = 1 - posibilities[1,1]
  
  posibilities[2,2] = count1rel1 / count1in_first
  posibilities[2,1] = 1 - posibilities[2,2]
  
  posibilities
}

get_entropy4_circle <- function(pi, alpha, betta, posibilities) {
  -((pi*betta + (1 - pi)*alpha)*(posibilities[1,1]*log2(posibilities[1,1]) + posibilities[1,2]*log2(posibilities[1,2])) + 
      (pi*(1 - betta) + (1 - pi)*(1 - alpha))*(posibilities[2,1]*log2(posibilities[2,1]) + posibilities[2,2]*log2(posibilities[2,2])))
}
