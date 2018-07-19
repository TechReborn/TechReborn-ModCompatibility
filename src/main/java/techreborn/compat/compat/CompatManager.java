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

package techreborn.compat.compat;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import reborncore.common.registration.impl.ConfigRegistryFactory;
import techreborn.compat.compat.buildcraft.BuildcraftBuildersCompat;
import techreborn.compat.compat.buildcraft.BuildcraftCompat;
import techreborn.compat.compat.crafttweaker.CraftTweakerCompat;
import techreborn.compat.compat.ic2.RecipesIC2;
import techreborn.compat.compat.immersiveengineering.RecipeImmersiveEngineering;
import techreborn.compat.compat.opencomputers.CompatOpenComputers;
import techreborn.compat.compat.theoneprobe.TheOneProbeCompat;
import techreborn.compat.compat.thermalexpansion.RecipeThermalExpansion;
import techreborn.compat.compat.tinkers.CompatModuleTinkers;
import techreborn.lib.ModInfo;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class CompatManager {

	public static CompatManager INSTANCE = new CompatManager();
	public static boolean isIC2Loaded = false;
	public static boolean isQuantumStorageLoaded = false;
	public ArrayList<ICompatModule> compatModules = new ArrayList<>();

	public CompatManager() {
		isIC2Loaded = Loader.isModLoaded("ic2");
		isQuantumStorageLoaded = Loader.isModLoaded("quantumstorage");
		register(CraftTweakerCompat.class, "crafttweaker");
		register(CompatModuleTinkers.class, "tconstruct");
		register(TheOneProbeCompat.class, "theoneprobe");
		//register(CompatModulePsi.class, "Psi");
		register(RecipesIC2.class, "ic2");
		register(BuildcraftBuildersCompat.class, "buildcraftbuilders");
		register(BuildcraftCompat.class, "buildcraftcore");
		register(RecipeThermalExpansion.class, "thermalexpansion");
		register(CompatOpenComputers.class, "opencomputers");
		register(RecipeImmersiveEngineering.class, "immersiveengineering");
	}

	public void register(Class<? extends ICompatModule> moduleClass, Object... objs) {
		registerCompact(moduleClass, true, objs);
	}

	public void registerCompact(Class<? extends ICompatModule> moduleClass, boolean config, Object... objs) {
		if(config){
			if(!shouldLoad(moduleClass.getSimpleName())){
				return;
			}
		}
		for (Object obj : objs) {
			if (obj instanceof String) {
				String modid = (String) obj;
				if (modid.startsWith("@")) {
					if (modid.equals("@client")) {
						if (FMLCommonHandler.instance().getSide() != Side.CLIENT) {
							return;
						}
					}
				} else if (modid.startsWith("!")) {
					if (Loader.isModLoaded(modid.replaceAll("!", ""))) {
						return;
					}
				} else {
					if (!Loader.isModLoaded(modid)) {
						return;
					}
				}
			} else if (obj instanceof Boolean) {
				Boolean boo = (Boolean) obj;
				if (!boo) {
				}
				return;
			}
		}
		try {
			compatModules.add(moduleClass.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	//This is a hack, and is bad. Dont do this.
	public boolean shouldLoad(String name){
		ConfigRegistry configRegistry = new ConfigRegistry(){
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public String category() {
				return "modules";
			}

			@Override
			public String key() {
				return name;
			}

			@Override
			public String comment() {
				return "Should the compat module '" + name + "' be loaded";
			}

			@Override
			public String config() {
				return "compat";
			}
		};

		RebornRegistry rebornRegistry = new RebornRegistry(){

			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public String modID() {
				return ModInfo.MOD_ID;
			}

			@Override
			public int priority() {
				return 0;
			}

			@Override
			public boolean earlyReg() {
				return false;
			}
		};
		Configuration configuration = ConfigRegistryFactory.getOrCreateConfig(configRegistry, rebornRegistry);
		Property property = ConfigRegistryFactory.get(configRegistry.category(), configRegistry.key(), true, configRegistry.comment(), boolean.class, configuration);
		configuration.save();
		return property.getBoolean();
	}

}
