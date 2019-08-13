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

package techreborn.compatmod.appliedenergistics;

import appeng.api.AEApi;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import techreborn.api.recipe.machines.GrinderRecipe;
import techreborn.api.recipe.machines.IndustrialGrinderRecipe;
import techreborn.compat.ICompatModule;
import techreborn.init.ModItems;
import techreborn.init.recipes.RecipeMethods;
import techreborn.lib.ModInfo;

/**
 * @author estebes
 */
@RebornRegistry(modOnly = "appliedenergistics2", modID = ModInfo.MOD_ID)
public class AppliedEnergisticsCompat implements ICompatModule {
	// Config >>
	@ConfigRegistry(config = "compat", category = "appliedenergistics2", key = "EnableQuartzGrinderRecipes", comment = "Enable grinder recipes related to Applied Energistics 2 quartz")
	public static boolean enableQuartzGrinderRecipes = true;

	@ConfigRegistry(config = "compat", category = "appliedenergistics2", key = "EnableQuartzIndustrialGrinderRecipes", comment = "Enable industrial grinder recipes related to Applied Energistics 2 quartz")
	public static boolean enableQuartzIndustrialGrinderRecipes = true;
	// << Config

	@Override
	public void init(FMLInitializationEvent event) {
		// Grinder
		if (enableQuartzGrinderRecipes) {
			RecipeHandler.addRecipe(new GrinderRecipe(
				AEApi.instance().definitions().materials().certusQuartzCrystal().maybeStack(1).orElse(MISSING.copy()),
				AEApi.instance().definitions().materials().certusQuartzDust().maybeStack(1).orElse(MISSING.copy()),
				300, 2));

			RecipeHandler.addRecipe(new GrinderRecipe(
				RecipeMethods.getOre("oreCertusQuartz"),
				AEApi.instance().definitions().materials().certusQuartzDust().maybeStack(5).orElse(MISSING.copy()),
				300, 2));

			RecipeHandler.addRecipe(new GrinderRecipe(
				RecipeMethods.getOre("oreQuartz"),
				AEApi.instance().definitions().materials().netherQuartzDust().maybeStack(3).orElse(MISSING.copy()),
				300, 2));

			RecipeHandler.addRecipe(new GrinderRecipe(
				RecipeMethods.getOre("gemQuartz"),
				AEApi.instance().definitions().materials().netherQuartzDust().maybeStack(1).orElse(MISSING.copy()),
				300, 2));
		}

		// Industrial Grinder
		if (enableQuartzIndustrialGrinderRecipes) {
			FluidStack water = new FluidStack(FluidRegistry.WATER, 1000);

			RecipeHandler.addRecipe(new IndustrialGrinderRecipe(
				AEApi.instance().definitions().blocks().quartzOre().maybeStack(1).orElse(MISSING.copy()),
				water,
				AEApi.instance().definitions().materials().certusQuartzCrystal().maybeStack(2).orElse(MISSING.copy()),
				AEApi.instance().definitions().materials().certusQuartzDust().maybeStack(3).orElse(MISSING.copy()) ,
				null,
				null,
				100, 128, false));

			RecipeHandler.addRecipe(new IndustrialGrinderRecipe(
				AEApi.instance().definitions().blocks().quartzOreCharged().maybeStack(1).orElse(MISSING.copy()),
				water,
				AEApi.instance().definitions().materials().certusQuartzCrystalCharged().maybeStack(2).orElse(MISSING.copy()),
				AEApi.instance().definitions().materials().certusQuartzDust().maybeStack(3).orElse(MISSING.copy()) ,
				null,
				null,
				100, 128, false));
		}
	}

	// Fields >>
	private static final ItemStack MISSING = new ItemStack(ModItems.MISSING_RECIPE_PLACEHOLDER);
	// << Fields
}
