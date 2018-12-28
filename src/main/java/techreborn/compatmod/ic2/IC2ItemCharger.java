/*
 * Copyright (c) 2018 modmuss50 and Gigabit101
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package techreborn.compatmod.ic2;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import reborncore.api.power.IEnergyItemInfo;
import reborncore.common.RebornCoreConfig;
import reborncore.common.powerSystem.TilePowerAcceptor;
import reborncore.common.powerSystem.forge.ForgePowerItemManager;

public class IC2ItemCharger {

	protected static void chargeIc2Item(TilePowerAcceptor tilePowerAcceptor, ItemStack stack){
		if(!isIC2PoweredItem(stack)) {
			return;
		}

		tilePowerAcceptor.useEnergy(ElectricItem.manager.charge(stack, tilePowerAcceptor.getEnergy(), 4, false, false));
	}

	protected static void chargeIc2Item(ForgePowerItemManager powerAcceptor, ItemStack stack){
		if(!isIC2PoweredItem(stack)) {
			return;
		}

		double energyPassed = ElectricItem.manager.charge(stack, powerAcceptor.extractEnergy(powerAcceptor.getEnergyStored(), true) / RebornCoreConfig.euPerFU, 4, false, true);
		int passedFE = (int)(energyPassed * RebornCoreConfig.euPerFU);

		if(energyPassed > 0) {
			ElectricItem.manager.charge(stack, powerAcceptor.extractEnergy(passedFE, false) / RebornCoreConfig.euPerFU, 4, false, false);
		}
	}

	protected static void dischargeIc2Item(TilePowerAcceptor tilePowerAcceptor, ItemStack stack){
		if(!isIC2PoweredItem(stack)) {
			return;
		}

		tilePowerAcceptor.addEnergy(ElectricItem.manager.discharge(stack, tilePowerAcceptor.getFreeSpace(), 4, false, true, false));
	}

	protected static boolean isIC2PoweredItem(ItemStack stack){
		if(stack.isEmpty()) {
			return false;
		}

		Item item = stack.getItem();

		return item instanceof IElectricItem || item instanceof ISpecialElectricItem || ElectricItem.getBackupManager(stack) != null;
	}


	public static void requestEnergyFromIc2Armor(ForgePowerItemManager powerAcceptor, EntityLivingBase entity) {
		for (EntityEquipmentSlot slot: EntityEquipmentSlot.values()) {
			if(slot.getSlotType() == EntityEquipmentSlot.Type.HAND) {
				continue;
			}

			ItemStack stack = entity.getItemStackFromSlot(slot);
			if(!isIC2PoweredItem(stack) || stack.getItem() instanceof IEnergyItemInfo) {
				// Not a powered item, or it is a TR item that will push energy.
				continue;
			}

			double maxExtractedEnergy = ElectricItem.manager.discharge(stack, ElectricItem.manager.getCharge(stack), 4, true, true, true);
			int passedFE = Math.min(powerAcceptor.getMaxEnergyStored() - powerAcceptor.getEnergyStored(), (int)(maxExtractedEnergy * RebornCoreConfig.euPerFU));

			if(passedFE > 0) {
				double extracted = ElectricItem.manager.discharge(stack, passedFE / RebornCoreConfig.euPerFU, 4, true, true, false);
				int extractedFE = (int)(extracted * RebornCoreConfig.euPerFU);

				powerAcceptor.setEnergyStored(Math.min(powerAcceptor.getEnergyStored() + extractedFE, powerAcceptor.getMaxEnergyStored()));
			}
		}
	}
}
