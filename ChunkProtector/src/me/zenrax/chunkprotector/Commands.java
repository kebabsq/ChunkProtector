package me.zenrax.chunkprotector;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Commands implements CommandExecutor {

    private final ChunkProtector plugin;

    public Commands(ChunkProtector plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Bu komut sadece oyuncular tarafından kullanılabilir.");
            return true;
        }
        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();
        ClaimManager manager = plugin.getClaimManager();

        switch (label.toLowerCase()) {
            case "claim":
                if (manager.claimChunk(chunk, player.getName())) {
                    player.sendMessage("§aBu chunk artık sana ait!");
                } else {
                    player.sendMessage("§cBu chunk zaten alınmış!");
                }
                break;
            case "unclaim":
                if (manager.unclaimChunk(chunk, player.getName())) {
                    player.sendMessage("§aClaim kaldırıldı.");
                } else {
                    player.sendMessage("§cBu chunk sana ait değil.");
                }
                break;
            case "claims":
                List<String> claims = manager.getClaims(player.getName());
                if (claims.isEmpty()) {
                    player.sendMessage("§eHiç claim'in yok.");
                } else {
                    player.sendMessage("§aClaim'lerin:");
                    for (String c : claims) {
                        player.sendMessage("- " + c);
                    }
                }
                break;
        }
        return true;
    }
}
