/*
 * Naimplementujte třídu Homework2 implementující rozhraní InterSearch (váš soubor Homework2.java bude toto rozhraní obsahovat).
 */

interface InterSearch {
    /* Požitím interpolačního hledání vrátí index prvku what nalezeného mezi indexy first a last
       pole data nebo -1, pokud tam není. Metoda bude rekurzivní a měla by být odolná vůči chybně
       zadaným parametrům (v případě chyby vrátí opět -1). Pro zaokrouhlování na celá čísla použijte metodu Math.round(). */
    public int search(int first, int last, int what, int[] data);
}

/*
 * Naimplementujte třídu Homework2 implementující rozhraní InterSearch (váš soubor Homework2.java bude toto rozhraní obsahovat).
 */

public class Homework2 implements InterSearch {
    @Override
    public int search(int first, int last, int what, int[] data) {
        int indexRange = last - first;

        // Check invalid data
        if (first < 0 || indexRange < 0 || last >= data.length) {
            return -1;
        }

        // If only has one element return if it equals
        if (what == data[first]) {
            return first;
        }

        // If less than 2 values
        if (indexRange <= 1 || data[first] == data[last] ) {
            return -1;
        }

        int low = data[first];
        int high = data[last];
        double valueRange = (double)(high - low);
        double valuePosition = Math.max(0, Math.min(1, (what - low) / valueRange));
        int index = first + (int)Math.round(valuePosition * indexRange);

        if (what == data[index]) {
            return index;
        } else if (what < data[index]){
            return search(first, index - 1, what, data);
        } else {
            return search(index + 1, last, what, data);
        }
    }
}