package com.amirali.todo;

import com.amirali.todo.model.Todo;
import com.amirali.todo.utils.ModalDialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodoItem extends ListCell<Todo> {

    @FXML
    private Label date;

    @FXML
    private AnchorPane root;

    @FXML
    private Label title;

    private FXMLLoader loader;

    private final ObservableList<Todo> baseList;

    private final StackPane editorContainer;

    private EditorController editorController;

    public TodoItem(@NotNull ObservableList<Todo> baseList, @NotNull StackPane editorContainer) {
        this.baseList = baseList;
        this.editorContainer = editorContainer;

        try {
            var loader = new FXMLLoader(getClass().getResource("editor-view.fxml"));
            loader.load();
            editorController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Todo todo, boolean b) {
        super.updateItem(todo, b);

        setText(null);
        if (todo == null || b) {
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("listview-item-view.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setContextMenu(createMenu());
            }

            title.setText(todo.getTitle());
            date.setText(formatDate(todo.getDate()));

            setGraphic(root);
        }
    }

    private ContextMenu createMenu() {
        var deleteIcon = new SVGPath();
        deleteIcon.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2H8c-1.1 0-2 .9-2 2v10zM18 4h-2.5l-.71-.71c-.18-.18-.44-.29-.7-.29H9.91c-.26 0-.52.11-.7.29L8.5 4H6c-.55 0-1 .45-1 1s.45 1 1 1h12c.55 0 1-.45 1-1s-.45-1-1-1z");
        deleteIcon.setFill(Color.RED);
        var deleteItemRoot = new HBox(5, deleteIcon, new Label("Delete"));
        deleteItemRoot.setAlignment(Pos.CENTER_LEFT);
        deleteItemRoot.setPrefWidth(100);
        var deleteItem = new CustomMenuItem(deleteItemRoot, true);
        deleteItem.setOnAction(actionEvent -> {
            try {
                showDeleteDialog();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        var editIcon = new SVGPath();
        editIcon.setContent("M3 17.46v3.04c0 .28.22.5.5.5h3.04c.13 0 .26-.05.35-.15L17.81 9.94l-3.75-3.75L3.15 17.1c-.1.1-.15.22-.15.36zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z");
        editIcon.getStyleClass().add("synced-theme-icons");
        var editItemRoot = new HBox(5, editIcon, new Label("Edit"));
        editItemRoot.setAlignment(Pos.CENTER_LEFT);
        editItemRoot.setPrefWidth(100);
        var editItem = new CustomMenuItem(editItemRoot, true);
        editItem.setOnAction(actionEvent -> {
            if (editorController != null)
                editorController.openEditor(baseList, getItem(), getIndex(), editorContainer);
        });

        return new ContextMenu(deleteItem, editItem);
    }

    private String formatDate(long dateMillis) {
        var date = new Date(dateMillis);
        var currentDateMinusYear = Calendar.getInstance();
        currentDateMinusYear.add(Calendar.YEAR, -1);

        String pattern;
        if (date.before(currentDateMinusYear.getTime()))
            pattern = "dd/MM/yyyy";
        else
            pattern = "MMMM d";

        return new SimpleDateFormat(pattern).format(date);
    }

    private void showDeleteDialog() throws IOException {
        var loader = new FXMLLoader(getClass().getResource("delete-dialog-view.fxml"));
        var modalDialog = new ModalDialog(getScene().getWindow(), loader.load());
        DeleteDialogController controller = loader.getController();
        controller.setTitle("Delete todo");
        controller.setMessage("Do you want to delete this todo?");
        controller.setData(modalDialog, baseList, getItem());

        modalDialog.openDialog();
    }
}
