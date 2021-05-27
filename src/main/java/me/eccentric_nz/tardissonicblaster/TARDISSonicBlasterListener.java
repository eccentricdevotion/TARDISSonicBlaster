package me.eccentric_nz.tardissonicblaster;

import me.eccentric_nz.tardis.enumeration.COMPASS;
import me.eccentric_nz.tardis.utility.TARDISStaticUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.UUID;

public class TARDISSonicBlasterListener implements Listener {

	private final TARDISSonicBlaster plugin;

	public TARDISSonicBlasterListener(TARDISSonicBlaster plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInteract(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (TARDISSonicBlasterUtils.checkBlasterInHand(player)) {
				UUID uuid = player.getUniqueId();
				if (!plugin.getIsBlasting().contains(uuid)) {
					// get distance
					Location target = Objects.requireNonNull(event.getClickedBlock()).getLocation();
					double distance = TARDISSonicBlasterUtils.getDistanceToTargetBlock(target, player);
					double angle = TARDISSonicBlasterUtils.getLineOfSightAngle(player);
					COMPASS direction = COMPASS.valueOf(TARDISStaticUtils.getPlayersDirection(player, false));
					new TARDISSonicBlasterAction(plugin).blast(target, direction, angle, distance, 100, uuid);
				}
			}
		}
	}
}
