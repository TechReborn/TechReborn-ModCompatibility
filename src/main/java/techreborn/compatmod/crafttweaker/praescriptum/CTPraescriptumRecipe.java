/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package techreborn.compatmod.crafttweaker.praescriptum;

import reborncore.api.praescriptum.recipes.Recipe;
import reborncore.api.praescriptum.recipes.RecipeHandler;
import reborncore.common.util.ItemUtils;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author estebes
 */
public class CTPraescriptumRecipe {
    // Add Recipe >>
    public static void add(Recipe recipe) {
        CraftTweakerAPI.apply(new CTPraescriptumRecipe.Add(recipe));
    }

    private static class Add implements IAction {
        public Add(Recipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public void apply() {
            recipe.register();
        }

        @Override
		public String describe() {
			return "Adding " + recipe + " for "	+ Arrays.stream(recipe.getItemOutputs())
					.map(ItemUtils::toFormattedString)
					.collect(Collectors.joining(", ", "{", "}"));
		}

        // Fields >>
        private final Recipe recipe;
        // << Fields
    }
    // << Add Recipe

    // Remove Recipe >>
    public static void remove(Recipe recipe, RecipeHandler recipeHandler) {
        CraftTweakerAPI.apply(new CTPraescriptumRecipe.Remove(recipe, recipeHandler));
    }

    private static class Remove implements IAction {
        public Remove(Recipe recipe, RecipeHandler recipeHandler) {
            this.recipe = recipe;
            this.recipeHandler = recipeHandler;
        }

        @Override
        public void apply() {
            if (recipeHandler.removeRecipe(recipe))
                removedRecipes.add(recipe);
        }

        @Override
        public String describe() {
            return "Removing " + recipe + " for " + Arrays.stream(recipe.getItemOutputs())
					.map(ItemUtils::toFormattedString)
                    .collect(Collectors.joining(", ", "{", "}"));
        }

        // Fields >>
        private final Recipe recipe;
        private final RecipeHandler recipeHandler;
        private final List<Recipe> removedRecipes = new ArrayList<>();
        // << Fields
    }
    // << Remove Recipe

    // Remove All Recipes >>
    public static void removeAll(RecipeHandler recipeHandler) {
        CraftTweakerAPI.apply(new CTPraescriptumRecipe.RemoveAll(recipeHandler));
    }

    private static class RemoveAll implements IAction {
        public RemoveAll(RecipeHandler recipeHandler) {
            this.recipeHandler = recipeHandler;
        }

        @Override
        public void apply() {
            recipeHandler.getRecipes().stream()
                    .collect(Collectors.toCollection(() -> removedRecipes));

            recipeHandler.getRecipes().removeAll(removedRecipes);
        }

        @Override
        public String describe() {
            return "Removing all recipes from " + recipeHandler;
        }

        // Fields >>
        private final RecipeHandler recipeHandler;
        private final List<Recipe> removedRecipes = new ArrayList<>();
        // << Fields
    }
    // << Remove All Recipes
}
