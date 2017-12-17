#include <iostream>

int main() {
    int inputNumber = 0;
    std::cin >> inputNumber;
    if (inputNumber < 5 && inputNumber >= 0) {
        std::cout << inputNumber*inputNumber*inputNumber*inputNumber;
    }
    if (inputNumber > 4 && inputNumber < 10) {
        std::cout << inputNumber*inputNumber;
    }
    system("pause");
    return 0;
}
