package techreborn.compatmod.industrialforegoing;

import com.buuz135.industrial.proxy.FluidsRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import reborncore.common.registration.RebornRegistry;
import techreborn.api.generator.EFluidGenerator;
import techreborn.api.generator.GeneratorRecipeHelper;
import techreborn.compat.ICompatModule;
import techreborn.lib.ModInfo;

@RebornRegistry(modOnly = "industrialforegoing", modID = ModInfo.MOD_ID)
public class IndustrialForegoingCompat implements ICompatModule {

	@Override
	public void init(FMLInitializationEvent event) {
		GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.SEMIFLUID, FluidsRegistry.BIOFUEL, 32);
	}
}
