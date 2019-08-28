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

package techreborn.compatmod.buildcraft;

import buildcraft.energy.BCEnergyFluids;
import buildcraft.lib.fluid.FuelRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import techreborn.Core;
import techreborn.api.generator.EFluidGenerator;
import techreborn.api.generator.GeneratorRecipeHelper;
import techreborn.api.recipe.machines.DistillationTowerRecipe;
import techreborn.compat.ICompatModule;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ItemDynamicCell;
import techreborn.lib.ModInfo;

/**
 * @author estebes, modmuss50
 */
@RebornRegistry(modOnly = "buildcraftenergy", modID = ModInfo.MOD_ID)
public class BuildCraftEnergyCompat implements ICompatModule {
	// Configs >>
	@ConfigRegistry(config = "compat", category = "buildcraft", key = "EnableDistillationTowerBuildCraftRecipes", comment = "Enable distillation tower recipes related to BuildCraft fuels")
	public static boolean enableDistillationTowerBuildCraftRecipes = true;

	@ConfigRegistry(config = "compat", category = "buildcraft", key = "EnableBuildcraftFuels", comment = "Allow BuildCraft fuels to be used in fuel generators")
	public static boolean allowBuildCraftFuels = true;
	// << Configs

	@Override
	public void init(FMLInitializationEvent event) {
		if (enableDistillationTowerBuildCraftRecipes) {
			try {
				RecipeHandler.addRecipe(new DistillationTowerRecipe(ItemDynamicCell.getCellWithFluid(BCEnergyFluids.crudeOil[0], 16), ItemDynamicCell.getEmptyCell(33),
					RecipeMethods.getMaterial("diesel", 16, RecipeMethods.Type.CELL), RecipeMethods.getMaterial("sulfuricAcid", 16, RecipeMethods.Type.CELL),
					RecipeMethods.getMaterial("glyceryl", RecipeMethods.Type.CELL), RecipeMethods.getMaterial("methane", 16, RecipeMethods.Type.CELL), 16000, 16));
			} catch (NullPointerException exception) {
				Core.logHelper.error("Cannot create a distillation tower recipe for BuildCraft Oil. This might be caused by another mod messing with it (i.e. AsmodeusCore).");
			}
		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if (allowBuildCraftFuels) { // fuel = 384k
			FuelRegistry.INSTANCE.getFuels().forEach(fuel ->
				GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.THERMAL, fuel.getFluid().getFluid(), (int) (fuel.getPowerPerCycle() / 200000)));
		}
	}
}
