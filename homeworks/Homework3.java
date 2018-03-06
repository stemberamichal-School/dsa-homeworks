class Homework3<E extends DSAComparable<E>> implements HeapStorage<E> {
    private int offset = 1;
    private int size;
    private E[] elements;

    Homework3(E[] elements) {
        this.elements = elements;
        size = elements.length;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E getElement (int index) {
        if (index - offset < 0) {
            return elements[0];
        }

        if (index - offset >= size) {
            return elements[size - 1];
        }

        return elements[index - offset];
    }

    public void setElement(E element, int index) {
        elements[index - offset] = element;
    }

    // Prohodi prvky na indexech index a index2 (1 <= index, index2 <= getSize()).
    public void swap (int index, int index2) {
        E first = getElement(index);
        E second = getElement(index2);
        setElement(first, index2);
        setElement(second, index);
    }

    // Odstrani posledni prvek (tzn. snizi velikost teto HeapStorage) a vrati jeho hodnotu.
    public E extractLast() {
        size--;
        return elements[size];
    }

    // Vlozi prvek a vrati jeho index. Muzete predpokladat, ze ve spodnim poli je dost mista.
    public int insertLast (E element) {
        elements[size] = element;
        size++;
        return size + offset - 1;

    }
}

// Trida Heap reprezentuje haldu (s maximem ve vrcholu).

class Heap<E extends DSAComparable<E>> {

    HeapStorage<E> storage;

    // Querying parent
    int parentOf(int child) { return child / 2; }
//    boolean hasParent(int child) { return child <= storage.getSize() && parentOf(child) >= 1; }

    // Querying children
    int leftChildOf(int parent) { return 2 * parent; }
    int rightChildOf(int parent) { return leftChildOf(parent) + 1;  }
//    boolean hasLeftChild(int parent) { return parent >= 1 && leftChildOf(parent) <= storage.getSize(); }
//    boolean hasRightChild(int parent) { return parent >= 1 && rightChildOf(parent) <= storage.getSize(); }

    boolean exists(int node) { return node >= 1 && node <= storage.getSize(); }


    // Vytvori haldu nad danym HeapStorage (tzn. zavola algoritmus build heap).
    Heap(HeapStorage<E> storage) {
        for (int i = storage.getSize() / 2; i > 0; --i) {
            heapify(i);
        }
    }

    // Zavola algoritmus heapify nad uzlem na indexu index.
    void heapify(int parent) {
        int leftChild = leftChildOf(parent);
        int rightChild = rightChildOf(parent);

        boolean leftChildExists = exists(leftChild);
        boolean rightChildExists = exists(rightChild);

        E parentValue = storage.getElement(parent);

        if(leftChildExists && rightChildExists) {
            E leftValue = storage.getElement(leftChild);
            E rightValue = storage.getElement(rightChild);

            if(parentValue.less(rightValue) && leftValue.less(rightValue)) {
                storage.swap(parent, rightChild);
                heapify(rightChild);
            } else if (parentValue.less(leftValue)) {
                storage.swap(parent, leftChild);
                heapify(leftChild);
            }
        } else if (leftChildExists && parentValue.less(storage.getElement(leftChild))) {
            storage.swap(parent, leftChild);
            heapify(leftChild);
        } else if(rightChildExists && parentValue.less(storage.getElement(rightChild))) {
            storage.swap(parent, rightChild);
            heapify(rightChild);
        }
    }

    void siftUp(int index) {
        if(exists(index) == false)
            return;

        int parent = parentOf(index);
        if (exists(parent) == false)
            return;

        E value = storage.getElement(index);
        E valueParent = storage.getElement(parent);

        if (valueParent.less(value)) {
            storage.swap(index, parent);
            siftUp(parent);
        }
    }

    // Vlozi do haldy novy prvek. Muzete predpokladat, ze v poli uvnitr HeapStorage na nej misto je.
    void insert(E element) {
        storage.insertLast(element);
        siftUp(storage.getSize());
    }

    // Odstrani a vrati z haldy maximalni prvek.
    E extractMax() {
        storage.swap(1, storage.getSize());
        E max = storage.extractLast();
        heapify(1);
        return max;
    }

    // Vrati true, pokud je halda prazdna.
    boolean isEmpty() {
        return storage.isEmpty();
    }

    // Pomoci algoritmu trideni haldou vzestupne setridi pole array.
    static <E extends DSAComparable<E>> void heapsort(E[] array) {
        HeapStorage<E> storage = new Homework3<>(array);
        Heap<E> heap = new Heap<>(storage);

        while(heap.isEmpty() == false) {
            heap.extractMax();
        }
    }
}