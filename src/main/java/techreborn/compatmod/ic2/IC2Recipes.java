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

package techreborn.compatmod.ic2;

import net.minecraft.item.ItemStack;

import reborncore.api.recipe.RecipeHandler;
import reborncore.common.util.RebornCraftingHelper;

import techreborn.api.recipe.machines.CompressorRecipe;
import techreborn.init.IC2Duplicates;
import techreborn.init.ModBlocks;
import techreborn.items.ingredients.ItemParts;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IC2Recipes {
	static List<RecipeDuplicate> recipeDuplicateList = new ArrayList<>();

	public static void cloneMachineRecipes(String machine, IBasicMachineRecipeManager recipeManager){
		RecipeHandler.getRecipeClassFromName(machine).forEach(recipeType -> {
			if(recipeType.getInputs().size() == 1 && recipeType.getOutputs().size() == 1){
				Object object = recipeType.getInputs().get(0);
				IRecipeInput input = null;
				if(object instanceof ItemStack){
					input = Recipes.inputFactory.forStack((ItemStack) object);
				} else if (object instanceof String){
					input = Recipes.inputFactory.forOreDict((String) object);
				}
				recipeManager.addRecipe(input, Collections.singletonList(recipeType.getOutputs().get(0)), null, false);
			}

		});
	}

	public static void registerRecipes() {
		recipeDuplicateList.add(new RecipeDuplicate(new ItemStack(ModBlocks.MACHINE_FRAMES, 1, 0),
				IC2Dict.getItem("resource", "machine")));

		for (RecipeDuplicate duplicate : recipeDuplicateList) {
			duplicate.add();
		}

		RebornCraftingHelper.addShapelessRecipe(ItemParts.getPartByName("rubber"),
				IC2Dict.getItem("crafting", "rubber"));
		
		if (!IC2Duplicates.deduplicate()) {
			RebornCraftingHelper.addShapelessRecipe(IC2Dict.getItem("crafting", "rubber"),
					ItemParts.getPartByName("rubber"));
			RecipeHandler.addRecipe(new CompressorRecipe(IC2Dict.getItem("crafting", "carbon_mesh"),
					IC2Dict.getItem("crafting", "carbon_plate"), 300, 4));
			RecipeHandler.addRecipe(new CompressorRecipe(IC2Dict.getItem("crafting", "coal_ball"),
					IC2Dict.getItem("crafting", "coal_block"), 300, 4));
		}
	}

	public static class RecipeDuplicate {
		ItemStack stack1;
		ItemStack stack2;

		public RecipeDuplicate(ItemStack stack1, ItemStack stack2) {
			this.stack1 = stack1;
			this.stack2 = stack2;
		}

		public void add() {
			RebornCraftingHelper.addShapelessRecipe(stack2, stack1);
			RebornCraftingHelper.addShapelessRecipe(stack1, stack2);
		}
	}
}
