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

package techreborn.compatmod.railcraft;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;

import techreborn.api.RollingMachineRecipe;
import techreborn.api.recipe.Fuels;
import techreborn.compat.ICompatModule;
import techreborn.lib.ModInfo;

/**
 * @author estebes
 */
@RebornRegistry(modOnly = "railcraft", modID = ModInfo.MOD_ID)
public class RailcraftCompat implements ICompatModule {
    // Configs >>
    @ConfigRegistry(config = "compat", category = "railcraft", key = "DisableRailRelatedRollingMachineRecipes", comment = "Disable rolling machine rail related recipes when Railcraft is installed")
    public static boolean disableRailRelatedRollingMachineRecipes = true;

    @ConfigRegistry(config = "compat", category = "railcraft", key = "EnableRailcraftFuels", comment = "Allow Railcraft fuels to be used in the fuel generators (i.e. Creosote)")
    public static boolean allowRailcraftFuels = true;
    // << Configs

    @Override
    public void init(FMLInitializationEvent event) {
        if (disableRailRelatedRollingMachineRecipes) {
            RollingMachineRecipe.instance.getRecipeList().remove(new ResourceLocation(ModInfo.MOD_ID, "rail"));
            RollingMachineRecipe.instance.getRecipeList().remove(new ResourceLocation(ModInfo.MOD_ID, "gold_rail"));
            RollingMachineRecipe.instance.getRecipeList().remove(new ResourceLocation(ModInfo.MOD_ID, "detector_rail"));
            RollingMachineRecipe.instance.getRecipeList().remove(new ResourceLocation(ModInfo.MOD_ID, "activator_rail"));
            RollingMachineRecipe.instance.getRecipeList().remove(new ResourceLocation(ModInfo.MOD_ID, "minecart"));
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (allowRailcraftFuels) {
            // Creosote
            Fluid creosote = FluidRegistry.getFluid("creosote");
            if (creosote != null)
                Fuels.semiFluidGenerator.addFuel()
                        .addFluidSource(creosote)
                        .withEnergyOutput(3.0D)
                        .withEnergyPerTick(3.0D)
                        .register();
        }
    }
}
