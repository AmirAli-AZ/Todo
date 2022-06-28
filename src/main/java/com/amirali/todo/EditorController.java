package com.amirali.todo;

import com.amirali.todo.model.Todo;
import com.amirali.todo.utils.DBManager;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class EditorController {

    @FXML
    private BorderPane root;

    @FXML
    private CheckBox done;

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    private ObservableList<Todo> baseList;

    private Todo currentTodo;

    private int currentIndex;

    private StackPane editorContainer;

    private boolean editorOpened;

    @FXML
    public void save(ActionEvent actionEvent) {
        currentTodo.setTitle(title.getText());
        currentTodo.setDescription(description.getText());
        currentTodo.setDone(done.isSelected());

        baseList.set(currentIndex, DBManager.getInstance().updateTodo(currentTodo));
    }

    public void openEditor(@NotNull ObservableList<Todo> baseList, @NotNull Todo currentTodo, int currentIndex, @NotNull StackPane editorContainer) {
        root.setOpacity(1);
        editorContainer.getChildren().add(root);

        editorOpened = true;

        title.setText(currentTodo.getTitle());
        description.setText(currentTodo.getDescription());
        done.setSelected(currentTodo.isDone());

        this.baseList = baseList;
        this.currentTodo = currentTodo;
        this.currentIndex = currentIndex;
        this.editorContainer = editorContainer;
    }

    @FXML
    public void close(ActionEvent actionEvent) {
        closeEditor();
    }

    public void setDone(boolean done) {
        this.done.setSelected(done);
    }

    public void closeEditor() {
        if (!editorOpened)
            return;

        var fadeOutAnimation = new FadeTransition(Duration.millis(300), root);
        fadeOutAnimation.setToValue(0);
        fadeOutAnimation.setInterpolator(Interpolator.EASE_OUT);
        fadeOutAnimation.setOnFinished(actionEvent -> {
            editorContainer.getChildren().clear();
            editorOpened = false;
        });
        fadeOutAnimation.play();
    }

    public void setEditorOpened(boolean editorOpened) {
        this.editorOpened = editorOpened;
    }

    public boolean isEditorOpened() {
        return editorOpened;
    }
}
