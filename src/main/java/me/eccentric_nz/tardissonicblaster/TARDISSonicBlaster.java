package me.eccentric_nz.tardissonicblaster;

import me.eccentric_nz.tardis.TARDISPlugin;
import me.eccentric_nz.tardis.api.TARDISAPI;
import me.eccentric_nz.tardis.files.TARDISFileCopier;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TARDISSonicBlaster extends JavaPlugin {

	private final List<UUID> isBlasting = new ArrayList<>();
	private String pluginName;
	private TARDISAPI tardisAPI;
	private TARDISPlugin tardis;
	private FileConfiguration recipesConfig;
	private double maxUsableDistance;

	@Override
	public void onDisable() {
		// TODO: Place any custom disable code here.
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();
		PluginManager pm = getServer().getPluginManager();
		/*
		 * Get TARDIS
		 */
		Plugin p = pm.getPlugin("TARDIS");
		if (p == null || !p.isEnabled()) {
			System.err.println("[TARDISSonicBlaster] Cannot find TARDIS!");
			pm.disablePlugin(this);
			return;
		}
		tardis = (TARDISPlugin) p;
		tardisAPI = tardis.getTardisAPI();
		PluginDescriptionFile pdfFile = getDescription();
		pluginName = ChatColor.GOLD + "[" + pdfFile.getName() + "]" + ChatColor.RESET + " ";
		pm.registerEvents(new TARDISSonicBlasterListener(this), this);
		pm.registerEvents(new TARDISSonicBlasterCraftListener(), this);
		loadRecipes();
		maxUsableDistance = Math.sqrt(getConfig().getDouble("max_blocks"));
	}

	private void loadRecipes() {
		copy("recipes.yml");
		this.recipesConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "recipes.yml"));
		new TARDISSonicBlasterRecipe(this).addShapedRecipes();
	}

	/**
	 * Copies the schematic file to the TARDISSonicBlaster plugin directory if
	 * it is not present.
	 *
	 * @param filename the name of the file to copy
	 * @return a File
	 */
	public File copy(String filename) {
		String filepath = getDataFolder() + File.separator + filename;
		InputStream in = getResource(filename);
		return TARDISFileCopier.copy(filepath, in, false);
	}

	public TARDISAPI getTardisAPI() {
		return tardisAPI;
	}

	public String getPluginName() {
		return pluginName;
	}

	public FileConfiguration getRecipesConfig() {
		return recipesConfig;
	}

	public double getMaxUsableDistance() {
		return maxUsableDistance;
	}

	public List<UUID> getIsBlasting() {
		return isBlasting;
	}
}
