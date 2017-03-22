#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

long long MSL = 0, rootKey;

struct TreeItem {
    TreeItem *leftSon;
    TreeItem *rightSon;
    long long value;

    long long height;
    long long countOfLeaf;

    long long maxSemipathLength;

    long long countOfSonsMaxSemipath;
    long long countOfParentMaxSemipath;
};

void addElement(TreeItem *&root, long long value);

void show(TreeItem *root, ofstream &fout);

void deleteByKey(TreeItem *&pItem, long long keyToDelete);

TreeItem *&minimum(TreeItem *startElement);

void firstReverseTraversal(TreeItem *&element);

void secondReverseTraversal(TreeItem *&pItem);

void straightTraversal(TreeItem *&element);

int main() {
    long long value = 0;
    TreeItem *root = nullptr;

    ifstream fin("input.txt");
    ofstream fout("output.txt");

    if (!fin) {
        return 0;
    }

    while (fin >> value) {
        addElement(root, value);
    }

    deleteByKey(root, root->value);

    if (root->leftSon == nullptr && root->rightSon == nullptr) {
        fout << root->value << " 1" << endl;
        return 0;
    }

    firstReverseTraversal(root);
    secondReverseTraversal(root);

    rootKey = root->value;
    straightTraversal(root);

    show(root, fout);

    fin.close();
    fout.close();
    return 0;
}

void straightTraversal(TreeItem *&element) {
    if (element != nullptr) {

        if (element->leftSon != nullptr && element->rightSon != nullptr) {
            if (element->leftSon->height > element->rightSon->height) {
                element->leftSon->countOfParentMaxSemipath = element->countOfParentMaxSemipath
                                                             + element->countOfSonsMaxSemipath;
                element->rightSon->countOfParentMaxSemipath = element->countOfSonsMaxSemipath;
            }
            if (element->rightSon->height > element->leftSon->height) {
                element->rightSon->countOfParentMaxSemipath = element->countOfParentMaxSemipath
                                                              + element->countOfSonsMaxSemipath;
                element->leftSon->countOfParentMaxSemipath = element->countOfSonsMaxSemipath;
            }
            if (element->leftSon->height == element->rightSon->height) {
                element->leftSon->countOfParentMaxSemipath = element->countOfSonsMaxSemipath
                                                             + element->leftSon->countOfLeaf *
                                                               element->countOfParentMaxSemipath / element->countOfLeaf;
                element->rightSon->countOfParentMaxSemipath = element->countOfSonsMaxSemipath
                                                              + element->rightSon->countOfLeaf *
                                                                element->countOfParentMaxSemipath / element->countOfLeaf;
            }
        }

        if (element->leftSon != nullptr && element->rightSon == nullptr) {
            element->leftSon->countOfParentMaxSemipath =
                    element->countOfParentMaxSemipath + element->countOfSonsMaxSemipath;
        }

        if (element->rightSon != nullptr && element->leftSon == nullptr) {
            element->rightSon->countOfParentMaxSemipath =
                    element->countOfParentMaxSemipath + element->countOfSonsMaxSemipath;
        }

        if (element->value == rootKey) {
            element->countOfParentMaxSemipath = 0;
        }

        straightTraversal(element->leftSon);
        straightTraversal(element->rightSon);
    }
}

void secondReverseTraversal(TreeItem *&element) {
    if (element != nullptr) {
        secondReverseTraversal(element->leftSon);
        secondReverseTraversal(element->rightSon);

        if (MSL != element->maxSemipathLength) {
            element->countOfSonsMaxSemipath = 0;
            return;
        } else {
            if (element->leftSon != nullptr && element->rightSon != nullptr) {
                element->countOfSonsMaxSemipath = element->leftSon->countOfLeaf * element->rightSon->countOfLeaf;
                return;
            }
            if (element->leftSon != nullptr) {
                element->countOfSonsMaxSemipath = element->leftSon->countOfLeaf;
                return;
            }
            if (element->rightSon != nullptr) {
                element->countOfSonsMaxSemipath = element->rightSon->countOfLeaf;
                return;
            }
        }
    }
}

