package com.amirali.todo.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.jetbrains.annotations.NotNull;

public class PopupMessage extends Popup {

    private final StackPane root = new StackPane();

    private final Text messageLabel = new Text();

    public PopupMessage(@NotNull String message) {
        messageLabel.setText(message);

        root.setPrefHeight(55);
        root.setAlignment(Pos.TOP_LEFT);
        root.setEffect(new DropShadow());
        var shape = new SVGPath();
        shape.setContent("M -18.655797 -0.19022129 C -19.178484 -0.19022129 -19.598878 0.098052895 -19.598878 0.45573697 L -19.598878 5.1902032 C -19.598878 5.5478873 -19.178484 5.8361615 -18.655797 5.8361615 L 0.39409321 5.8361615 L 2.2578008 9.0641798 L 4.1223721 5.8361615 L 23.172262 5.8361615 C 23.694949 5.8361615 24.115343 5.5478873 24.115343 5.1902032 L 24.115343 0.45573697 C 24.115343 0.098052895 23.694949 -0.19022129 23.172262 -0.19022129 L -18.655797 -0.19022129 z");
        root.setShape(shape);
        root.setPadding(new Insets(3));

        root.getChildren().add(messageLabel);

        getContent().add(root);
        setAutoHide(true);
    }

    public void setColor(@NotNull Paint paint) {
        root.setStyle("-fx-background-color: #" + Integer.toHexString(paint.hashCode()) + ";");
    }

    public void setTextColor(@NotNull Paint paint) {
        messageLabel.setFill(paint);
    }
}
