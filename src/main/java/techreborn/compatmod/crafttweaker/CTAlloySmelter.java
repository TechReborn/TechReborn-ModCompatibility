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

package techreborn.compatmod.crafttweaker;

import com.google.common.collect.ImmutableList;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import reborncore.api.praescriptum.ingredients.input.InputIngredient;
import reborncore.api.praescriptum.ingredients.input.ItemStackInputIngredient;
import reborncore.api.praescriptum.ingredients.input.OreDictionaryInputIngredient;
import reborncore.api.praescriptum.ingredients.output.ItemStackOutputIngredient;
import reborncore.api.praescriptum.recipes.Recipe;
import reborncore.api.praescriptum.recipes.RecipeHandler;
import reborncore.common.util.ItemUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techreborn.api.recipe.Recipes;

import java.util.Optional;

/**
 * @author estebes
 */
@ZenClass("mods.techreborn.alloySmelter")
public class CTAlloySmelter extends CTPraescriptum {
	@ZenMethod
	@techreborn.compatmod.crafttweaker.ZenDocumentation("IItemStack output, IIngredient ingredientA, IIngredient ingredientB, int tickTime, int energyCostPerTick")
	public static void addRecipe(IItemStack output, IIngredient ingredientA, IIngredient ingredientB, int operationDuration, int energyCostPerTick) {
		InputIngredient<?> inputA = ingredientA instanceof IItemStack ? 
				ItemStackInputIngredient.of(ItemUtils.copyWithSize(CraftTweakerMC.getItemStack(ingredientA), ingredientA.getAmount())) 
				: OreDictionaryInputIngredient.of(((IOreDictEntry) ingredientA).getName(), ingredientA.getAmount());

		InputIngredient<?> inputB = ingredientB instanceof IItemStack ? 
				ItemStackInputIngredient.of(ItemUtils.copyWithSize(CraftTweakerMC.getItemStack(ingredientB), ingredientB.getAmount())) 
				: OreDictionaryInputIngredient.of(((IOreDictEntry) ingredientB).getName(), ingredientB.getAmount());

		Recipe recipe = getRecipeHandler().createRecipe()
			.withInput(ImmutableList.of(inputA, inputB))
			.withOutput(CraftTweakerMC.getItemStack(output))
			.withEnergyCostPerTick(energyCostPerTick)
			.withOperationDuration(operationDuration);

		add(recipe);
	}
	
	@ZenMethod
	@ZenDocumentation("IItemStack output")
	public static void removeRecipe(IItemStack output) {
		ItemStack outStack = CraftTweakerMC.getItemStack(output);
		Optional<Recipe> maybeRecipe = getRecipeHandler().getRecipeByOutput(ImmutableList.of(ItemStackOutputIngredient.of(outStack)));
		maybeRecipe.ifPresent(recipe -> getRecipeHandler().removeRecipe(recipe));
	}

	@ZenMethod
	@ZenDocumentation("IItemStack ingredientA, IItemStack ingredientB")
	public static void removeInputRecipe(IItemStack ingredientA, IItemStack ingredientB) {
		ItemStack inputA = CraftTweakerMC.getItemStack(ingredientA);
		ItemStack inputB = CraftTweakerMC.getItemStack(ingredientB);

		Optional<Recipe> maybeRecipe = getRecipeHandler().findRecipe(ImmutableList.of(inputA, inputB), ImmutableList.of());

		maybeRecipe.ifPresent(recipe -> getRecipeHandler().removeRecipe(recipe));
	}
	
	@ZenMethod
	public static void removeAll() {
		removeAll(getRecipeHandler());
	}

	public static RecipeHandler getRecipeHandler() {
		return Recipes.alloySmelter;
	}
}
