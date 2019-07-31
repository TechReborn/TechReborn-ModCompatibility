package techreborn.compatmod.refinedstorage;

import com.raoulvdberge.refinedstorage.RSItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import reborncore.common.registration.RebornRegistry;
import techreborn.api.recipe.Recipes;
import techreborn.compat.ICompatModule;
import techreborn.lib.ModInfo;

/**
 * @author estebes
 */
@RebornRegistry(modOnly = "refinedstorage", modID = ModInfo.MOD_ID)
public class RefinedStorageCompat implements ICompatModule {
	@Override
	public void init(FMLInitializationEvent event) {
		// Basic Processor
		Recipes.assemblingMachine.createRecipe()
			.withInput("plateSilicon")
			.withInput("plateIron")
			.withOutput(new ItemStack(RSItems.PROCESSOR, 1 , 3))
			.withEnergyCostPerTick(1)
			.withEnergyCostPerTick(800)
			.register();

		// Improved Processor
		Recipes.assemblingMachine.createRecipe()
			.withInput("plateSilicon")
			.withInput("plateGold")
			.withOutput(new ItemStack(RSItems.PROCESSOR, 1 , 4))
			.withEnergyCostPerTick(2)
			.withEnergyCostPerTick(1600)
			.register();

		Recipes.assemblingMachine.createRecipe()
			.withInput("plateSilicon")
			.withInput("plateDiamond")
			.withOutput(new ItemStack(RSItems.PROCESSOR, 1 , 5))
			.withEnergyCostPerTick(4)
			.withEnergyCostPerTick(3200)
			.register();
	}
}
