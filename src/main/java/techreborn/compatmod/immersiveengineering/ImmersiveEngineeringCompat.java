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

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;

import techreborn.api.recipe.Fuels;
import techreborn.api.recipe.Recipes;
import techreborn.compat.ICompatModule;
import techreborn.init.recipes.RecipeMethods;
import techreborn.lib.ModInfo;

/**
 * @author estebes, modmuss50
 */
@RebornRegistry(modOnly = "immersiveengineering", modID = ModInfo.MOD_ID)
public class ImmersiveEngineeringCompat implements ICompatModule {
    // Configs >>
    @ConfigRegistry(config = "compat", category = "immersive_engineering", key = "EnableCompressorImmersiveEngineeringRecipes", comment = "Enable compressor recipes related to Immersive Engineering content")
    public static boolean enableCompressorImmersiveEngineeringRecipes = true;

    @ConfigRegistry(config = "compat", category = "immersive_engineering", key = "EnableImmersiveEngineeringFuels", comment = "Allow Immersive Engineering fuels to be used in the fuel generators")
    public static boolean enableImmersiveEngineeringFuels = true;
    // << Configs

    @Override
    public void init(FMLInitializationEvent event) {
        if (enableCompressorImmersiveEngineeringRecipes) {
            // Coke dust -> HOP Graphite dust
            Item material = ForgeRegistries.ITEMS.getValue(new ResourceLocation("immersiveengineering:material"));
            if (material != null) {
                Recipes.compressor.createRecipe()
                        .withInput("dustCoke")
                        .withOutput(RecipeMethods.getStack(material, 1, 18))
                        .withEnergyCostPerTick(4)
                        .withOperationDuration(300)
                        .register();
            }
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (enableImmersiveEngineeringFuels) {
            // Creosote
            Fluid creosote = FluidRegistry.getFluid("creosote");
            if (creosote != null) {
                Fuels.semiFluidGenerator.addFuel()
                        .addFluidSource(creosote)
                        .withEnergyOutput(3.0D)
                        .withEnergyPerTick(3.0D)
                        .register();
            }
        }
    }
}
