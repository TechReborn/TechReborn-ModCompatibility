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

package techreborn.compatmod.ic2;

import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import reborncore.common.util.OreUtil;
import techreborn.Core;
import techreborn.init.IC2Duplicates;

/**
 * Created by modmuss50 on 16/07/2016.
 */
public class IC2Dict {

	public static void initDuplicates() {
		IC2Duplicates.GRINDER.setIc2Stack(getItem("te", "macerator"));
		IC2Duplicates.ELECTRICAL_FURNACE.setIc2Stack(getItem("te", "electric_furnace"));
		IC2Duplicates.IRON_FURNACE.setIc2Stack(getItem("te", "iron_furnace"));
		IC2Duplicates.GENERATOR.setIc2Stack(getItem("te", "generator"));
		IC2Duplicates.WATER_MILL.setIc2Stack(getItem("te", "water_generator"));
		IC2Duplicates.EXTRACTOR.setIc2Stack(getItem("te", "extractor"));
		IC2Duplicates.RECYCLER.setIc2Stack(getItem("te", "recycler"));
		IC2Duplicates.COMPRESSOR.setIc2Stack(getItem("te", "compressor"));
		IC2Duplicates.BAT_BOX.setIc2Stack(getItem("te", "batbox"));
		IC2Duplicates.MFE.setIc2Stack(getItem("te", "mfe"));
		IC2Duplicates.MFSU.setIc2Stack(getItem("te", "mfsu"));
		IC2Duplicates.LVT.setIc2Stack(getItem("te", "lv_transformer"));
		IC2Duplicates.MVT.setIc2Stack(getItem("te", "mv_transformer"));
		IC2Duplicates.HVT.setIc2Stack(getItem("te", "hv_transformer"));
		IC2Duplicates.CABLE_COPPER.setIc2Stack(getIC2Cable("copper", 0));
		IC2Duplicates.CABLE_GOLD.setIc2Stack(getIC2Cable("gold", 0));
		IC2Duplicates.CABLE_ICOPPER.setIc2Stack(getIC2Cable("copper", 1));
		IC2Duplicates.CABLE_IGOLD.setIc2Stack(getIC2Cable("gold", 1));
		IC2Duplicates.CABLE_HV.setIc2Stack(getIC2Cable("iron", 0));
		IC2Duplicates.CABLE_IHV.setIc2Stack(getIC2Cable("iron", 1));
		IC2Duplicates.CABLE_IIHV.setIc2Stack(getIC2Cable("iron", 2));
		IC2Duplicates.CABLE_GLASSFIBER.setIc2Stack(getIC2Cable("glass", 0));

		IC2Duplicates.MIXED_METAL.setIc2Stack(getItem("ingot", "alloy"));
		IC2Duplicates.CARBON_FIBER.setIc2Stack(getItem("crafting", "carbon_fibre"));
		IC2Duplicates.CARBON_MESH.setIc2Stack(getItem("crafting", "carbon_mesh"));
		IC2Duplicates.SCRAP.setIc2Stack(getItem("crafting", "scrap"));
		IC2Duplicates.FREQ_TRANSMITTER.setIc2Stack(getItem("frequency_transmitter"));
		IC2Duplicates.NEUTRON_REFLECTOR.setIc2Stack(getItem("neutron_reflector"));
		IC2Duplicates.THICK_NEUTRON_REFLECTOR.setIc2Stack(getItem("thick_neutron_reflector"));
		// Note: IC2Duplicates.IRIDIUM_NEUTRON_REFLECTOR is handled by classic/experimental
	}

	public static void initOreDictionary() {
		//Rubber - ore dic: itemRubber, hidden from JEI
		//Rubber Sap - only used to make rubber, hidden from JEI
		//Rubber tree blocks, hidden when deduplication is on, and rubber tress are not set to gen, includes tree taps
		//Circuits are hidden in JEI

		try {
			OreUtil.registerOre("reBattery", getItem("re_battery"));

			OreUtil.registerOre("circuitBasic", getItem("crafting", "circuit"));
			OreUtil.registerOre("circuitAdvanced", getItem("crafting", "advanced_circuit"));

			OreUtil.registerOre("machineBlockBasic", getItem("resource", "machine"));
			OreUtil.registerOre("machineBlockAdvanced", getItem("resource", "advanced_machine"));

			OreUtil.registerOre("lapotronCrystal", getItem("lapotron_crystal"));
			OreUtil.registerOre("energyCrystal", getItem("energy_crystal"));

			OreUtil.registerOre("drillBasic", getItem("drill"));
			OreUtil.registerOre("drillDiamond", getItem("diamond_drill"));

			OreUtil.registerOre("reflectorBasic", getItem("neutron_reflector"));
			OreUtil.registerOre("reflectorThick", getItem("thick_neutron_reflector"));

			OreUtil.registerOre("craftingIndustrialDiamond", getItem("crafting", "industrial_diamond"));

			OreUtil.registerOre("hvTransformer", getItem("te", "hv_transformer"));

			OreUtil.registerOre("rubberWood", getItem("rubber_wood"));
			OreUtil.registerOre("glassReinforced", getItem("glass", "reinforced"));

			OreUtil.registerOre("oreIridium", getItem("misc_resource", "iridium_ore"));

			OreUtil.registerOre("logRubber", getItem("rubber_wood"));
			OreUtil.registerOre("plateIridiumAlloy", getItem("crafting", "iridium"));
			OreUtil.registerOre("plateAdvancedAlloy", getItem("crafting", "alloy"));
			OreUtil.registerOre("plateCarbon", getItem("crafting", "carbon_plate"));

		} catch (NoClassDefFoundError notFound) {
			Core.logHelper.warn(
					"Can't enable integration: IC2 installed but cannot be hooked\n" +
							"Do you use incompatible IC2 version?\n" +
							"Please create issue on github and provide FULL LOG and mod list");
		} catch (Throwable error) {
			Core.logHelper.warn(
					"Exception thrown during IC2 integration initDuplicates\n" +
							"Do you use incompatible IC2 version?\n" +
							"Please create issue on github and provide FULL LOG and mod list.\n" +
							"Error stack trace: ");
			error.printStackTrace();
		}
	}

	public static ItemStack getIC2Cable(String type, int insulation) {
		return getItem("cable", "type:"+type+",insulation:"+insulation);
	}

	public static ItemStack getItem(String name) {
		return getItem(name, null);
	}

	public static ItemStack getItem(String name, String variant) {
		ItemStack stack = IC2Items.getItem(name, variant);

		if(stack==null || stack.isEmpty() || stack.getItem().getRegistryName().toString().equals("ic2:itemnouse")) {
			// Treat IC2 Classic's ic2:itemnouse item as null.
			stack = null;
		}

		if(stack==null) {
			Core.logHelper.warn("Failed to look up the IC2 item with name "+name+" and variant "+variant);
		}

		return stack;
	}

}
