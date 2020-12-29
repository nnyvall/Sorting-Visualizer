import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * @author Nicholas Nyvall (nnyvall@wisc.edu)
 * This class creates a visual representation of an integer array as a bar graph. It handles animations corresponding
 * to the sorting algorithms bubble sort, selection sort, and insertion sort.
 */
public class GraphVisualizer extends StackPane {

    public static int numBars = 50;
    public static int[] array = createRandomArray(numBars);
    public static int bsIndex = 0;
    public static int ssIndex = 0;
    public static int isIndex = 0;
    public static int counter = array.length;

    public AnimationTimer bubbleTimer;
    public AnimationTimer selectionTimer;
    public AnimationTimer insertionTimer;

    public ArrayList<Integer> highlightBlue = new ArrayList<>();
    public ArrayList<Integer> highlightRed = new ArrayList<>();

    /**
     * This is a constructor method for GraphVisualizer. It creates animation timers for each sorting algorithm to
     * be run as specified by the user.
     */
    public GraphVisualizer() {
        Algorithms algorithms = new Algorithms(this);
        this.setId("graphVisualizer");

        /*
         * This is the animation timer for an iterative bubble sort implementation. It continually runs bubble sort,
         * resetting bsIndex to 0 after each bar is placed successfully. Bars are highlighted blue once they are
         * successfully sorted and placed in their final position. The current bar is highlighted red within the bubbleSort
         * method.
         */
        this.bubbleTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (bsIndex == array.length - 1) {
                    if (counter - 1 >= 0) {
                        highlightBlue.add(counter - 1);
                        counter--;
                    }
                    bsIndex = 0;
                }
                algorithms.bubbleSort(array, bsIndex);
            }
        };

        /*
         * This is an animation timer for an iterative selection sort implementations. It continually runs selection
         * sort, incrementing ssIndex until it is at the end of the array, then stopping. Bars are highlighted within
         * the selectionSort method, red corresponds to the bars to be swapped, blue to sorted bars. The animation is
         * delayed by the frameCount.
         */
        this.selectionTimer = new AnimationTimer() {
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount % 20 == 0) {
                    algorithms.selectionSort(array, ssIndex);
                    if (ssIndex == array.length - 1) {
                        this.stop();
                    }
                    ssIndex++;
                }
                frameCount++;
            }
        };

        /*
         * This is an animation timer for an iterative insertion sort implementation. It continually runs insertion
         * sort, incrementing isIndex while it does not exceed the bounds of the array. The current bar is highlighted
         * red and the sorted bars are highlighted blue.
         */
        this.insertionTimer = new AnimationTimer() {
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount % 6 == 0) {
                    if (isIndex < array.length - 1) {
                        isIndex++;
                        highlightRed.add(isIndex);
                    } else {
                        highlightBlue.add(array.length - 1);
                        this.stop();
                    }
                    algorithms.insertionSort(array, isIndex);
                }
                frameCount++;
            }
        };
    }

    /**
     * This method stops all animation timers, resets all algorithm's index counters to 0, removes all highlighted bars,
     * and creates a new random graph.
     */
    public void resetFields() {
        bubbleTimer.stop();
        insertionTimer.stop();
        selectionTimer.stop();
        bsIndex = 0;
        isIndex = 0;
        ssIndex = 0;
        counter = array.length;
        highlightBlue = new ArrayList<>();
        highlightRed = new ArrayList<>();
        array = createRandomArray(numBars);
        createChart(array, this);
    }

    /**
     * This method creates a randomized array of a size specified by the user between 25 and 100.
     *
     * @param numBars the size of the array (25-100)
     * @return an randomized int array of numbers between 50 and 550.
     */
    public static int[] createRandomArray(int numBars) {
        int[] arr = new int[numBars];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) Math.floor(Math.random() * Math.floor(500)) + 50;
        }
        return arr;
    }

    /**
     * This method styles and displays an integer array as a bar graph. It also highlights bars as specified by other
     * methods.
     *
     * @param array           the integer array to be graphically represented
     * @param graphVisualizer the StackPane where the graph will be displayed
     */
    public void createChart(int[] array, StackPane graphVisualizer) {
        graphVisualizer.getChildren().clear();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

        //Controls style elements of graph
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: white;");
        chart.setAnimated(true);
        chart.setCategoryGap(0);
        chart.setBarGap(2);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.getYAxis().setTickLabelsVisible(false);
        chart.getYAxis().setOpacity(0);
        chart.getXAxis().setTickLabelsVisible(false);
        chart.getXAxis().setOpacity(0);
        chart.setLegendVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        for (int i = 0; i < array.length; i++) {
            int index = i + 1;
            series1.getData().add(new XYChart.Data<>(Integer.toString(index), array[i]));
        }
        chart.getData().add(series1);
        for (Node n : chart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: #232b2b;");
        }

        //Highlights specified bars either blue or red
        for (Integer integer : highlightBlue) {
            if (integer < array.length) {
                series1.getData().get(integer).getNode().setStyle("-fx-bar-fill: #3333FF;");
            }
        }
        for (Integer integer : highlightRed) {
            if (integer < array.length) {
                series1.getData().get(integer).getNode().setStyle("-fx-bar-fill: #CC0000;");
            }
        }
        graphVisualizer.getChildren().add(chart);
    }
}
