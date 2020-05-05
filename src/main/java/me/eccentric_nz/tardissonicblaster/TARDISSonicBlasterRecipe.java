/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardissonicblaster;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.enumeration.RECIPE_ITEM;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author eccentric_nz
 */
public class TARDISSonicBlasterRecipe {

    private final TARDISSonicBlaster plugin;
    private final HashMap<String, Integer> modelData = new HashMap<>();

    public TARDISSonicBlasterRecipe(TARDISSonicBlaster plugin) {
        this.plugin = plugin;
        modelData.put("Sonic Blaster", 10000001);
        modelData.put("Blaster Battery", 10000002);
        modelData.put("Landing Pad", 10000001);
    }

    public void addShapedRecipes() {
        Set<String> shaped = plugin.getRecipesConfig().getConfigurationSection("").getKeys(false);
        for (String s : shaped) {
            plugin.getServer().addRecipe(makeRecipe(s));
        }
    }

    public ShapedRecipe makeRecipe(String s) {
        Material mat = Material.valueOf(plugin.getRecipesConfig().getString(s + ".result"));
        int amount = plugin.getRecipesConfig().getInt(s + ".amount");
        ItemStack is = new ItemStack(mat, amount);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(s);
        if (!plugin.getRecipesConfig().getString(s + ".lore").isEmpty()) {
            im.setLore(Arrays.asList(plugin.getRecipesConfig().getString(s + ".lore").split("~")));
        }
        im.setCustomModelData(modelData.get(s));
        is.setItemMeta(im);
        NamespacedKey key = new NamespacedKey(TARDIS.plugin, s.replace(" ", "_").toLowerCase(Locale.ENGLISH));
        ShapedRecipe r = new ShapedRecipe(key, is);
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
                String ingredient = plugin.getRecipesConfig().getString(s + ".ingredients." + g);
                if (ingredient.contains("=")) {
                    ItemStack exact;
                    String[] choice = ingredient.split("=");
                    Material m = Material.valueOf(choice[0]);
                    exact = new ItemStack(m, 1);
                    ItemMeta em = exact.getItemMeta();
                    em.setDisplayName(choice[1]);
                    em.setCustomModelData(RECIPE_ITEM.getByName(choice[1]).getCustomModelData());
                    exact.setItemMeta(em);
                    r.setIngredient(c, new RecipeChoice.ExactChoice(exact));
                } else {
                    Material m = Material.valueOf(ingredient);
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
