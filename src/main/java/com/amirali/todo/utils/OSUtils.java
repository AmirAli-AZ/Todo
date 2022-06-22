package com.amirali.todo.utils;

public final class OSUtils {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isLinux() {
        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
    }
}
