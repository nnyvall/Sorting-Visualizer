import java.util.ArrayList;

/**
 * @author Nicholas Nyvall (nnyvall@wisc.edu)
 * This class contains all the algorithms that can be graphically represented. Algorithms include bubble sort,
 * selection sort, and insertion sort.
 */
public class Algorithms {

    public GraphVisualizer graphVisualizer;

    public Algorithms(GraphVisualizer gv) {
        this.graphVisualizer = gv;
    }

    /**
     * This method runs an iterative implementation of bubble sort. It repeatedly checks if the current index
     * (incremented in it's animation timer) is greater than the next in order index. If it is not, the greater index
     * replaces the current. This process continues until the array is sorted. The current bar is highlighted red
     * and all sorted bars are highlighted blue.
     *
     * @param array the integer array to be sorted
     * @param index the current index
     */
    protected void bubbleSort(int[] array, int index) {
        int temp;
        graphVisualizer.highlightRed = new ArrayList<>();
        if (array[index] > array[index + 1]) {
            temp = array[index];
            array[index] = array[index + 1];
            array[index + 1] = temp;
            graphVisualizer.highlightRed.add(index + 1);
        }
        GraphVisualizer.bsIndex++;
        graphVisualizer.createChart(array, graphVisualizer);
    }

    /**
     * This method runs an iterative implementation of selection sort. It iterates through the entire array, keeping
     * track of the minimum value. Once the end of the array has been reached, the minimum remaining value is swapped
     * with the current index. This process is repeated until the array is sorted. The current bar and the bar is
     * for which it will be swapped are highlighted red and all sorted bars are highlighted blue.
     *
     * @param array the integer array to be sorted
     * @param index the current index
     */
    protected void selectionSort(int[] array, int index) {
        int currMinIndex = index;
        int minVal = array[currMinIndex];
        int currVal = array[index];
        graphVisualizer.highlightRed = new ArrayList<>();
        graphVisualizer.highlightBlue.add(index);
        for (int i = index; i < array.length; i++) {
            if (array[i] < minVal) {
                minVal = array[i];
                currMinIndex = i;
            }
        }
        if (index != array.length - 1) {
            graphVisualizer.highlightRed.add(index);
            graphVisualizer.highlightRed.add(currMinIndex);
        }
        array[index] = minVal;
        array[currMinIndex] = currVal;
        graphVisualizer.createChart(array, graphVisualizer);
    }

    /**
     * This method runs an iterative implementation of insertion sort. They array is iterated through repeatedly
     * comparing the current element to its predecessor. If the key is smaller it is then swapped. The process is
     * repeated until the array is sorted. The current bar is highlighted red and all sorted bars are highlighted blue.
     *
     * @param array the integer array to be sorted
     * @param index the current index
     */
    protected void insertionSort(int[] array, int index) {
        int key = array[index];
        int j = index - 1;
        graphVisualizer.highlightRed = new ArrayList<>();
        if (index != array.length - 1) {
            graphVisualizer.highlightRed.add(j + 1);
        }
        while (j >= 0 && array[j] > key) {
            array[j + 1] = array[j];
            graphVisualizer.highlightBlue.add(j);
            j--;
        }
        array[j + 1] = key;
        graphVisualizer.createChart(array, graphVisualizer);
    }
}
