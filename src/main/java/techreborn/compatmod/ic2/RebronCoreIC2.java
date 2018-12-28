package techreborn.compatmod.ic2;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import reborncore.api.power.ExternalPowerHandler;
import reborncore.api.power.ExternalPowerManager;
import reborncore.common.powerSystem.TilePowerAcceptor;
import reborncore.common.powerSystem.forge.ForgePowerItemManager;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import techreborn.lib.ModInfo;

@RebornRegistry(modOnly = "ic2,!ic2-classic-spmod", modID = ModInfo.MOD_ID)
public class RebronCoreIC2 implements ExternalPowerManager {

	@ConfigRegistry(config = "ic2", comment = "Should ic2 power support be enabled? (Requires restart)")
	public static boolean ic2Power = true;

	public RebronCoreIC2() {
		if(ic2Power) {
			ElectricItem.registerBackupManager(new TRBackupElectricItemManager(this));
		}
	}

	@Override
	public ExternalPowerHandler createPowerHandler(TilePowerAcceptor acceptor) {
		if(!ic2Power){
			return null;
		}
		return new IC2EnergyBase(acceptor);
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
		IC2ItemCharger.chargeIc2Item(powerAcceptor, stack);
	}

	@Override
	public void requestEnergyFromArmor(ForgePowerItemManager powerAcceptor, EntityLivingBase entity) {
		IC2ItemCharger.requestEnergyFromIc2Armor(powerAcceptor, entity);
	}
}
