/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardissonicblaster;

import java.util.Arrays;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author eccentric_nz
 */
public class TARDISSonicBlasterRecipe {

    private final TARDISSonicBlaster plugin;

    public TARDISSonicBlasterRecipe(TARDISSonicBlaster plugin) {
        this.plugin = plugin;
    }

    public void addShapedRecipes() {
        Set<String> shaped = plugin.getRecipesConfig().getConfigurationSection("").getKeys(false);
        for (String s : shaped) {
            plugin.getServer().addRecipe(makeRecipe(s));
        }
    }

    public ShapedRecipe makeRecipe(String s) {
        String[] result_iddata = plugin.getRecipesConfig().getString(s + ".result").split(":");
        Material mat = Material.valueOf(result_iddata[0]);
        int amount = plugin.getRecipesConfig().getInt(s + ".amount");
        ItemStack is;
        if (result_iddata.length == 2) {
            short result_data = Short.parseShort(result_iddata[1]);
            is = new ItemStack(mat, amount, result_data);
        } else {
            is = new ItemStack(mat, amount);
        }
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(s);
        if (!plugin.getRecipesConfig().getString(s + ".lore").isEmpty()) {
            im.setLore(Arrays.asList(plugin.getRecipesConfig().getString(s + ".lore").split("~")));
        }
        im.setCustomModelData(10000002);
        is.setItemMeta(im);
        ShapedRecipe r = new ShapedRecipe(is);
        // get shape
        try {
            String[] shape_tmp = plugin.getRecipesConfig().getString(s + ".shape").split(",");
            String[] shape = new String[3];
            for (int i = 0; i < 3; i++) {
                shape[i] = shape_tmp[i].replaceAll("-", " ");
            }
            r.shape(shape[0], shape[1], shape[2]);
            Set<String> ingredients = plugin.getRecipesConfig().getConfigurationSection(s + ".ingredients").getKeys(false);
            for (String g : ingredients) {
                char c = g.charAt(0);
                String[] recipe_iddata = plugin.getRecipesConfig().getString(s + ".ingredients." + g).split(":");
                Material m = Material.valueOf(recipe_iddata[0]);
                if (recipe_iddata.length == 2) {
                    int recipe_data = Integer.parseInt(recipe_iddata[1]);
                    r.setIngredient(c, m, recipe_data);
                } else {
                    r.setIngredient(c, m);
                }
            }
        } catch (IllegalArgumentException e) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginName() + ChatColor.RED + "Recipe failed! " + ChatColor.RESET + "Check the config file!");
        }
        // add the recipe to TARDIS' list
        plugin.getTardisAPI().getShapedRecipes().put(s, r);
        // return the recipe
        return r;
    }
}
