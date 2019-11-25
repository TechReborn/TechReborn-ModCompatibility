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

package techreborn.compatmod.industrialforegoing;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;

import techreborn.api.recipe.Fuels;
import techreborn.compat.ICompatModule;
import techreborn.lib.ModInfo;

/**
 * @author estebes, modmuss50
 */
@RebornRegistry(modOnly = "industrialforegoing", modID = ModInfo.MOD_ID)
public class IndustrialForegoingCompat implements ICompatModule {
    // Configs >>
    @ConfigRegistry(config = "compat", category = "industrial_foregoing", key = "EnableIndustrialForegoingFuels", comment = "Allow Industrial Foregoing fuels to be used in the fuel generators")
    public static boolean enableIndustrialForegoingFuels = true;
    // << Configs

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (enableIndustrialForegoingFuels) {
            // Biofuel
            Fluid biofuel = FluidRegistry.getFluid("biofuel");
            if (biofuel != null)
                Fuels.dieselGenerator.addFuel()
                        .addFluidSource(biofuel)
                        .withEnergyOutput(32.0D)
                        .withEnergyPerTick(16.0D)
                        .register();
        }
    }
}
