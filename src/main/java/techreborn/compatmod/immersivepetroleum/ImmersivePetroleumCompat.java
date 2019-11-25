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

package techreborn.compatmod.immersivepetroleum;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;

import techreborn.api.recipe.Fuels;
import techreborn.api.recipe.Recipes;
import techreborn.api.recipe.machines.DistillationTowerRecipe;
import techreborn.compat.ICompatModule;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ItemDynamicCell;
import techreborn.lib.ModInfo;

/**
 * @author estebes
 */
@RebornRegistry(modOnly = "immersivepetroleum", modID = ModInfo.MOD_ID)
public class ImmersivePetroleumCompat implements ICompatModule {
    // Configs >>
    @ConfigRegistry(config = "compat", category = "immersive_petroleum", key = "EnableDistillationTowerImmersivePetroleumRecipes", comment = "Enable distillation tower recipes related to Immersive Petroleum content")
    public static boolean enableDistillationTowerImmersivePetroleumRecipes = true;

    @ConfigRegistry(config = "compat", category = "immersive_petroleum", key = "EnableChemicalReactorImmersivePetroleumRecipes", comment = "Enable chemical reactor recipes related to Immersive Petroleum content")
    public static boolean enableChemicalReactorImmersivePetroleumRecipes = true;

    @ConfigRegistry(config = "compat", category = "immersive_petroleum", key = "EnableImmersivePetroleumFuels", comment = "Allow Immersive Petroleum fuels to be used in the fuel generators")
    public static boolean enableImmersivePetroleumFuels = true;
    // << Configs

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        // NO-OP
    }

    @Override
    public void init(FMLInitializationEvent event) {
        if (enableDistillationTowerImmersivePetroleumRecipes) {
            Fluid crudeOil = FluidRegistry.getFluid("oil");
            if (crudeOil != null) {
                RecipeHandler.addRecipe(new DistillationTowerRecipe(ItemDynamicCell.getCellWithFluid(crudeOil, 16),
                        ItemDynamicCell.getEmptyCell(33),
                        RecipeMethods.getMaterial("diesel", 16, RecipeMethods.Type.CELL),
                        RecipeMethods.getMaterial("sulfuricAcid", 16, RecipeMethods.Type.CELL),
                        RecipeMethods.getMaterial("glyceryl", RecipeMethods.Type.CELL),
                        RecipeMethods.getMaterial("methane", 16, RecipeMethods.Type.CELL),
                        16000,
                        16));
            }
        }

        if (enableChemicalReactorImmersivePetroleumRecipes) {
            Fluid crudeOil = FluidRegistry.getFluid("oil");
            Fluid napalm = FluidRegistry.getFluid("napalm");
            if (crudeOil != null && napalm != null) {
                Recipes.chemicalReactor.createRecipe()
                        .withInput("dustAluminum", 6)
                        .withInput(ItemDynamicCell.getCellWithFluid(crudeOil, 1))
                        .withOutput(ItemDynamicCell.getCellWithFluid(napalm, 1))
                        .withEnergyCostPerTick(30)
                        .withOperationDuration(1000)
                        .register();

                Recipes.chemicalReactor.createRecipe()
                        .withInput("dustAluminium", 6)
                        .withInput(ItemDynamicCell.getCellWithFluid(crudeOil, 1))
                        .withOutput(ItemDynamicCell.getCellWithFluid(napalm, 1))
                        .withEnergyCostPerTick(30)
                        .withOperationDuration(1000)
                        .register();
            }
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (enableImmersivePetroleumFuels) {
            // Crude Oil
            Fluid crudeOil = FluidRegistry.getFluid("oil");
            if (crudeOil != null)
                Fuels.semiFluidGenerator.addFuel()
                        .addFluidSource(crudeOil)
                        .withEnergyOutput(16.0D)
                        .withEnergyPerTick(8.0D)
                        .register();

            // Diesel
            Fluid diesel = FluidRegistry.getFluid("diesel");
            if (diesel != null)
                Fuels.dieselGenerator.addFuel()
                        .addFluidSource(diesel)
                        .withEnergyOutput(384.0D)
                        .withEnergyPerTick(20.0D)
                        .register();

            // Gasoline
            Fluid gasoline = FluidRegistry.getFluid("gasoline");
            if (gasoline != null)
                Fuels.dieselGenerator.addFuel()
                        .addFluidSource(gasoline)
                        .withEnergyOutput(384.0D)
                        .withEnergyPerTick(20.0D)
                        .register();

            // Napalm
            Fluid napalm = FluidRegistry.getFluid("napalm");
            if (napalm != null)
                Fuels.dieselGenerator.addFuel()
                        .addFluidSource(napalm)
                        .withEnergyOutput(400.0D)
                        .withEnergyPerTick(20.0D)
                        .register();
        }
    }
}
