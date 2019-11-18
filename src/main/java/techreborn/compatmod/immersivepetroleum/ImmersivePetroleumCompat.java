package techreborn.compatmod.immersivepetroleum;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import reborncore.common.util.ItemUtils;

import techreborn.api.generator.EFluidGenerator;
import techreborn.api.generator.GeneratorRecipeHelper;
import techreborn.api.recipe.Recipes;
import techreborn.api.recipe.machines.DistillationTowerRecipe;
import techreborn.compat.ICompatModule;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ItemDynamicCell;
import techreborn.lib.ModInfo;

import flaxbeard.immersivepetroleum.common.IPContent;

/**
 * @author estebes
 */
@RebornRegistry(modOnly = "immersivepetroleum", modID = ModInfo.MOD_ID)
public class ImmersivePetroleumCompat implements ICompatModule {
    // Configs >>
    @ConfigRegistry(config = "compat", category = "immersive_petroleum", key = "EnableDistillationTowerImmersivePetroleumRecipes", comment = "Enable distillation tower recipes related to Immersive Petroleum content")
    public static boolean enableDistillationTowerImmersivePetroleumRecipes = true;

    @ConfigRegistry(config = "compat", category = "immersive_petroleum", key = "EnableChemicalReactorImmersivePetroleumRecipes", comment = "Enable chemical reactor recipes related to Immersive Petroleum content")
    public static boolean enableChemicalReactorImmersivePetroleumRecipes = true;

    @ConfigRegistry(config = "compat", category = "immersive_petroleum", key = "EnableImmersivePetroleumFuels", comment = "Allow Immersive Petroleum fuels to be used in the fuel generators")
    public static boolean enableImmersivePetroleumFuels = true;
    // << Configs

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        // NO-OP
    }

    @Override
    public void init(FMLInitializationEvent event) {
        if (enableDistillationTowerImmersivePetroleumRecipes) {
            RecipeHandler.addRecipe(new DistillationTowerRecipe(ItemDynamicCell.getCellWithFluid(IPContent.fluidCrudeOil, 16),
                    ItemDynamicCell.getEmptyCell(33),
                    RecipeMethods.getMaterial("diesel", 16, RecipeMethods.Type.CELL),
                    RecipeMethods.getMaterial("sulfuricAcid", 16, RecipeMethods.Type.CELL),
                    RecipeMethods.getMaterial("glyceryl", RecipeMethods.Type.CELL),
                    RecipeMethods.getMaterial("methane", 16, RecipeMethods.Type.CELL),
                    16000,
                    16));
        }

        if (enableChemicalReactorImmersivePetroleumRecipes) {
            Recipes.chemicalReactor.createRecipe()
                    .withInput("dustAluminum", 6)
                    .withInput(ItemDynamicCell.getCellWithFluid(IPContent.fluidCrudeOil, 1))
                    .withOutput(ItemDynamicCell.getCellWithFluid(IPContent.fluidNapalm, 1))
                    .withEnergyCostPerTick(30)
                    .withOperationDuration(1000)
                    .register();

            Recipes.chemicalReactor.createRecipe()
                    .withInput("dustAluminium", 6)
                    .withInput(ItemDynamicCell.getCellWithFluid(IPContent.fluidCrudeOil, 1))
                    .withOutput(ItemDynamicCell.getCellWithFluid(IPContent.fluidNapalm, 1))
                    .withEnergyCostPerTick(30)
                    .withOperationDuration(1000)
                    .register();
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (enableImmersivePetroleumFuels) {
            // Crude Oil
            GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.SEMIFLUID, IPContent.fluidCrudeOil, 16);

            // Diesel
            GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.DIESEL, IPContent.fluidDiesel, 384);

            // Gasoline
            GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.DIESEL, IPContent.fluidGasoline, 384);

            // Napalm
            GeneratorRecipeHelper.registerFluidRecipe(EFluidGenerator.DIESEL, IPContent.fluidNapalm, 400);
        }
    }
}
