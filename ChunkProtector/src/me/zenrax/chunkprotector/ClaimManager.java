package me.zenrax.chunkprotector;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class ClaimManager {
    private final ChunkProtector plugin;
    private final FileConfiguration config;

    public ClaimManager(ChunkProtector plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public String getChunkKey(Chunk chunk) {
        return chunk.getX() + "," + chunk.getZ();
    }

    public boolean isClaimed(Chunk chunk) {
        String key = getChunkKey(chunk);
        return config.getString("claims." + chunk.getWorld().getName() + "." + key) != null;
    }

    public boolean isOwner(Chunk chunk, String player) {
        String key = getChunkKey(chunk);
        String owner = config.getString("claims." + chunk.getWorld().getName() + "." + key);
        return player.equals(owner);
    }

    public boolean claimChunk(Chunk chunk, String player) {
        String key = getChunkKey(chunk);
        String world = chunk.getWorld().getName();
        if (isClaimed(chunk)) return false;
        config.set("claims." + world + "." + key, player);
        plugin.saveConfig();
        return true;
    }

    public boolean unclaimChunk(Chunk chunk, String player) {
        String key = getChunkKey(chunk);
        String world = chunk.getWorld().getName();
        if (!isOwner(chunk, player)) return false;
        config.set("claims." + world + "." + key, null);
        plugin.saveConfig();
        return true;
    }

    public List<String> getClaims(String player) {
        List<String> list = new ArrayList<>();
        for (String world : config.getConfigurationSection("claims").getKeys(false)) {
            for (String chunkKey : config.getConfigurationSection("claims." + world).getKeys(false)) {
                if (player.equals(config.getString("claims." + world + "." + chunkKey))) {
                    list.add(world + ": " + chunkKey);
                }
            }
        }
        return list;
    }
}
