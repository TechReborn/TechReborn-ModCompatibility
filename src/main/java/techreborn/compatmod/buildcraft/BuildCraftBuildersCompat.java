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

import buildcraft.builders.BCBuildersBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import reborncore.common.util.RebornCraftingHelper;
import reborncore.common.util.RecipeRemover;
import techreborn.Core;
import techreborn.compat.ICompatModule;
import techreborn.init.ModItems;
import techreborn.lib.ModInfo;

/**
 * Created by Mark on 02/06/2017.
 */
@RebornRegistry(modOnly = "buildcraftbuilders", modID = ModInfo.MOD_ID)
public class BuildCraftBuildersCompat implements ICompatModule {
	// Configs >>
	@ConfigRegistry(config = "compat", category = "buildcraft", key = "ExpensiveQuarryRecipe", comment = "Buildcraft's quarry will require an advanced circuit and diamond drill if enabled")
	public static boolean expensiveQuarryRecipe = true;
	// << Configs

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if (expensiveQuarryRecipe) {
			RecipeRemover.removeAnyRecipe(new ItemStack(BCBuildersBlocks.quarry));
			RebornCraftingHelper.addShapedOreRecipe(new ItemStack(BCBuildersBlocks.quarry),
				"IAI", "GIG", "DED",
				'I', "gearIron",
				'G', "gearGold",
				'D', "gearDiamond",
				'A', "circuitAdvanced",
				'E', new ItemStack(ModItems.DIAMOND_DRILL));
		}
		//The recipebook still knows about the old recipe so crashes, this should update it to have it replaced by the new recipe
		Core.proxy.rebuildRecipeBook();
	}
}
