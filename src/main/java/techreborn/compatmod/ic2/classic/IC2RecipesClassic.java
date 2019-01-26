package techreborn.compatmod.ic2.classic;

import net.minecraft.item.ItemStack;
import reborncore.common.util.RebornCraftingHelper;
import techreborn.compatmod.ic2.IC2Dict;
import techreborn.init.ModItems;

public class IC2RecipesClassic {
	public static void registerRecipes() {
		RebornCraftingHelper.addShapelessRecipe(IC2Dict.getItem("electric_wrench"), new ItemStack(ModItems.WRENCH),
				IC2Dict.getItem("re_battery"));
	}
}