void firstReverseTraversal(TreeItem *&element) {
    if (element != nullptr) {
        firstReverseTraversal(element->leftSon);
        firstReverseTraversal(element->rightSon);

        if (element->leftSon != nullptr && element->rightSon != nullptr) {
            element->height = max(element->leftSon->height, element->rightSon->height) + 1;
            element->maxSemipathLength = element->leftSon->height + element->rightSon->height + 2;
//            element->countOfSonsMaxSemipath = element->leftSon->countOfLeaf * element->rightSon->countOfLeaf;
            MSL = max(MSL, element->maxSemipathLength);

            if (element->leftSon->height == element->rightSon->height)
                element->countOfLeaf = element->leftSon->countOfLeaf + element->rightSon->countOfLeaf;
            if (element->leftSon->height > element->rightSon->height)
                element->countOfLeaf = element->leftSon->countOfLeaf;
            if (element->rightSon->height > element->leftSon->height)
                element->countOfLeaf = element->rightSon->countOfLeaf;

            return;
        }
        if (element->leftSon != nullptr) {
            element->height = element->leftSon->height + 1;
            element->countOfLeaf = element->leftSon->countOfLeaf;
            element->maxSemipathLength = element->leftSon->height + 1;
            MSL = max(MSL, element->maxSemipathLength);

//            element->countOfSonsMaxSemipath = element->leftSon->countOfLeaf;
            return;
        }
        if (element->rightSon != nullptr) {
            element->height = element->rightSon->height + 1;
            element->countOfLeaf = element->rightSon->countOfLeaf;
            element->maxSemipathLength = element->rightSon->height + 1;
            MSL = max(MSL, element->maxSemipathLength);

//            element->countOfSonsMaxSemipath = element->rightSon->countOfLeaf;
            return;
        }
        if (element->leftSon == nullptr && element->rightSon == nullptr) {
            element->height = 0;
            element->countOfLeaf = 1;
            element->maxSemipathLength = 0;
//            element->countOfSonsMaxSemipath = 0;
            return;
        }


    }
}

void addElement(TreeItem *&element, long long value) {
    if (nullptr == element) {
        element = new TreeItem;
        element->value = value;
        element->leftSon = nullptr;
        element->rightSon = nullptr;
        element->height = 0;
        element->countOfLeaf = 0;

        element->maxSemipathLength = 0;
        element->countOfSonsMaxSemipath = 0;
        element->countOfParentMaxSemipath = 0;

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
            element->height = 0;
            element->countOfLeaf = 0;
            element->maxSemipathLength = 0;
            element->countOfSonsMaxSemipath = 0;
            element->countOfParentMaxSemipath = 0;
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
            element->height = 0;

            element->countOfLeaf = 0;
            element->maxSemipathLength = 0;
            element->countOfSonsMaxSemipath = 0;
            element->countOfParentMaxSemipath = 0;
        }
        return;
    }
}

void show(TreeItem *root, ofstream &fout) {
    if (root != nullptr) {
        fout << root->value << " " << root->countOfParentMaxSemipath + root->countOfSonsMaxSemipath << endl;
//        fout << root->value << " h = " << root->height << " l = " << root->countOfLeaf << endl;
//        fout << "   MSL = " << root->maxSemipathLength << endl;
//        fout << "   b = " << root->countOfSonsMaxSemipath << " a = " << root->countOfParentMaxSemipath << endl << endl;
        show(root->leftSon, fout);
        show(root->rightSon, fout);
    }
}

void deleteByKey(TreeItem *&root, long long keyToDelete) {
    if (root == nullptr)
        return;
    if (keyToDelete < root->value)
        deleteByKey(root->leftSon, keyToDelete);
    else if (keyToDelete > root->value)
        deleteByKey(root->rightSon, keyToDelete);
    else if (root->leftSon != nullptr && root->rightSon != nullptr) {
        root->value = minimum(root->rightSon)->value;
        deleteByKey(root->rightSon, root->value);
    } else if (root->leftSon != nullptr)
        root = root->leftSon;
    else
        root = root->rightSon;
}

TreeItem *&minimum(TreeItem *startElement) {
    if (startElement->leftSon == nullptr) {
        return startElement;
    }
    minimum(startElement->leftSon);
}