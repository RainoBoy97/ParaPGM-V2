package me.parapenguin.parapgm.util;

import org.bukkit.Bukkit;

import java.io.*;
import java.net.URL;

public class Downloader {

    public static void downloadFile(File downloading, String url) throws IOException {
        downloadFile(downloading.getName(), downloading.getParentFile(), url);
    }

    public static void downloadFile(String file, File folder, String link) throws IOException {
        long cms = System.currentTimeMillis();
        URL url;

        if (link == null) {
            return;
        } else {
            url = link.endsWith("/") ? new URL(link + "/" + file.replaceAll(" ", "%20")) : new URL(link + file.replaceAll(" ", "%20"));
        }

        InputStream inStream = url.openStream();
        BufferedInputStream bufIn = new BufferedInputStream(inStream);

        File fileWrite = new File(folder + "/" + file);
        OutputStream out = new FileOutputStream(fileWrite);
        BufferedOutputStream bufOut = new BufferedOutputStream(out);
        byte buffer[] = new byte[1024];
        while (true) {
            int nRead = bufIn.read(buffer, 0, buffer.length);
            if (nRead <= 0) break;
            bufOut.write(buffer, 0, nRead);
        }

        bufOut.flush();
        out.close();
        inStream.close();

        Bukkit.getServer().getLogger().info("Downloaded '" + file + "' to " + folder.getPath() + ", taking " + (System.currentTimeMillis() - cms) + "ms.");
    }

}
