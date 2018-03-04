
class Homework1 implements Mergesort {

    /// Index of the first element in the second half
    private int splitIndex(int [] array) {
        return (array.length / 2) + (array.length % 2);
    }

    @Override
    public int[] getFirstHalfOf(int[] array) {
        int index = splitIndex(array);
        int[] firstHalf = new int[index];
        System.arraycopy(array, 0, firstHalf, 0, index);
        return firstHalf;
    }

    @Override
    public int[] getSecondHalfOf(int[] array) {
        int index = splitIndex(array);
        int secondHalfLength = array.length - index;
        int[] secondHalf = new int[secondHalfLength];
        System.arraycopy(array, index, secondHalf, 0, secondHalfLength);
        return secondHalf;
    }

    @Override
    public int[] merge(int[] firstHalf, int[] secondHalf) {
        int resultLength = firstHalf.length + secondHalf.length;
        int[] result = new int[resultLength];


        for(int rIdx = 0, fIdx = 0, sIdx = 0; rIdx < resultLength; rIdx ++) {
            if (fIdx >= firstHalf.length) {
                result[rIdx] = secondHalf[sIdx];
                sIdx++;
            } else if (sIdx >= secondHalf.length) {
                result[rIdx] = firstHalf[fIdx];
                fIdx++;
            } else if(firstHalf[fIdx] <= secondHalf[sIdx]) {
                result[rIdx] = firstHalf[fIdx];
                fIdx++;
            } else {
                result[rIdx] = secondHalf[sIdx];
                sIdx++;
            }
        }
    }

    @Override
    public int[] mergesort(int[] array) {
        int[] firstHalf = getFirstHalfOf(array);
        int[] secondHalf = getSecondHalfOf(array);

        int[] firstSorted = mergesort(firstHalf);
        int[] secondSorted = mergesort(secondHalf);

        return merge(firstSorted, secondSorted);
    }
}