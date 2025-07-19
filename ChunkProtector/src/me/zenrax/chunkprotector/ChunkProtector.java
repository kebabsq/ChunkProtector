package me.zenrax.chunkprotector;

import org.bukkit.plugin.java.JavaPlugin;

public class ChunkProtector extends JavaPlugin {

    private static ChunkProtector instance;
    private ClaimManager claimManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        claimManager = new ClaimManager(this);
        getCommand("claim").setExecutor(new Commands(this));
        getCommand("unclaim").setExecutor(new Commands(this));
        getCommand("claims").setExecutor(new Commands(this));
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
    }

    public static ChunkProtector getInstance() {
        return instance;
    }

    public ClaimManager getClaimManager() {
        return claimManager;
    }
}
