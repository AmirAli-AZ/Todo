package com.amirali.todo;

import com.amirali.todo.model.Todo;
import com.amirali.todo.utils.DBManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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

    @FXML
    public void save(ActionEvent actionEvent) {
        currentTodo.setTitle(title.getText());
        currentTodo.setDescription(description.getText());
        currentTodo.setDone(done.isSelected());

        baseList.set(currentIndex, DBManager.getInstance().updateTodo(currentTodo));
    }

    public void openEditor(@NotNull ObservableList<Todo> baseList, @NotNull Todo currentTodo, int currentIndex, @NotNull StackPane editorContainer) {
        if (!editorContainer.getChildren().isEmpty())
            editorContainer.getChildren().clear();
        editorContainer.getChildren().add(root);

        currentTodo.setEditorOpened(true);

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
        editorContainer.getChildren().clear();
        currentTodo.setEditorOpened(false);
    }

    public void setDone(boolean done) {
        this.done.setSelected(done);
    }
}
