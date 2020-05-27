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

package techreborn.compatmod.crafttweaker;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import reborncore.common.registration.RebornRegistry;

import techreborn.compat.ICompatModule;
import techreborn.compatmod.crafttweaker.praescriptum.*;
import techreborn.lib.ModInfo;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;
import static crafttweaker.api.minecraft.CraftTweakerMC.getLiquidStack;

@RebornRegistry(modOnly = "crafttweaker", modID = ModInfo.MOD_ID)
public class CraftTweakerCompat implements ICompatModule {
    public static ItemStack toStack(IItemStack iStack) {
        if (iStack == null) {
            return null;
        }
        return getItemStack(iStack);
    }

    public static Object toObject(IIngredient iStack) {
        if (iStack == null)
            return null;
        else {
            if (iStack instanceof IOreDictEntry)
                return ((IOreDictEntry) iStack).getName();
            else if (iStack instanceof IItemStack)
                return getItemStack((IItemStack) iStack);
            else if (iStack instanceof IngredientStack) {
                IIngredient ingr = ObfuscationReflectionHelper.getPrivateValue(IngredientStack.class, (IngredientStack) iStack, "ingredient");
                return toObject(ingr);
            } else
                return null;
        }
    }

    public static FluidStack toFluidStack(ILiquidStack iStack) {
        return getLiquidStack(iStack);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        CraftTweakerAPI.registerClass(CTBlastFurnace.class);
        CraftTweakerAPI.registerClass(CTIndustrialGrinder.class);
        CraftTweakerAPI.registerClass(CTImplosionCompressor.class);
        CraftTweakerAPI.registerClass(CTIndustrialElectrolyzer.class);
        CraftTweakerAPI.registerClass(CTIndustrialSawmill.class);
        CraftTweakerAPI.registerClass(CTFusionReactor.class);
        CraftTweakerAPI.registerClass(CTVacuumFreezer.class);
        CraftTweakerAPI.registerClass(CTFluidGen.class);
        CraftTweakerAPI.registerClass(CTRollingMachine.class);
        CraftTweakerAPI.registerClass(CTScrapbox.class);
        CraftTweakerAPI.registerClass(CTDistillationTower.class);
        CraftTweakerAPI.registerClass(CTFluidReplicator.class);

        // Praescriptum >>
        CraftTweakerAPI.registerClass(CTAlloySmelter.class);
        CraftTweakerAPI.registerClass(CTAssemblingMachine.class);
        CraftTweakerAPI.registerClass(CTChemicalReactor.class);
        CraftTweakerAPI.registerClass(CTCompressor.class);
        CraftTweakerAPI.registerClass(CTExtractor.class);
        CraftTweakerAPI.registerClass(CTGrinder.class);
        CraftTweakerAPI.registerClass(CTPlateBendingMachine.class);
        CraftTweakerAPI.registerClass(CTSolidCanningMachine.class);
        CraftTweakerAPI.registerClass(CTWireMill.class);
        CraftTweakerAPI.registerClass(CTCentrifuge.class);
        // << Praescriptum
    }
}
