/*
 *  Copyright 2015 eccentric_nz.
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
public class TARDISSonicBlasterUtils {

	public static boolean checkBlasterInHand(Player p) {
		ItemStack is = p.getItemInHand();

		if (!is.getType().equals(Material.GOLDEN_HOE)) {
			return false;
		}
		ItemMeta im = is.getItemMeta();
		assert im != null;
		if (!im.hasDisplayName()) {
			return false;
		}
		return im.getDisplayName().equals("Sonic Blaster");
	}

	public static float getLineOfSightAngle(Player p) {
		return p.getLocation().getPitch();
	}

	public static double getDistanceToTargetBlock(Location b, Player p) {
		Location l = p.getLocation();
		return b.distance(l);
	}
}
