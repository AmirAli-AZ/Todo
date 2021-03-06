package com.amirali.todo;

import com.amirali.todo.model.Todo;
import com.amirali.todo.utils.DBManager;
import com.amirali.todo.utils.ModalDialog;
import com.amirali.todo.utils.PopupMessage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class CreatorDialogController {

    @FXML
    private TextArea description;

    @FXML
    private TextField title;

    private ModalDialog modalDialog;

    private ObservableList<Todo> baseList;

    @FXML
    public void cancel(ActionEvent event) {
        modalDialog.closeDialog();
    }

    @FXML
    public void create(ActionEvent event) {
        var keywordEntered = false;
        for (Keywords keyword : Keywords.values()) {
            if (title.getText().equalsIgnoreCase(keyword.getKeyword())) {
                keywordEntered = true;
                break;
            }
        }

        if (keywordEntered) {
            var errorMessage = new PopupMessage("cannot set keywords");
            errorMessage.setColor(Color.RED);
            errorMessage.setTextColor(Color.WHITE);

            errorMessage.show(title.getScene().getWindow());

            var bounds = title.localToScreen(title.getBoundsInLocal());
            errorMessage.setX(bounds.getMinX() + (bounds.getWidth() - errorMessage.getWidth()) / 2);
            errorMessage.setY(bounds.getMinY() - errorMessage.getHeight());

            return;
        }

        baseList.add(0, DBManager.getInstance().insertNewTodo(title.getText(), description.getText()));
        modalDialog.closeDialog();
    }

    public void setData(@NotNull ModalDialog modalDialog, @NotNull ObservableList<Todo> baseList) {
        this.modalDialog = modalDialog;
        this.baseList = baseList;
    }

}
