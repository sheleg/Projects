import string
import math

alphabet = string.ascii_uppercase + " "
size = 27


def print_data(data_to_print, path):
    with open(path, "w") as f:
        s = ""
        for i in data_to_print:
            s += i
        f.write(s)

def read_key(path):
    with open(path) as f:
        index = f.readlines()
    index_to_transpose = index[1].split(' ')
    mat_of_data = []
    for i in index_to_transpose:
        mat_of_data.append(int(i) - 1)
    return mat_of_data


def read_data(path):
    with open(path) as f:
        return f.readline()


def get_right_format_data(raw_data, key):
    if len(raw_data) % len(key) != 0:
        raw_data += ' ' * (- len(raw_data) % len(key))
    n = len(key)
    matrix_data = [raw_data[i:i + n] for i in range(0, len(raw_data), n)]
    return matrix_data


def transpose(correct_data, key):
    temp_str = ""
    end_str = []
    for s in correct_data:
        temp_str = ""
        for i in range(0, len(key)):
            temp_str += s[key[i]]
        end_str.append(temp_str)
    return end_str


def get_reverse_key(key):
    in_key = []
    for i in range(0, len(key)):
        in_key.append(key.index(i))
    return in_key


def reverse_transpose(data_to_decrypt, key):
    reverse_key = get_reverse_key(key)
    return transpose(data_to_decrypt, reverse_key)


key = read_key("key.txt")
data = read_data("in.txt")
right_data = get_right_format_data(data, key)
encrypt_data = transpose(right_data, key)
print_data(encrypt_data, "crypt.txt")
decrypt_data = reverse_transpose(encrypt_data, key)
print_data(decrypt_data, "decrypt.txt")

