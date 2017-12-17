org 256

mov ah, 01h
int 21h

sub al, '0'
mul al
cmp al, 16

ja higherThenNumber

mul al

higherThenNumber:
    mov bx, 10
loopOfNumeral:
    xor dx, dx
    div bx
    add dl, '0'

    push dx
    test ax, ax
    jne loopOfNumeral

print:
    ;mov bx, [sp]
    ;test bx, bx

    ;je done

    pop dx

    test dx, dx
    je allNumeralEnded

    mov ah, 2
    int 21h

    jmp print

allNumeralEnded:
    push dx
ret
