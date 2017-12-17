import numpy as np
import math
import string

size = 27
alphabet = string.ascii_uppercase + ' '
length = 0


def print_in_file(path, str_to_print):
    with open(path, "w") as f:
        f.write(str_to_print)


def get_matrix_of_string(input_string):
    input_data = np.array([alphabet.index(i) for i in input_string])
    #    input_data = np.array([alphabet.index(input_s[i:i+n]) for i in range(0, len(input_s), n)])
    matrix = np.matrix(input_data).reshape(math.ceil(len(input_data) / 2), 2)
    return matrix


def get_input_data(path):
    with open(path) as f:
        input_s = f.read()

    length = len(input_s)

    if length % 2 == 1:
        input_s = input_s + ' '

    return get_matrix_of_string(input_s)


def get_key(path):
    with open(path) as f:
        input_key = f.read()

    input_data_key = np.array([alphabet.index(i) for i in input_key])
    key = np.matrix(input_data_key).reshape(2, 2)
    return key


def get_string_after_refactor(matrix, key):
    list_of_codes = []
    for row in matrix:
        result = key.dot(np.matrix(row).reshape(2, 1))
        list_of_codes.append(np.mod(int(result[0]), size))
        list_of_codes.append(np.mod(int(result[1]), size))

    code_str = ""
    for i in list_of_codes:
        code_str = code_str + alphabet[i]
    return code_str


def get_eiler_function(number):
    count = 0
    for i in range(1, number, 1):
        if math.gcd(i, number) == 1:
            count += 1
    return count


def get_inverse_matrix_by_mod(matrix):
    det = int(np.linalg.det(matrix)) ** (get_eiler_function(size) - 1)
    temp_matrix = np.matrix(matrix).reshape(4, 1)
    matrix_inverse = np.matrix(np.zeros(4)).reshape(4, 1)
    matrix_inverse[0] = np.mod((temp_matrix[3] * det), size)
    matrix_inverse[3] = np.mod((temp_matrix[0] * det), size)
    matrix_inverse[1] = np.mod((-1 * temp_matrix[1] * det), size)
    matrix_inverse[2] = np.mod((-1 * temp_matrix[2] * det), size)
    matrix_inverse = matrix_inverse.reshape(2, 2)

    return matrix_inverse


key = get_key("key.txt")
key_inverse = get_inverse_matrix_by_mod(key)
encode_string = get_string_after_refactor(get_input_data("in.txt"), key)
print_in_file("crypt.txt", encode_string)
decode_string = get_string_after_refactor(get_matrix_of_string(encode_string), key_inverse)
print_in_file("decrypt.txt", decode_string)



