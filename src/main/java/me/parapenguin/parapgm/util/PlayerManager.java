package me.parapenguin.parapgm.util;

import net.minecraft.server.v1_6_R3.Packet20NamedEntitySpawn;
import net.minecraft.server.v1_6_R3.Packet29DestroyEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerManager {

    public static void hidePlayer(Player toHide, Player... canSee) {
        for (Player a : Bukkit.getOnlinePlayers()) {
            boolean foundPlayer = a.equals(toHide);

            for (Player b : canSee) {
                foundPlayer = a.equals(b);
            }

            if (foundPlayer) {
                continue;
            }

            ((CraftPlayer) a).getHandle().playerConnection.sendPacket(new Packet29DestroyEntity(((CraftPlayer) toHide).getHandle().id));
        }
    }

    public static void showPlayer(Player toShow, Player... cantSee) {
        for (Player a : Bukkit.getOnlinePlayers()) {
            boolean foundPlayer = a.equals(toShow);

            for (Player b : cantSee) {
                foundPlayer = a.equals(b);
            }

            if (foundPlayer)
                continue;

            ((CraftPlayer) a).getHandle().playerConnection.sendPacket(new Packet20NamedEntitySpawn(((CraftPlayer) toShow).getHandle()));
        }
    }

    public static void playerBackToPlayer(Player toShow, Player... cantSee) {
        for (Player a : Bukkit.getOnlinePlayers()) {
            boolean foundPlayer = a.equals(toShow);

            for (Player b : cantSee) {
                foundPlayer = a.equals(b);
            }

            if (foundPlayer)
                continue;

            ((CraftPlayer) a).getHandle().playerConnection.sendPacket(new Packet29DestroyEntity(((CraftPlayer) toShow).getHandle().id));
            ((CraftPlayer) a).getHandle().playerConnection.sendPacket(new Packet20NamedEntitySpawn(((CraftPlayer) toShow).getHandle()));
        }
    }

    public static void hidePlayers(Player toHide, Player... exempt) {
        for (Player a : Bukkit.getOnlinePlayers()) {
            boolean foundPlayer = a.equals(toHide);

            for (Player b : exempt) {
                foundPlayer = a.equals(b);
            }

            if (foundPlayer)
                continue;

            ((CraftPlayer) toHide).getHandle().playerConnection.sendPacket(new Packet29DestroyEntity(((CraftPlayer) a).getHandle().id));
        }
    }

}