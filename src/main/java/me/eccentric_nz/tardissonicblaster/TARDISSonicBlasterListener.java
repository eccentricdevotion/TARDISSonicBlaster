package me.eccentric_nz.tardissonicblaster;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class TARDISSonicBlasterListener implements Listener {

    private final TARDISSonicBlaster plugin;

    public TARDISSonicBlasterListener(TARDISSonicBlaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
    }
}
