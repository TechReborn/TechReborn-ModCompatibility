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

package techreborn.compatmod.ic2.experimental.recipes;

import net.minecraft.item.ItemStack;
import reborncore.common.util.ItemUtils;
import techreborn.api.recipe.Recipes;
import techreborn.compatmod.ic2.IC2Dict;

/**
 * @author estebes
 */
public class ChemicalReactorRecipes {
	public static void init() {
		// Fertilizer
		ItemStack fertilizer = IC2Dict.getItem("crop_res", "fertilizer");

		Recipes.chemicalReactor.createRecipe()
			.withInput("dustCalcite", 1)
			.withInput("dustSulfur", 1)
			.withOutput(copyWithSize(fertilizer, 2))
			.withEnergyCostPerTick(30)
			.withOperationDuration(200)
			.register();

		Recipes.chemicalReactor.createRecipe()
			.withInput("dustCalcite", 1)
			.withInput("dustAsh", 3)
			.withOutput(copyWithSize(fertilizer, 1))
			.withEnergyCostPerTick(30)
			.withOperationDuration(100)
			.register();
	}

	public static ItemStack copyWithSize(ItemStack stack, int size) {
		if (ItemUtils.isEmpty(stack)) return ItemStack.EMPTY;

		return ItemUtils.setSize(stack.copy(), size);
	}
}
