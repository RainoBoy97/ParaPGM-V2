package me.parapenguin.parapgm.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Shapes {

    public static boolean isInside(Player player, Location corner1, Location corner2) {
        return isInside(player.getLocation(), corner1, corner2);
    }

    public static Boolean isInside(Location loc, Location corner1, Location corner2) {
        double xMin, xMax, yMin, yMax, zMin, zMax = 0;

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        xMin = Math.min(corner1.getX(), corner2.getX());
        xMax = Math.max(corner1.getX(), corner2.getX());

        yMin = Math.min(corner1.getY(), corner2.getY());
        yMax = Math.max(corner1.getY(), corner2.getY());

        zMin = Math.min(corner1.getZ(), corner2.getZ());
        zMax = Math.max(corner1.getZ(), corner2.getZ());

        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);
    }

    public static List<Block> getInsideCuboid(Location corner1, Location corner2) {
        List<Block> blocks = new ArrayList<Block>();

        int xMin, xMax, yMin, yMax, zMin, zMax = 0;

        xMin = Math.min(corner1.getBlockX(), corner2.getBlockX());
        xMax = Math.max(corner1.getBlockX(), corner2.getBlockX());

        yMin = Math.min(corner1.getBlockY(), corner2.getBlockY());
        yMax = Math.max(corner1.getBlockY(), corner2.getBlockY());

        zMin = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
        zMax = Math.max(corner1.getBlockZ(), corner2.getBlockZ());

        World world = corner1.getWorld();

        int px = xMin;
        while (px <= xMax) {
            int py = yMin;
            while (py <= yMax) {
                int pz = zMin;
                while (pz <= zMax) {
                    blocks.add(world.getBlockAt(new Location(world, px, py, pz)));
                    pz++;
                }
                py++;
            }
            px++;
        }

        return blocks;
    }

    public static List<Location> getCircle(Location loc, int radius, int height, boolean hollow, boolean sphere, int plus_y) {
        List<Location> locs = new ArrayList<Location>();

        World world = loc.getWorld();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();

        for (int x = cx - height; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                for (int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        locs.add(new Location(world, x, y + plus_y, z));
                    }
                }
            }
        }
        return locs;
    }

}
