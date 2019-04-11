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

package techreborn.compatmod.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import reborncore.api.power.IEnergyItemInfo;
import reborncore.common.registration.RebornRegistry;
import techreborn.compat.ICompatModule;
import techreborn.init.ModItems;
import techreborn.items.armor.ItemLithiumBatpack;
import techreborn.lib.ModInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@RebornRegistry(modOnly = "baubles", modID = ModInfo.MOD_ID)
public class BaublesCompat implements ICompatModule {

	IBauble POWER_BAUBLE = new IBauble() {
		@Override
		public BaubleType getBaubleType(ItemStack itemStack) {
			return BaubleType.BODY;
		}

		@Override
		public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
			if(player instanceof EntityPlayer){
				ItemLithiumBatpack.distributePowerToInventory(player.world, (EntityPlayer) player, itemstack, 1000);
			}
		}
	};

	public static List<Item> VALID_ITEMS = new ArrayList<>();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		VALID_ITEMS.add(ModItems.LAPOTRONIC_CRYSTAL);
		VALID_ITEMS.add(ModItems.LAPOTRONIC_ORB);
	}

	@SubscribeEvent
	public void addCaps(AttachCapabilitiesEvent<ItemStack> event){
		if(VALID_ITEMS.contains(event.getObject().getItem()) && event.getObject().getItem() instanceof IEnergyItemInfo){
			event.addCapability(new ResourceLocation("baubles", "bauble_power_cap"), new ICapabilityProvider() {
				@Override
				public boolean hasCapability(
					@Nonnull
						Capability<?> capability,
					@Nullable
						EnumFacing facing) {
					return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE;
				}

				@Nullable
				@Override
				public <T> T getCapability(
					@Nonnull
						Capability<T> capability,
					@Nullable
						EnumFacing facing) {
					if(capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE){
						return BaublesCapabilities.CAPABILITY_ITEM_BAUBLE.cast(POWER_BAUBLE);
					}
					return null;
				}
			});
		}
	}
}
