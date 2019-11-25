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

package techreborn.compatmod.refinedstorage;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;

import techreborn.api.recipe.Recipes;
import techreborn.compat.ICompatModule;
import techreborn.lib.ModInfo;

/**
 * @author estebes
 */
@RebornRegistry(modOnly = "refinedstorage", modID = ModInfo.MOD_ID)
public class RefinedStorageCompat implements ICompatModule {
	// Configs >>
    @ConfigRegistry(config = "compat", category = "refinedstorage", key = "EnableAssemblingMachineProcessorRecipes", comment = "Enable assembling machine recipes envolving Refined Storage processors")
    public static boolean enableAssemblingMachineProcessorRecipes = true;
	// << Configs

    @Override
    public void init(FMLInitializationEvent event) {
        if (!enableAssemblingMachineProcessorRecipes) return;

        Item processor = ForgeRegistries.ITEMS.getValue(new ResourceLocation("refinedstorage:processor"));

        if (processor != null) {
            // Basic Processor
            Recipes.assemblingMachine.createRecipe()
                    .withInput("plateSilicon")
                    .withInput("plateIron")
                    .withOutput(new ItemStack(processor, 1, 3))
                    .withEnergyCostPerTick(1)
                    .withOperationDuration(800)
                    .register();

            // Improved Processor
            Recipes.assemblingMachine.createRecipe()
                    .withInput("plateSilicon")
                    .withInput("plateGold")
                    .withOutput(new ItemStack(processor, 1, 4))
                    .withEnergyCostPerTick(2)
                    .withOperationDuration(1600)
                    .register();

            // Advanced Processor
            Recipes.assemblingMachine.createRecipe()
                    .withInput("plateSilicon")
                    .withInput("plateDiamond")
                    .withOutput(new ItemStack(processor, 1, 5))
                    .withEnergyCostPerTick(4)
                    .withOperationDuration(3200)
                    .register();
        }
    }
}
