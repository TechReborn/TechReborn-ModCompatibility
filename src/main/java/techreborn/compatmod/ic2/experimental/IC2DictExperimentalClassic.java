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

import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import techreborn.init.IC2Duplicates;

public class IC2DictExperimentalClassic {

	public static void preInit(){
		IC2Duplicates.REFINED_IRON.setIc2Stack(IC2Items.getItem("ingot", "refined_iron"));
		IC2Duplicates.LAPATRON_CRYSTAL.setIc2Stack(IC2Items.getItem("lapotron_crystal"));
		IC2Duplicates.BASIC_MACHINE_FRAME.setIc2Stack(IC2Items.getItem("resource", "machine"));
		IC2Duplicates.ADVANCED_MACHINE_FRAME.setIc2Stack(IC2Items.getItem("resource", "advanced_machine"));
		IC2Duplicates.CARBON_PLATE.setIc2Stack(IC2Items.getItem("crafting", "carbon_plate"));
		IC2Duplicates.ADVANCED_ALLOY.setIc2Stack(IC2Items.getItem("crafting", "alloy"));
	}

	public static ItemStack meta(ItemStack itemStack, int meta){
		//Has to be done this way, over using setItemDamage as ic2 batterys overwirte this
		return new ItemStack(itemStack.getItem(), 1, meta);
	}

}
