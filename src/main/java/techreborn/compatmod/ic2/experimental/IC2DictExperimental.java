package techreborn.compatmod.ic2.experimental;

import net.minecraft.item.ItemStack;
import reborncore.common.util.OreUtil;
import techreborn.compatmod.ic2.IC2Dict;
import techreborn.init.IC2Duplicates;

public class IC2DictExperimental {
	public static void initDuplicates() {
		IC2Duplicates.IRIDIUM_NEUTRON_REFLECTOR.setIc2Stack(IC2Dict.getItem("iridium_reflector"));
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
