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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author eccentric_nz
 */
public class TardisSonicBlasterUtils {

    public static boolean checkBlasterInHand(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (!itemStack.getType().equals(Material.GOLDEN_HOE)) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        if (!itemMeta.hasDisplayName()) {
            return false;
        }
        return itemMeta.getDisplayName().equals("Sonic Blaster");
    }

    public static float getLineOfSightAngle(Player player) {
        return player.getLocation().getPitch();
    }

    public static double getDistanceToTargetBlock(Location target, Player player) {
        Location loc = player.getLocation();
        return target.distance(loc);
    }
}
