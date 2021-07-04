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
import me.eccentric_nz.TARDIS.enumeration.RecipeItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * @author eccentric_nz
 */
public class TardisSonicBlasterRecipe {

    private final TardisSonicBlasterPlugin plugin;
    private final HashMap<String, Integer> modelData = new HashMap<>();

    public TardisSonicBlasterRecipe(TardisSonicBlasterPlugin plugin) {
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
        Material material = Material.valueOf(plugin.getRecipesConfig().getString(s + ".result"));
        int amount = plugin.getRecipesConfig().getInt(s + ".amount");
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(s);
        if (!plugin.getRecipesConfig().getString(s + ".lore").isEmpty()) {
            itemMeta.setLore(Arrays.asList(plugin.getRecipesConfig().getString(s + ".lore").split("~")));
        }
        itemMeta.setCustomModelData(modelData.get(s));
        itemStack.setItemMeta(itemMeta);
        NamespacedKey key = new NamespacedKey(TARDIS.plugin, s.replace(" ", "_").toLowerCase(Locale.ENGLISH));
        ShapedRecipe recipe = new ShapedRecipe(key, itemStack);
        // get shape
        try {
            String[] shape_tmp = plugin.getRecipesConfig().getString(s + ".shape").split(",");
            String[] shape = new String[3];
            for (int i = 0; i < 3; i++) {
                shape[i] = shape_tmp[i].replaceAll("-", " ");
            }
            recipe.shape(shape[0], shape[1], shape[2]);
            Set<String> ingredients = plugin.getRecipesConfig().getConfigurationSection(s + ".ingredients").getKeys(false);
            for (String g : ingredients) {
                char c = g.charAt(0);
                String ingredient = plugin.getRecipesConfig().getString(s + ".ingredients." + g);
                if (ingredient.contains("=")) {
                    ItemStack exact;
                    String[] choice = ingredient.split("=");
                    Material m = Material.valueOf(choice[0]);
                    exact = new ItemStack(m, 1);
                    ItemMeta exactItemMeta = exact.getItemMeta();
                    exactItemMeta.setDisplayName(choice[1]);
                    exactItemMeta.setCustomModelData(RecipeItem.getByName(choice[1]).getCustomModelData());
                    exact.setItemMeta(exactItemMeta);
                    recipe.setIngredient(c, new RecipeChoice.ExactChoice(exact));
                } else {
                    Material m = Material.valueOf(ingredient);
                    recipe.setIngredient(c, m);
                }
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginName() + ChatColor.RED + "Recipe failed! " + illegalArgumentException.getMessage());
        }
        // add the recipe to TARDIS' list
        plugin.getTardisApi().getShapedRecipes().put(s, recipe);
        // return the recipe
        return recipe;
    }
}
