import com.sun.tools.classfile.Opcode;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

class DSAHashTableIterator<K, V> implements Iterator<Pair<K, V>>{

    private int _index;
    private List<Pair<K, V>> _allPairs;

    DSAHashTableIterator(Set<Pair<K, V>>[] table) {
        _index = 0;
//        _allPairs =
        Stream<Pair<K, V>> pairStream = Arrays.stream(table)
            .map( x -> {
                    Pair<K, V>[] pairs = (Pair<K, V>[]) new Pair<?, ?>[x.size()];
                    return x.toArray(pairs);
            })
            .flatMap(x -> Arrays.stream(x));

        _allPairs = pairStream.collect(Collectors.toList());
    }

    @Override
    public boolean hasNext() {
        return _index < _allPairs.size();
    }

    @Override
    public Pair<K, V> next() {
        Pair<K, V> out = _allPairs.get(_index);
        _index += 1;
        return out;
    }

    @Override
    public void remove() {

    }
}

// Trida DSAHashTable reprezentuje rozptylovaci tabulku se zřetězením (první varianta v učebnici).

class DSAHashTable<K,V> {
    private int count;
    private Set<Pair<K, V>>[] table;

    // Vytvori prazdnou instanci DSAHashTable, delka vnitrniho pole je nastavena na size, obsah vnitrniho pole je inicializovan na prazdne mnoziny.

    DSAHashTable(int size) {
        count = 0;
        table = (Set<Pair<K, V> >[]) new Set<?>[size];

        for (int i = 0; i < size; ++i) {
            table[i] = new HashSet<>();
        }
    }

    private Pair<K, V> pairWithKeyInSet(K key, Set<Pair<K, V>> set) {
        Pair<K, V> outputPair = null;
        int keyHash = key.hashCode();
        Iterator<Pair<K, V>> it = set.iterator();

        while(it.hasNext()) {
            Pair<K, V> pair = it.next();
            if (pair.hashCode() == keyHash) {
                outputPair = pair;
            }
        }

        return outputPair;
    }

    // Ulozi dvojici (key, value) do rozptylovaci tabulky. Pokud uz v tabulce je jina dvojice se stejnym klicem, je smazana.
    // Klic ani hodnota nesmi byt null. Pokud by pocet dvojic v tabulce po vykonani put mel vzrust nad dvojnasobek delky vnitrniho pole,
    // vnitrni pole zdvojnasobi.

    void put(K key, V value) {
        int index = getIndexOf(key);
        Set<Pair<K, V>> set = table[index];
        Pair<K, V> collision = pairWithKeyInSet(key, set);

        if(collision != null) {
            set.remove(collision);
            count--;
        }

        Pair<K, V> insert = new Pair<>(key, value);
        set.add(insert);
        count++;
    }

    // Vrati hodnotu asociovanou s danym klicem nebo null, pokud dany klic v tabulce neni.

    V get(K key) {
        int index = getIndexOf(key);
        Set<Pair<K, V>> set = table[index];
        Pair<K, V> pair = pairWithKeyInSet(key, set);

        return pair != null ? pair.value : null;
    }

    // Smaze dvojici s danym klicem. Pokud v tabulce dany klic neni, nedela nic.

    void remove(K key) {
        int index = getIndexOf(key);
        Set<Pair<K, V>> set = table[index];
        Pair<K, V> collision = pairWithKeyInSet(key, set);

        if(collision != null) {
            set.remove(collision);
        }
    }

    // Vrati vnitrni pole. Prvky vnitrniho pole mohou byt instance trid v balicku java.util, tzn. nemusite psat vlastni implementaci rozhrani java.util.Set.

    Set<Pair<K,V>>[] getArray() {
        return table;
    }

    // Pro dany klic vrati index v poli. Jako hashovaci funkce se pouzije key.hashCode.

    int getIndexOf(K key) {
        return key.hashCode() % table.length;
    }

    // Pokud je pocet prvku mensi nebo roven dvojnasobku delky vnitrniho pole, vrati true, jinak vrati false.

    boolean isBigEnough() {
        return 2 * table.length > count;
    }

    // Zmeni delku vnitrniho pole, nainicializuje jej prazdnymi mnozinami a zkopiruje do nej vsechny dvojice.

    void resize(int newSize) {
        DSAHashTableIterator<K, V> it = new DSAHashTableIterator<>(table);
        count = 0;
        table = (Set<Pair<K, V> >[]) new Set<?>[newSize];

        for (int i = 0; i < newSize; ++i) {
            table[i] = new HashSet<>();
        }

        while (it.hasNext()) {
            Pair<K, V> current = it.next();
            put(current.key, current.value);
        }
    }

    // Vrati iterator pres vsechny dvojice v tabulce. Iterator nemusi mit implementovanou metodu remove.

    Iterator<Pair<K,V>> iterator() {
        return new DSAHashTableIterator<>(table);
    }
}