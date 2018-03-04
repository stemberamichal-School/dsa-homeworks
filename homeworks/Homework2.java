
public class Homework2 implements InterSearch {
    @Override
    public int search(int first, int last, int what, int[] data) {
        int indexRange = last - first;

        // Check invalid data
        if (first > last || indexRange == 0 || last >= data.length) {
            return -1;
        }

        // If only has one element return if it equals
        if (indexRange == 1) {
            if (what == data[first]) {
                return first;
            } else {
                return -1;
            }
        }


        int low = data[first];
        int high = data[last];
        double valueRange = (double)(high - low);
        double valuePosition = (what - low) / valueRange;
        int index = (int)Math.round(valuePosition * indexRange);

        if (what == data[index]) {
            return index;
        } else if (what < data[index]){
            return search(first, index - 1, what, data);
        } else {
            return search(index + 1, last, what, data);
        }
    }
}