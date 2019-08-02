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

package techreborn.compatmod.immersiveengineering;

import blusunrize.immersiveengineering.common.IEContent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import techreborn.api.generator.EFluidGenerator;
import techreborn.api.generator.GeneratorRecipeHelper;
import techreborn.api.recipe.machines.CompressorRecipe;
import techreborn.compat.ICompatModule;
import techreborn.lib.ModInfo;

/**
 * @author modmuss50
 */
@RebornRegistry(modOnly = "immersiveengineering", modID = ModInfo.MOD_ID)
public class RecipeImmersiveEngineering implements ICompatModule {
	// Configs >>
	@ConfigRegistry(config = "compat", category = "immersiveengineering", key = "EnableImmersiveEngineeringFuels", comment = "Allow ImmersiveEngineering fuels to be used in the fuel generators (i.e. Creosote)")
	public static boolean allowImmersiveEngineeringFuels = true;
	// << Configs

	@Override
	public void init(FMLInitializationEvent event) {
		// dust_coke to dust_hop_graphite
		RecipeHandler.addRecipe(new CompressorRecipe(new ItemStack(IEContent.itemMaterial, 8, 17),
				new ItemStack(IEContent.itemMaterial, 1, 18), 300, 4));
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if (allowImmersiveEngineeringFuels) {
			// Creosote
			GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.SEMIFLUID, IEContent.fluidCreosote, 3); // 3k EU per bucket
		}
	}
}
