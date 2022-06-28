package com.amirali.todo;

import com.amirali.todo.model.Todo;
import com.amirali.todo.utils.DBManager;
import com.amirali.todo.utils.ModalDialog;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class DeleteDialogController {

    @FXML
    private Label title, message;

    private ModalDialog modalDialog;

    private ObservableList<Todo> baseList;

    private Todo currentTodo;

    private EditorController editorController;

    @FXML
    public void no(ActionEvent actionEvent) {
        modalDialog.closeDialog();
    }

    @FXML
    public void yes(ActionEvent actionEvent) {
        var removedTodo = DBManager.getInstance().remove(currentTodo);
        if (removedTodo != null) {
            editorController.closeEditor();
            baseList.removeIf(todo -> todo.getId() == removedTodo.getId());
        }
        modalDialog.closeDialog();
    }

    public void setData(@NotNull ModalDialog modalDialog, @NotNull ObservableList<Todo> baseList, @NotNull Todo currentTodo, @NotNull EditorController editorController) {
        this.modalDialog = modalDialog;
        this.baseList = baseList;
        this.currentTodo = currentTodo;
        this.editorController = editorController;
    }

    public void setTitle(@NotNull String title) {
        this.title.setText(title);
    }

    public void setMessage(@NotNull String message) {
        this.message.setText(message);
    }
}
