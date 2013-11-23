package me.parapenguin.parapgm.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NMSUtils {

    public static String getPackageName() {
        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static Class<?> getClass(String classname) {
        try {
            return Class.forName(getPackageName() + (classname.endsWith(".") ? classname : "." + classname));
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    public static Object getCraftPlayer(Player player) {
        try {
            return player.getClass().getMethod("getHandle").invoke(player);
        } catch (Exception ex) {
            return null;
        }
    }


}
