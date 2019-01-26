package techreborn.compatmod.ic2.experimental;

import net.minecraft.item.ItemStack;
import reborncore.common.util.RebornCraftingHelper;
import techreborn.compatmod.ic2.IC2Dict;
import techreborn.init.ModItems;

public class IC2RecipesExperimental {
	public static void registerRecipes() {
		RebornCraftingHelper.addShapelessRecipe(IC2Dict.getItem("electric_wrench"), new ItemStack(ModItems.WRENCH),
				IC2Dict.getItem("crafting", "small_power_unit"));
	}
}
