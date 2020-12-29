import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author Nicholas Nyvall (nnyvall@wisc.edu)
 * This class handles the graphical components of the application, excluding algorithm animations.
 */
public class ViewController extends BorderPane {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private final GraphVisualizer graphVisualizer;

    final ChoiceBox<String> algoSelect;
    final Slider numBarSelect;
    final Label numBarLabel;
    final Button resetButton;

    final int componentWidth = 200;
    final int componentHeight = 60;

    /**
     * This is a constructor method for ViewController. It creates two primary GUI sections, an HBox at the bottom to
     * contain buttons, sliders, choice-boxes, labels, etc. and a GraphVisualizer to visually represent the array and
     * control animations.
     */
    public ViewController() {

        HBox buttonContainer = new HBox();
        this.graphVisualizer = new GraphVisualizer();
        this.setCenter(graphVisualizer);
        this.setBottom(buttonContainer);

        this.algoSelect = new ChoiceBox<>();
        this.numBarSelect = new Slider(25, 100, 50);
        this.numBarLabel = new Label("Bars: 50");
        this.resetButton = new Button("Reset");
        initComponents();

        buttonContainer.getChildren().addAll(algoSelect, numBarSelect, numBarLabel, resetButton);
        buttonContainer.setAlignment(Pos.CENTER);

        for (Node node : buttonContainer.getChildren()) {
            HBox.setMargin(node, new Insets(20, 20, 20, 20));
        }

        setStyle("-fx-background-color: #353839; ");
        graphVisualizer.setMaxSize(WIDTH - 40, HEIGHT - 150);
        graphVisualizer.createChart(GraphVisualizer.array, graphVisualizer);
    }

    /**
     * This method initializes the components of the application including buttons, sliders, choice-boxes, and labels.
     * It assigns them actions accordingly.
     */
    public void initComponents() {
        //Initializing algorithm selection ChoiceBox
        algoSelect.getItems().addAll("- Select Algorithm -", "Bubble Sort", "Selection Sort", "Insertion Sort");
        algoSelect.setMinSize(componentWidth, componentHeight);
        algoSelect.setMaxSize(componentWidth, componentHeight);
        algoSelect.setValue("- Select Algorithm -");
        algoSelect.setOnAction((event) -> {
            String value = algoSelect.getValue();
            switch (value) {
                case "Bubble Sort":
                    graphVisualizer.bubbleTimer.start();
                    break;
                case "Selection Sort":
                    graphVisualizer.selectionTimer.start();
                    break;
                case "Insertion Sort":
                    graphVisualizer.insertionTimer.start();
                    break;
            }
        });

        //Initializing the bar number Slider
        numBarSelect.setShowTickLabels(true);
        numBarSelect.setShowTickMarks(true);
        numBarSelect.setSnapToTicks(true);
        numBarSelect.setMinSize(componentWidth, componentHeight);
        numBarSelect.setMaxSize(componentWidth, componentHeight);
        numBarSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            GraphVisualizer.numBars = newValue.intValue();
            GraphVisualizer.array = GraphVisualizer.createRandomArray(GraphVisualizer.numBars);
            graphVisualizer.createChart(GraphVisualizer.array, graphVisualizer);
            numBarLabel.setText("Bars: " + GraphVisualizer.numBars);
            graphVisualizer.resetFields();
        });

        //Initializing the number of bars Label
        numBarLabel.setId("numBarLabel");
        numBarLabel.setAlignment(Pos.CENTER);
        numBarLabel.setMinSize(componentWidth, componentHeight);
        numBarLabel.setMaxSize(componentWidth, componentHeight);

        //Initializing the reset Button
        resetButton.setMinSize(componentWidth, componentHeight);
        resetButton.setMaxSize(componentWidth, componentHeight);
        resetButton.setOnMousePressed(actionEvent -> graphVisualizer.resetFields());
    }
}
