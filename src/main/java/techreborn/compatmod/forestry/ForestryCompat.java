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

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;

import techreborn.api.recipe.Fuels;
import techreborn.api.recipe.machines.DistillationTowerRecipe;
import techreborn.compat.ICompatModule;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ItemCells;
import techreborn.items.ItemDynamicCell;
import techreborn.lib.ModInfo;

import forestry.api.fuels.FuelManager;
import forestry.api.fuels.GeneratorFuel;

/**
 * @author estebes
 */
@RebornRegistry(modOnly = "forestry", modID = ModInfo.MOD_ID)
public class ForestryCompat implements ICompatModule {
    // Configs >>
    @ConfigRegistry(config = "compat", category = "forestry", key = "EnableDistillationTowerForestryRecipes", comment = "Enable distillation tower recipes envolving Forestry fuels")
    public static boolean enableDistillationTowerForestryRecipes = true;

    @ConfigRegistry(config = "compat", category = "forestry", key = "EnableForestryFuels", comment = "Allow Forestry fuels to be used in the fuel generators")
    public static boolean enableForestryFuels = true;
    // << Configs

    @Override
    public void init(FMLInitializationEvent event) {
        // Biomass -> ethanol
        if (enableDistillationTowerForestryRecipes) {
            Fluid biomass = FluidRegistry.getFluid("biomass");
            Fluid ethanol = FluidRegistry.getFluid("bio.ethanol");
            if (biomass != null && ethanol != null) {
                RecipeHandler.addRecipe(new DistillationTowerRecipe(ItemCells.getCellByName("biomass", 16), null,
                        RecipeMethods.getMaterial("bio.ethanol", 8, RecipeMethods.Type.CELL),
                        ItemDynamicCell.getEmptyCell(8), null, null, 400, 16));
            }
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (enableForestryFuels) {
            // Biomass
            Fluid biomass = FluidRegistry.getFluid("biomass");
            if (biomass != null) {
                GeneratorFuel biomassFuel = FuelManager.generatorFuel.get(biomass);
                Fuels.semiFluidGenerator.addFuel()
                        .addFluidSource(biomass)
                        .withEnergyOutput(biomassFuel.getEu() * biomassFuel.getRate())
                        .withEnergyPerTick((double) biomassFuel.getEu() / (double) biomassFuel.getRate())
                        .register();
            }

            // Ethanol
            Fluid ethanol = FluidRegistry.getFluid("bio.ethanol");
            if (ethanol != null) {
                GeneratorFuel ethanolFuel = FuelManager.generatorFuel.get(ethanol);
                Fuels.dieselGenerator.addFuel()
                        .addFluidSource(ethanol)
                        .withEnergyOutput(ethanolFuel.getEu() * ethanolFuel.getRate())
                        .withEnergyPerTick((double) ethanolFuel.getEu() / (double) ethanolFuel.getRate())
                        .register();
            }
        }
    }
}
