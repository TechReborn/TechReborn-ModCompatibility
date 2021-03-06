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

package techreborn.compatmod.ic2.power;

import ic2.api.item.IBackupElectricItemManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import reborncore.api.power.IEnergyItemInfo;
import reborncore.common.RebornCoreConfig;
import reborncore.common.powerSystem.forge.ForgePowerItemManager;

public class TRBackupElectricItemManager implements IBackupElectricItemManager {
	private IC2PowerManager externalPowerManager;

	TRBackupElectricItemManager(IC2PowerManager externalPowerManager) {
		this.externalPowerManager = externalPowerManager;
	}

	@Override
	public boolean handles(ItemStack stack) {
		return !stack.isEmpty() && stack.getItem() instanceof IEnergyItemInfo;
	}

	@Override
	public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
		if(!handles(stack)) {
			return 0.0;
		}

		ForgePowerItemManager item = new ForgePowerItemManager(stack);
		IEnergyItemInfo info = (IEnergyItemInfo)stack.getItem();

		if (!item.canReceive()) {
			return 0.0;
		}

		int maxReceive = (int)(amount * RebornCoreConfig.euPerFU);
		if(!ignoreTransferLimit) {
			maxReceive = Math.min(maxReceive, (int)info.getMaxTransfer(stack));
		}

		int energyReceived = Math.min(item.getMaxEnergyStored() - item.getEnergyStored(), maxReceive);

		if (!simulate) {
			item.setEnergy(item.getEnergyStored() + energyReceived);
		}

		return energyReceived / RebornCoreConfig.euPerFU;
	}

	@Override
	public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
		if(!handles(stack)) {
			return 0.0;
		}

		ForgePowerItemManager item = new ForgePowerItemManager(stack);
		IEnergyItemInfo info = (IEnergyItemInfo)stack.getItem();

		if (!item.canExtract()) {
			return 0.0;
		}

		int maxExtract = (int)(amount * RebornCoreConfig.euPerFU);
		if(!ignoreTransferLimit) {
			maxExtract = Math.min(maxExtract, (int)info.getMaxTransfer(stack));
		}

		int energyExtracted = Math.min(item.getEnergyStored(), maxExtract);

		if (!simulate) {
			item.setEnergy(item.getEnergyStored() - energyExtracted);
		}

		return energyExtracted / RebornCoreConfig.euPerFU;
	}

	@Override
	public double getCharge(ItemStack stack) {
		if(!handles(stack)) {
			return 0.0;
		}

		ForgePowerItemManager item = new ForgePowerItemManager(stack);

		if(!item.canExtract()) {
			return 0.0;
		}

		return item.getEnergyStored() / RebornCoreConfig.euPerFU;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		if(!handles(stack)) {
			return 0.0;
		}

		ForgePowerItemManager item = new ForgePowerItemManager(stack);

		return item.getMaxEnergyStored() / RebornCoreConfig.euPerFU;
	}

	@Override
	public boolean canUse(ItemStack stack, double amount) {
		return getCharge(stack) >= amount;
	}

	@Override
	public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
		if(!handles(stack)) {
			return false;
		}

		chargeFromArmor(stack, entity);
		double maxTransfer = discharge(stack, amount, Integer.MAX_VALUE, true, false, true);

		if((int)maxTransfer == (int)amount) {
			discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
			chargeFromArmor(stack, entity);

			return true;
		}

		return false;
	}

	@Override
	public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
		if(!handles(stack)) {
			return;
		}

		ForgePowerItemManager item = new ForgePowerItemManager(stack);

		externalPowerManager.requestEnergyFromArmor(item, entity);
	}

	@Override
	public String getToolTip(ItemStack stack) {
		// Return null to prevent IC2 from drawing a tooltip since we already draw one

		return null;
	}

	@Override
	public int getTier(ItemStack stack) {
		// TechReborn does not have a concept of electric item tiers.

		return 4;
	}
}
