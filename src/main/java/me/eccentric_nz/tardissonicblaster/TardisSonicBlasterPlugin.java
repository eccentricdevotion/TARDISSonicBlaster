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

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.api.TardisAPI;
import me.eccentric_nz.TARDIS.files.TARDISFileCopier;
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

public class TardisSonicBlasterPlugin extends JavaPlugin {

    private final List<UUID> isBlasting = new ArrayList<>();
    private String pluginName;
    private TardisAPI tardisApi;
    private TARDIS tardis;
    private FileConfiguration recipesConfig;
    private double maxUsableDistance;

    @Override
    public void onDisable() {
        // TODO Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PluginManager pluginManager = getServer().getPluginManager();
        /*
         * Get TARDIS
         */
        Plugin plugin = pluginManager.getPlugin("TARDIS");
        if (plugin == null || !plugin.isEnabled()) {
            System.err.println("[TARDISSonicBlaster] Cannot find TARDIS!");
            pluginManager.disablePlugin(this);
            return;
        }
        tardis = (TARDIS) plugin;
        tardisApi = tardis.getTardisAPI();
        PluginDescriptionFile pdfFile = getDescription();
        pluginName = ChatColor.GOLD + "[" + pdfFile.getName() + "]" + ChatColor.RESET + " ";
        pluginManager.registerEvents(new TardisSonicBlasterListener(this), this);
        pluginManager.registerEvents(new TardisSonicBlasterCraftListener(), this);
        loadRecipes();
        maxUsableDistance = Math.sqrt(getConfig().getDouble("max_blocks"));
    }

    private void loadRecipes() {
        copy("recipes.yml");
        this.recipesConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "recipes.yml"));
        new TardisSonicBlasterRecipe(this).addShapedRecipes();
    }

    /**
     * Copies the schematic file to the TARDISSonicBlaster plugin directory if
     * it is not present.
     *
     * @param fileName the name of the file to copy
     * @return a File
     */
    public File copy(String fileName) {
        String filePath = getDataFolder() + File.separator + fileName;
        InputStream inputStream = getResource(fileName);
        return TARDISFileCopier.copy(filePath, inputStream, false);
    }

    public TardisAPI getTardisApi() {
        return tardisApi;
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
