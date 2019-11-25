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

package techreborn.compatmod.thermalexpansion;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;

import techreborn.api.fluidreplicator.FluidReplicatorRecipe;
import techreborn.api.fluidreplicator.FluidReplicatorRecipeList;
import techreborn.api.recipe.Fuels;
import techreborn.api.recipe.machines.DistillationTowerRecipe;
import techreborn.compat.ICompatModule;
import techreborn.init.ModItems;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ItemDynamicCell;
import techreborn.items.ingredients.ItemDusts;
import techreborn.items.ingredients.ItemPlates;
import techreborn.lib.ModInfo;

import cofh.api.util.ThermalExpansionHelper;
import cofh.thermalfoundation.init.TFFluids;
import cofh.thermalfoundation.init.TFItems;
import cofh.thermalfoundation.item.ItemMaterial;

/**
 * @author estebes, modmuss50
 */
@RebornRegistry(modOnly = "thermalexpansion", modID = ModInfo.MOD_ID)
public class ThermalExpansionCompat implements ICompatModule {
    // Configs >>
    @ConfigRegistry(config = "compat", category = "thermal_expansion", key = "EnableDistillationTowerThermalExpansionRecipes", comment = "Enable distillation tower recipes related to ThermalExpansion content")
    public static boolean enableDistillationTowerThermalExpansionRecipes = true;

    @ConfigRegistry(config = "compat", category = "thermal_expansion", key = "EnableThermalExpansionFuels", comment = "Allow Thermal Expansion fuels to be used in the fuel generators")
    public static boolean enableThermalExpansionFuels = true;

    @ConfigRegistry(config = "compat", category = "thermal_expansion", key = "DisableInductionSmelterRecipesBypassingBlastFurnace", comment = "Disable induction smelter recipes that bypass the use of the blast furnace (i.e. tungsten ingots)")
    public static boolean disableInductionSmelterRecipesBypassingBlastFurnace = true;
    // << Configs

