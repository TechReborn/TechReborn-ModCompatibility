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

package techreborn.compatmod.ic2.classic;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import reborncore.common.util.OreUtil;
import techreborn.Core;
import techreborn.compatmod.ic2.IC2Dict;
import techreborn.init.IC2Duplicates;

public class IC2DictClassic {
	public static void initDuplicates() {
		IC2Duplicates.IRIDIUM_NEUTRON_REFLECTOR.setIc2Stack(getIridiumReflector());
	}

	public static void initOreDictionary() {
		OreUtil.registerOre("reflectorIridium", getIridiumReflector());
		OreUtil.registerOre("industrialTnt", IC2Dict.getItem("te", "itnt"));
		OreUtil.registerOre("fenceIron", IC2Dict.getItem("fence"));
	}

	private static ItemStack getIridiumReflector() {
		// Try to get the iridium reflector from the item registry instead.
		Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation("ic2:itemreflectors"));
		if(item == null) {
			Core.logHelper.warn("Failed to look up the IC2 Classic iridium neutron reflector item (ic2:itemreflectors:2)");
			return ItemStack.EMPTY;
		} else {
			return new ItemStack(item, 1, 2);
		}
	}
}
