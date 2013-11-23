package me.parapenguin.parapgm;

import java.util.logging.Level;

public class ServerLog {

    public static void log(Level log, String message) {
        ParaPGM.getInstance().getLogger().log(log, message);
    }

    public static void info(String message) {
        ParaPGM.getInstance().getLogger().info(message);
    }

    public static void warning(String message) {
        ParaPGM.getInstance().getLogger().warning(message);
    }

    public static void severe(String message) {
        ParaPGM.getInstance().getLogger().severe(message);
    }

}
