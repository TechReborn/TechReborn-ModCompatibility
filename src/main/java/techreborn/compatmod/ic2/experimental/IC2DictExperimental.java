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

package techreborn.compatmod.ic2.experimental;

import net.minecraft.item.ItemStack;
import reborncore.common.util.OreUtil;
import techreborn.compatmod.ic2.IC2Dict;
import techreborn.init.IC2Duplicates;

public class IC2DictExperimental {
	public static void initDuplicates() {
//		IC2Duplicates.IRIDIUM_NEUTRON_REFLECTOR.setIc2Stack(IC2Dict.getItem("iridium_reflector"));
		IC2Duplicates.IRON_FENCE.setIc2Stack(IC2Dict.getItem("fence", "iron"));
	}

	public static void initOreDictionary() {
		OreUtil.registerOre("drillAdvanced", IC2Dict.getItem("iridium_drill"));
		OreUtil.registerOre("reflectorIridium", IC2Dict.getItem("iridium_reflector"));

		{
			ItemStack industrialTnt = IC2Dict.getItem("te", "itnt");
			industrialTnt.setItemDamage(1);

			OreUtil.registerOre("industrialTnt", industrialTnt);
		}

		OreUtil.registerOre("fertilizer", IC2Dict.getItem("crafting", "bio_chaff"));

		OreUtil.registerOre("uran235", IC2Dict.getItem("nuclear", "uranium_235"));
		OreUtil.registerOre("uran238", IC2Dict.getItem("nuclear", "uranium_238"));
		OreUtil.registerOre("smallUran235", IC2Dict.getItem("nuclear", "small_uranium_235"));
		OreUtil.registerOre("smallUran238", IC2Dict.getItem("nuclear", "small_uranium_238"));

		OreUtil.registerOre("fenceIron", IC2Dict.getItem("fence", "iron"));
	}
}