    @Override
    public void init(FMLInitializationEvent event) {
        ThermalExpansionHelper.addPulverizerRecipe(2000, new ItemStack(Items.ENDER_PEARL), RecipeMethods.getMaterial("ender_pearl", 1, RecipeMethods.Type.DUST));
        ThermalExpansionHelper.addPulverizerRecipe(3000, new ItemStack(Items.ENDER_EYE), RecipeMethods.getMaterial("ender_eye", 2, RecipeMethods.Type.DUST));
        ThermalExpansionHelper.addPulverizerRecipe(3000, new ItemStack(Items.FLINT), RecipeMethods.getMaterial("flint", 2, RecipeMethods.Type.SMALL_DUST), RecipeMethods.getMaterial("flint", 2, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(2500, new ItemStack(ModItems.CELL), RecipeMethods.getMaterial("tin", 4, RecipeMethods.Type.SMALL_DUST));
        ThermalExpansionHelper.addPulverizerRecipe(2500, new ItemStack(Blocks.END_STONE), RecipeMethods.getMaterial("endstone", 1, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("endstone", 1, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(3000, RecipeMethods.getMaterial("galena", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("galena", 1, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("sulfur", 1, RecipeMethods.Type.DUST), 50);
        ThermalExpansionHelper.addPulverizerRecipe(3000, RecipeMethods.getMaterial("ruby", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("ruby", 2, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("red_garnet", 1, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(3000, RecipeMethods.getMaterial("sapphire", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("sapphire", 2, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("sphalerite", 1, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(3000, RecipeMethods.getMaterial("bauxite", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("bauxite", 2, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("aluminum", 1, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(3000, RecipeMethods.getMaterial("pyrite", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("pyrite", 5, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("iron", 1, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(3000, RecipeMethods.getMaterial("cinnabar", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("cinnabar", 3, RecipeMethods.Type.DUST), new ItemStack(Items.REDSTONE), 10);
        ThermalExpansionHelper.addPulverizerRecipe(4000, RecipeMethods.getMaterial("sphalerite", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("sphalerite", 4, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("zinc", 1, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(5000, RecipeMethods.getMaterial("tungsten", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("tungsten", 2, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("manganese", 1, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(3000, RecipeMethods.getMaterial("peridot", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("peridot", 2, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("emerald", 1, RecipeMethods.Type.DUST), 10);
        ThermalExpansionHelper.addPulverizerRecipe(3000, RecipeMethods.getMaterial("sodalite", 1, RecipeMethods.Type.ORE), RecipeMethods.getMaterial("sodalite", 12, RecipeMethods.Type.DUST), RecipeMethods.getMaterial("aluminum", 1, RecipeMethods.Type.DUST), 10);

        for (String plate : ItemPlates.types) {
            if (!plate.equals(ModItems.META_PLACEHOLDER) && ItemDusts.hasDust(plate)) {
                ThermalExpansionHelper.addPulverizerRecipe(5000, RecipeMethods.getMaterial(plate, 1, RecipeMethods.Type.PLATE), RecipeMethods.getMaterial(plate, 1, RecipeMethods.Type.DUST));
            }
        }

        ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Blocks.SAND), RecipeMethods.getMaterial("refined_iron", 2, RecipeMethods.Type.INGOT), ItemMaterial.crystalSlag.copy(), 25);

        FluidReplicatorRecipeList.addRecipe(new FluidReplicatorRecipe(4, TFFluids.fluidCoal, 100, 20));


        if (enableDistillationTowerThermalExpansionRecipes) {
            // Crude Oil
            RecipeHandler.addRecipe(new DistillationTowerRecipe(ItemDynamicCell.getCellWithFluid(TFFluids.fluidCrudeOil, 16),
                    ItemDynamicCell.getEmptyCell(33),
                    RecipeMethods.getMaterial("diesel", 16, RecipeMethods.Type.CELL),
                    RecipeMethods.getMaterial("sulfuricAcid", 16, RecipeMethods.Type.CELL),
                    RecipeMethods.getMaterial("glyceryl", RecipeMethods.Type.CELL),
                    RecipeMethods.getMaterial("methane", 16, RecipeMethods.Type.CELL),
                    16000,
                    16));

            // Biocrude -> Grassoline
            RecipeHandler.addRecipe(new DistillationTowerRecipe(ItemDynamicCell.getCellWithFluid(TFFluids.fluidBiocrude, 16),
                    null,
                    ItemDynamicCell.getCellWithFluid(TFFluids.fluidBiofuel, 8),
                    ItemDynamicCell.getEmptyCell(8),
                    null,
                    null,
                    400,
                    16));
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (enableThermalExpansionFuels) {
            // Creosote
            Fuels.semiFluidGenerator.addFuel()
                    .addFluidSource(TFFluids.fluidCreosote)
                    .withEnergyOutput(3.0D)
                    .withEnergyPerTick(3.0D)
                    .register();

            // Crude Oil
            Fuels.semiFluidGenerator.addFuel()
                    .addFluidSource(TFFluids.fluidCrudeOil)
                    .withEnergyOutput(16.0D)
                    .withEnergyPerTick(8.0D)
                    .register();

            // Grassoline
            Fuels.dieselGenerator.addFuel()
                    .addFluidSource(TFFluids.fluidBiofuel)
                    .withEnergyOutput(32.0D)
                    .withEnergyPerTick(16.0D)
                    .register();

            // Liquifacted Coal
            Fuels.semiFluidGenerator.addFuel()
                    .addFluidSource(TFFluids.fluidCoal)
                    .withEnergyOutput(48.0D)
                    .withEnergyPerTick(8.0D)
                    .register();

            // Naphtha
            Fuels.semiFluidGenerator.addFuel()
                    .addFluidSource(TFFluids.fluidRefinedOil)
                    .withEnergyOutput(48.0D)
                    .withEnergyPerTick(8.0D)
                    .register();

            // Refined Fuel
            Fuels.dieselGenerator.addFuel()
                    .addFluidSource(TFFluids.fluidRefinedOil)
                    .withEnergyOutput(384.0D)
                    .withEnergyPerTick(20.0D)
                    .register();

            // Seed Oil
            Fuels.semiFluidGenerator.addFuel()
                    .addFluidSource(TFFluids.fluidSeedOil)
                    .withEnergyOutput(8.0D)
                    .withEnergyPerTick(8.0D)
                    .register();

            // Tree Oil
            Fuels.semiFluidGenerator.addFuel()
                    .addFluidSource(TFFluids.fluidTreeOil)
                    .withEnergyOutput(48.0D)
                    .withEnergyPerTick(8.0D)
                    .register();
        }

        if (disableInductionSmelterRecipesBypassingBlastFurnace) {
            // Sand
            ThermalExpansionHelper.removeSmelterRecipe(RecipeMethods.getMaterial("tungsten", 1, RecipeMethods.Type.ORE),
                    RecipeMethods.getStack(Blocks.SAND));
            ThermalExpansionHelper.removeSmelterRecipe(RecipeMethods.getMaterial("tungsten", 1, RecipeMethods.Type.DUST),
                    RecipeMethods.getStack(Blocks.SAND));

            // Cinnabar
            ThermalExpansionHelper.removeSmelterRecipe(RecipeMethods.getMaterial("tungsten", 1, RecipeMethods.Type.ORE),
                    RecipeMethods.getStack(TFItems.itemMaterial, 1, 866));

            // Rich slag
            ThermalExpansionHelper.removeSmelterRecipe(RecipeMethods.getMaterial("tungsten", 1, RecipeMethods.Type.ORE),
                    RecipeMethods.getStack(TFItems.itemMaterial, 1, 865));
        }
    }
}
