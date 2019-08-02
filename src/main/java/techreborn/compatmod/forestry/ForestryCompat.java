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

package techreborn.compatmod.forestry;

import forestry.api.fuels.FuelManager;
import forestry.api.fuels.GeneratorFuel;
import forestry.core.fluids.Fluids;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import techreborn.api.generator.EFluidGenerator;
import techreborn.api.generator.GeneratorRecipeHelper;
import techreborn.api.recipe.machines.DistillationTowerRecipe;
import techreborn.compat.ICompatModule;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ItemCells;
import techreborn.items.ItemDynamicCell;
import techreborn.lib.ModInfo;

/**
 * @author estebes
 */
@RebornRegistry(modOnly = "forestry", modID = ModInfo.MOD_ID)
public class ForestryCompat implements ICompatModule {
	// Configs >>
	@ConfigRegistry(config = "compat", category = "forestry", key = "EnableDistillationTowerForestryRecipes", comment = "Enable distillation tower recipes envolving Forestry fuels")
	public static boolean enableDistillationTowerForestryRecipes = true;

	@ConfigRegistry(config = "compat", category = "forestry", key = "EnableForestryFuels", comment = "Allow Forestry fuels to be used in the fuel generators")
	public static boolean allowForestryFuels = true;
	// << Configs

	@Override
	public void init(FMLInitializationEvent event) {
		// Biomass -> etanol
		if (enableDistillationTowerForestryRecipes) {
			RecipeHandler.addRecipe(new DistillationTowerRecipe(ItemCells.getCellByName(Fluids.BIOMASS.getTag(), 16), null,
				RecipeMethods.getMaterial(Fluids.BIO_ETHANOL.getTag(), 8, RecipeMethods.Type.CELL),
				ItemDynamicCell.getEmptyCell(8), null, null, 400, 16));
		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if (allowForestryFuels) {
			// Biomass
			GeneratorFuel biomass = FuelManager.generatorFuel.get(Fluids.BIOMASS.getFluid());
			GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.SEMIFLUID, Fluids.BIOMASS.getFluid(), biomass.getEu() * biomass.getRate());

			// Ethanol
			GeneratorFuel ethanol = FuelManager.generatorFuel.get(Fluids.BIO_ETHANOL.getFluid());
			GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.DIESEL, Fluids.BIO_ETHANOL.getFluid(), ethanol.getEu() * ethanol.getRate());
		}
	}
}
