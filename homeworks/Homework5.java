import java.util.Iterator;

class TreeIterator<E extends DSAComparable<E>> implements Iterator<E> {
    private Node<E> _next;

    TreeIterator(Node<E> leftmost) {
        _next = leftmost;
    }

    @Override
    public boolean hasNext() {
        return _next != null;
    }

    @Override
    public E next() {
        Node<E> current = _next;
        if (current != null) {
            _next = current.succ();
        }
        return current == null ? null : current.contents;
    }

    @Override
    public void remove() {

    }
}

// Trida Tree reprezentuje binarni vyhledavaci strom, ve kterem pro kazdy uzel n plati
// n.left == null || n.left.contents.less(n.contents) a
// n.right == null || n.right.contents.greater(n.contents).

class Tree<E extends DSAComparable<E>> {

    Node<E> root;

    private Node<E> leftmost(Node<E> root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root;
        }
        return leftmost(root.left);
    }

    private Node<E> rightmost(Node<E> root) {
        if (root == null) {
            return null;
        }
        if (root.right == null) {
            return root;
        }
        return rightmost(root.right);
    }

    private Node<E> findInsert(Node<E> root, E toInsert) {
        if (root == null || toInsert == null) {
            return null;
        }
        if (toInsert.less(root.contents)) {
            return root.left == null ? root : findInsert(root.left, toInsert);
        }
        if (toInsert.greater(root.contents)) {
            return root.right == null ? root : findInsert(root.right, toInsert);
        }
        return root;
    }

    // Vrati minimum z tohoto stromu nebo null, pokud je strom prazdny.

    E minimum() {
        return subtreeMin(root);
    }

    // Vrati minimum ze zadaneho podstromu nebo null, pokud je podstrom prazdny.

    E subtreeMin(Node<E> n) {
        Node<E> leftmost = leftmost(n);
        return leftmost == null ? null : leftmost.contents;
    }

    // Vrati maximum z tohoto podstromu nebo null, pokud je podstrom prazdny.

    E maximum() {
        return subtreeMax(root);
    }

    // Vrati maximum ze zadaneho podstromu nebo null, pokud je podstrom prazdny.

    E subtreeMax(Node<E> n) {
        Node<E> rightmost = rightmost(n);
        return rightmost == null ? null : rightmost.contents;
    }

    // Vlozi prvek do stromu (duplicity jsou zakazane)

    void insert(E elem) {
        Node<E> parent = findInsert(root, elem);
        Node<E> node = new Node<>(elem, null);
        if (parent == null) {
            root = new Node<>(elem, null);
        } else if (elem.less(parent.contents)) {
            parent.left = node;
            node.setParent(parent);
        } else if (elem.greater(parent.contents)) {
            parent.right = node;
            node.setParent(parent);
        }
    }

    // Projde strom a vrati: // - uzel s hodnotou elem, pokud existuje, // - null pokud uzel s hodnotou elem existuje

    Node<E> find(E elem) {
        Node<E> found = findInsert(root, elem);
        if (found == null || !found.contents.equals(elem)) {
            return null;
        }
        return found;
    }

    // Vrati true, pokud tento strom obsahuje prvek elem.

    boolean contains(E elem) {
        return find(elem) != null;
    }

    // Odstrani vyskyt prvku elem z tohoto stromu.

    void remove(E elem) {
        Node<E> parent = null;
        Node<E> node = root;

        while (node != null) {
            if (node.left != null && elem.less(node.contents)) {
                parent = node;
                node = node.left;
            } else if (node.right != null && elem.greater(node.contents)) {
                parent = node;
                node = node.right;
            } else {
                break;
            }
        }

        if (node == null || !elem.equals(node.contents)) {
            return;
        }

        Node<E> replace = null;

        if (node.left != null) {
            replace = node.left;
            rightmost(node.left).right = node.right;
        } else if(node.right != null) {
            replace = node.right;
        }

        if (parent != null && node.contents.less(parent.contents)) {
            parent.left = replace;
        } else if(parent != null) {
            parent.right = replace;
        } else {
            root = replace;
        }

        if (replace != null) {
            replace.setParent(parent);
        }
    }

    // Vrati iterator pres cely strom (od nejmensiho po nejvetsi). Metoda remove() nemusí být implementována

    Iterator<E> iterator() {
        return new TreeIterator<E>(leftmost(root));
    }

    // Vrati korenovy uzel tohoto stromu.

    Node<E> getRootNode() {
        return root;
    }

}