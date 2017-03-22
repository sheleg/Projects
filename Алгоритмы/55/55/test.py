with open("input.txt", "r") as fin:
    with open("output.txt", "w") as fout:
        n = int(fin.readline())
        dec_to_bin = str(bin(n)[2:])
        k = 0
        for i in dec_to_bin[::-1]:
            if int(i) == 1:
                fout.write(str(k) + "\n")
            k += 1
