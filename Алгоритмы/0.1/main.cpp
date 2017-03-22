#include <iostream>
#include <fstream>

using namespace std;

struct TreeItem {
    TreeItem *leftSon;
    TreeItem *rightSon;
    int value;
};


void addElement(TreeItem *&root, int value);
void show(TreeItem* root, ofstream& fout);

int main() {
    int value = 0;
    TreeItem *root = nullptr;

    ifstream fin("input.txt");
    ofstream fout("output.txt");

    if (!fin) {
        return 0;
    }

    while (!fin.eof()) {
        fin >> value;
        addElement(root, value);
    }

    show(root, fout);
    fin.close();
    fout.close();

    return 0;
}

void addElement(TreeItem *&element, int value) {
    if (nullptr == element) {
        element = new TreeItem;
        element->value = value;
        element->leftSon = nullptr;
        element->rightSon = nullptr;
        return;
    }

    if (value < element->value) {
        if (element->leftSon != nullptr)
            addElement(element->leftSon, value);
        else {
            element->leftSon = new TreeItem();
            element->leftSon->leftSon = nullptr;
            element->leftSon->rightSon = nullptr;
            element->leftSon->value = value;
        }
        return;
    }

    if (value > element->value) {
        if (element->rightSon != nullptr)
            addElement(element->rightSon, value);
        else {
            element->rightSon = new TreeItem;
            element->rightSon->leftSon = nullptr;
            element->rightSon->rightSon = nullptr;
            element->rightSon->value = value;
        }
        return;
    }
}

void show(TreeItem* root, ofstream& fout) {
    if (root != nullptr) {
        fout << root->value << std::endl;
        show(root->leftSon, fout);
        show(root->rightSon, fout);
    }
}
