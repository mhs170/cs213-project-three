package banking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionManagerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(TransactionManagerMain.class.getResource(
                        "TransactionManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Project 3 - Transaction Manager");
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(650); //prevent window from being too small
    }

    public static void main(String[] args) {
        launch();
    }
}