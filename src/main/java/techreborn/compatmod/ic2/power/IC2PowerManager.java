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

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Loader;
import reborncore.api.power.ExternalPowerHandler;
import reborncore.api.power.ExternalPowerManager;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.powerSystem.TilePowerAcceptor;
import reborncore.common.powerSystem.forge.ForgePowerItemManager;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import techreborn.lib.ModInfo;

@RebornRegistry(modOnly = "ic2", modID = ModInfo.MOD_ID)
public class IC2PowerManager implements ExternalPowerManager {

	@ConfigRegistry(config = "ic2", comment = "Should ic2 power support be enabled? (Requires restart)")
	public static boolean ic2Power = true;

	private boolean useIc2cWorkaround;

	public IC2PowerManager() {
		if(ic2Power) {
			ElectricItem.registerBackupManager(new TRBackupElectricItemManager(this));
			useIc2cWorkaround = Loader.isModLoaded("ic2-classic-spmod");
		}
		PowerSystem.EnergySystem.EU.enabled = () -> ic2Power;
	}

	@Override
	public ExternalPowerHandler createPowerHandler(TilePowerAcceptor acceptor) {
		if(!ic2Power){
			return null;
		}
		return new IC2EnergyDelegate(acceptor, useIc2cWorkaround);
	}

	@Override
	public boolean isPoweredTile(TileEntity tileEntity, EnumFacing facing) {
		if(!ic2Power){
			return false;
		}
		return tileEntity instanceof IEnergyTile || EnergyNet.instance.getTile(tileEntity.getWorld(), tileEntity.getPos()) != null;
	}

	@Override
	public boolean isPoweredItem(ItemStack stack) {
		if(!ic2Power){
			return false;
		}
		return IC2ItemCharger.isIC2PoweredItem(stack);
	}

	@Override
	public void dischargeItem(TilePowerAcceptor tilePowerAcceptor, ItemStack stack) {
		if(!ic2Power){
			return;
		}
		IC2ItemCharger.dischargeIc2Item(tilePowerAcceptor, stack);
	}

	@Override
	public void chargeItem(TilePowerAcceptor tilePowerAcceptor, ItemStack stack) {
		if(!ic2Power){
			return;
		}
		IC2ItemCharger.chargeIc2Item(tilePowerAcceptor, stack);
	}

	@Override
	public void chargeItem(ForgePowerItemManager powerAcceptor, ItemStack stack) {
		if(!ic2Power){
			return;
		}
		IC2ItemCharger.chargeIc2Item(powerAcceptor, stack);
	}

	@Override
	public void requestEnergyFromArmor(ForgePowerItemManager powerAcceptor, EntityLivingBase entity) {
		if(!ic2Power){
			return;
		}
		IC2ItemCharger.requestEnergyFromIc2Armor(powerAcceptor, entity);
	}
}
