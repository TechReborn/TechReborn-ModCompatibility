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

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.*;
import ic2.api.info.ILocatable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import reborncore.api.power.ExternalPowerHandler;
import reborncore.common.powerSystem.TilePowerAcceptor;

public class IC2EnergyDelegate implements IEnergyTile, IEnergySink, IEnergySource, ExternalPowerHandler, ILocatable, IMultiEnergySource {

	TilePowerAcceptor powerAcceptor;
	protected boolean addedToEnet;
	boolean useIc2cWorkaround;

	public IC2EnergyDelegate(TilePowerAcceptor powerAcceptor, boolean useIc2cWorkaround) {
		this.powerAcceptor = powerAcceptor;
		this.useIc2cWorkaround = useIc2cWorkaround;
	}

	@Override
	public double getDemandedEnergy() {
		return powerAcceptor.getMaxPower() - powerAcceptor.getEnergy();
	}

	@Override
	public int getSinkTier() {
		return powerAcceptor.getTier().getIC2Tier();
	}

	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		double used = powerAcceptor.addEnergy(amount);
		return (amount - used);
	}

	// IEnergyAcceptor
	@Override
	@Optional.Method(modid = "ic2")
	public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, EnumFacing enumFacing) {
		return powerAcceptor.canAcceptEnergy(enumFacing);
	}

	// IEnergySource
	@Override
	public double getOfferedEnergy() {
		double maxOffered = powerAcceptor.getMaxOutput() * powerAcceptor.maxPacketsPerTick;

		if(useIc2cWorkaround) {
			// IC2 Classic thinks that getOfferedEnergy refers to each packet...
			// This prevents explosions on transformers.
			maxOffered = powerAcceptor.getMaxOutput();
		}

		return Math.min(powerAcceptor.getEnergy(), maxOffered);
	}

	@Override
	public void drawEnergy(double amount) {
		powerAcceptor.useEnergy((int) amount);
	}

	@Override
	public int getSourceTier() {
		return powerAcceptor.getPushingTier().getIC2Tier();
	}

	// IMultiEnergySource
	@Override
	public boolean sendMultipleEnergyPackets() {
		return powerAcceptor.maxPacketsPerTick > 1;
	}

	@Override
	public int getMultipleEnergyPacketAmount() {
		return powerAcceptor.maxPacketsPerTick;
	}

	// IEnergyEmitter
	@Override
	public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, EnumFacing enumFacing) {
		return powerAcceptor.canProvideEnergy(enumFacing);
	}

	@Override
	public void tick() {
		if(powerAcceptor.getWorld().isRemote){
			return;
		}
		if (!addedToEnet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnet = true;
		}
	}

	@Override
	public void unload() {
		if (addedToEnet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnet = false;
		}
	}

	@Override
	public void invalidate() {
		unload();
	}

	@Override
	public BlockPos getPosition() {
		return powerAcceptor.getPos();
	}

	@Override
	public World getWorldObj() {
		return powerAcceptor.getWorld();
	}
	// END IC2
}
