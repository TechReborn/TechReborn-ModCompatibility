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
