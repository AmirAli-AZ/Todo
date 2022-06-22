package com.amirali.todo.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Environment {

    public static String getAppFolder() {
        var path = System.getProperty("user.home") + File.separator + "com.amirali.todo";

        if (OSUtils.isWindows())
            path = System.getenv("APPDATA") + File.separator + "com.amirali.todo";

        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}