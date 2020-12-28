import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Nicholas Nyvall (nnyvall@wisc.edu)
 * This class creates and runs the stage for the application.
 */
public class Main extends Application {

    /**
     * This method creates and initializes the stage for which the application will be displayed and run.
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Sorting Visualizer");
            primaryStage.setScene(new Scene(new ViewController(), ViewController.WIDTH, ViewController.HEIGHT));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method launches the application.
     *
     * @param args command line arguments to launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
