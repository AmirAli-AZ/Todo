package com.amirali.todo;

import com.amirali.todo.utils.ThemeManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    public static final double WIDTH = 800, HEIGHT = 500;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Todo");
        var loader = new FXMLLoader(getClass().getResource("app-view.fxml"));
        var scene = new Scene(loader.load(), WIDTH, HEIGHT);
        ThemeManager.setTheme(scene, ThemeManager.loadTheme());
        stage.setScene(scene);
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("todo-icon.png"))));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
