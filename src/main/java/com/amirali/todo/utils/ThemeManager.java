package com.amirali.todo.utils;

import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class ThemeManager {

    public static void setTheme(@NotNull Scene scene, @NotNull Theme theme) {
        if (scene.getStylesheets().isEmpty())
            scene.getStylesheets().add(theme.getThemePath());
        else
            scene.getStylesheets().set(0, theme.getThemePath());

        try {
            save(theme);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Theme loadTheme() {
        try {
            var properties = new Properties();
            var file = new File(Environment.getAppFolder() + File.separator + "theme.properties");
            if (file.exists()) {
                properties.load(new FileInputStream(file));
                return Theme.themeNameOf(properties.getProperty("theme"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Theme.LIGHT;
    }

    private static void save(@NotNull Theme theme) throws IOException {
        var properties = new Properties();
        var file = new File(Environment.getAppFolder() + File.separator + "theme.properties");
        if (file.exists()) {
            properties.load(new FileInputStream(file));
            var themeName = properties.getProperty("theme");
            if (themeName == null || themeName.equalsIgnoreCase(theme.getThemeName()))
                return;
        }

        properties.setProperty("theme", theme.getThemeName());
        properties.store(new FileOutputStream(Environment.getAppFolder() + File.separator + "theme.properties"), "DO NOT EDIT");
    }
}
