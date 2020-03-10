import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) throws Exception {
        stage.setTitle("Company Director 1.0");
        stage.setResizable(false);
        stage.setMaxHeight(410.0);
        stage.setMaxWidth(600.0);
        stage.setX(650.0);
        stage.setY(225.0);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
