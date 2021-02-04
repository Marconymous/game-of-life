import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOfLife extends Application {
    Canvas canvas = new Canvas(900, 900);
    LogicHandler logic = new LogicHandler(canvas);

    @Override
    public void start(Stage primaryStage) {
        HBox menu = new HBox();
        VBox root = new VBox(menu);
        Scene scene = new Scene(root);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(50), kv -> {
            logic.next();
        }));
        animation.setCycleCount(Animation.INDEFINITE);

        Button confirm = new Button("Start");
        confirm.setOnAction(e -> {
            animation.play();
            root.getChildren().add(canvas);
            root.getChildren().remove(1);
            root.getChildren().remove(0);
        });
        menu.getChildren().add(confirm);

        root.getChildren().add(logic.setup());

        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
