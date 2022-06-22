package com.amirali.todo.utils;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class ModalDialog extends Stage {

    private final Parent ownerRoot;

    private final GaussianBlur gaussianBlur = new GaussianBlur(0);

    private final ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300));

    private final DoubleProperty blurRadiusProperty = new SimpleDoubleProperty(10);

    public ModalDialog(@NotNull Window owner, @NotNull Parent parent) {
        super(StageStyle.TRANSPARENT);

        ownerRoot = owner.getScene().getRoot();
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        var bounds = ownerRoot.localToScreen(ownerRoot.getBoundsInLocal());
        setScene(new Scene(parent, bounds.getWidth() / 2, bounds.getHeight() / 2, Color.TRANSPARENT));
        ThemeManager.setTheme(getScene(), ThemeManager.loadTheme());

        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setNode(parent);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.currentTimeProperty().addListener((observableValue, oldValue, newValue) -> {
            var radius = (newValue.toMillis() / scaleTransition.getDuration().toMillis()) * blurRadiusProperty.doubleValue();
            gaussianBlur.setRadius(radius);
        });
    }

    public void openDialog() {
        show();

        var bounds = ownerRoot.localToScreen(ownerRoot.getBoundsInLocal());
        setX(bounds.getMinX() + (bounds.getWidth() - getWidth()) / 2);
        setY(bounds.getMinY() + (bounds.getHeight() - getHeight()) / 2);
        ownerRoot.setEffect(gaussianBlur);
        scaleTransition.play();
    }

    public void closeDialog() {
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.setOnFinished(actionEvent -> {
            close();
            ownerRoot.setEffect(null);
        });
        scaleTransition.playFrom(scaleTransition.getDuration());
    }

    public void setBlurRadius(double radius) {
        blurRadiusProperty.set(radius);
    }

    public double getBlurRadius() {
        return blurRadiusProperty.get();
    }

    public DoubleProperty blurRadiusProperty() {
        return blurRadiusProperty;
    }
}
