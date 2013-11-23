package me.parapenguin.parapgm.player;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Client {

    @Getter public static List<Client> clients = new ArrayList<Client>();

    @Getter Client client;
    @Getter Player player;

    public Client(Player player) {
        this.player = player;
        client = this;
    }

    public void add() {
        clients.add(client);
    }

    public static Client getClient(Player player) {
        for (Client client : clients)
            if (client.player == player)
                return client;
        return null;
    }

    public static Client getClient(String username) {
        for (Client client : clients)
            if (client.getPlayer().getName().equalsIgnoreCase(username))
                return client;
        return null;
    }

}
