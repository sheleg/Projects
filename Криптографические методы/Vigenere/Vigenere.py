import string
import numpy as np
alphabet = string.ascii_uppercase + ' '
size = 27


def get_str_from_file(path):
    with open(path) as f:
        s = f.readline()
    return s


def print_in_file(path, arr):
    s = ""
    for i in arr:
        s += alphabet[i]
    with open(path, "w") as f:
        f.write(s)


def get_correct_str(s):
    arr = [alphabet.index(i) for i in s]
    return arr


def get_correct_key(k):
    arr = [alphabet.index(i) for i in k]
    return arr


def encrypt(data, key):
    arr = [np.mod(data[i] + key[i % len(key)], size) for i in range(0, len(data))]
    return arr


def decrypt(data, key):
    arr = [np.mod(data[i] - key[i % len(key)], size) for i in range(0, len(data))]
    return arr


data_from_file = get_str_from_file("in.txt")
key = get_str_from_file("key.txt")
correct_data = get_correct_str(data_from_file)
correct_key = get_correct_key(key)
encode = encrypt(correct_data, correct_key)
print_in_file("crypt.txt", encode)
decode = decrypt(encode, correct_key)
print_in_file("decrypt.txt", decode)
