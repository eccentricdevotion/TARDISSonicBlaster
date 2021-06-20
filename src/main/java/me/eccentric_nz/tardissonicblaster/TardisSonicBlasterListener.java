/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardissonicblaster;

import me.eccentric_nz.tardis.enumeration.CardinalDirection;
import me.eccentric_nz.tardis.utility.TardisStaticUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.UUID;

public class TardisSonicBlasterListener implements Listener {

    private final TardisSonicBlasterPlugin plugin;

    public TardisSonicBlasterListener(TardisSonicBlasterPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (TardisSonicBlasterUtils.checkBlasterInHand(player)) {
                UUID uuid = player.getUniqueId();
                if (!plugin.getIsBlasting().contains(uuid)) {
                    // get distance
                    Location target = Objects.requireNonNull(event.getClickedBlock()).getLocation();
                    double distance = TardisSonicBlasterUtils.getDistanceToTargetBlock(target, player);
                    double angle = TardisSonicBlasterUtils.getLineOfSightAngle(player);
                    CardinalDirection direction = CardinalDirection.valueOf(TardisStaticUtils.getPlayersDirection(player, false));
                    new TardisSonicBlasterAction(plugin).blast(target, direction, angle, distance, uuid);
                }
            }
        }
    }
}
