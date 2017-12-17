#include <stdio.h>

int main() {
    int inputNumber = 0;
    scanf("%d", &inputNumber);
    if (inputNumber < 5 && inputNumber >= 0) {
        printf("%d", inputNumber*inputNumber*inputNumber*inputNumber);
    }
    if (inputNumber > 4 && inputNumber < 10) {
        printf("%d", inputNumber*inputNumber);
    }
    return 0;
}