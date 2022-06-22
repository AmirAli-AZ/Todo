package com.amirali.todo.utils;

import java.util.Objects;

public enum Theme {
    LIGHT(Objects.requireNonNull(Theme.class.getResource("/com/amirali/todo/themes/light-theme.css")).toExternalForm(), "light"),
    DARK(Objects.requireNonNull(Theme.class.getResource("/com/amirali/todo/themes/dark-theme.css")).toExternalForm(), "dark");

    private final String themePath, themeName;
    Theme(String themePath, String themeName) {
        this.themePath = themePath;
        this.themeName = themeName;
    }

    public String getThemePath() {
        return themePath;
    }

    public String getThemeName() {
        return themeName;
    }

    public static Theme themeNameOf(String themeName) {
        if (themeName == null || themeName.equalsIgnoreCase("light"))
            return LIGHT;
        else
            return DARK;
    }
}
