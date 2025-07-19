package me.zenrax.chunkprotector;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.Player;

public class Listeners implements Listener {

    private final ChunkProtector plugin;

    public Listeners(ChunkProtector plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();
        ClaimManager manager = plugin.getClaimManager();

        if (manager.isClaimed(chunk) && !manager.isOwner(chunk, p.getName())) {
            p.sendMessage("§cBu chunk korumalı!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();
        ClaimManager manager = plugin.getClaimManager();

        if (manager.isClaimed(chunk) && !manager.isOwner(chunk, p.getName())) {
            p.sendMessage("§cBu chunk korumalı!");
            event.setCancelled(true);
        }
    }
}
