package techreborn.compatmod.ic2;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;
import reborncore.api.recipe.RecipeHandler;
import reborncore.common.util.RebornCraftingHelper;
import techreborn.api.recipe.machines.CompressorRecipe;
import techreborn.api.recipe.machines.ExtractorRecipe;
import techreborn.api.recipe.machines.GrinderRecipe;
import techreborn.init.ModBlocks;
import techreborn.init.ModItems;
import techreborn.init.recipes.ChemicalReactorRecipes;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ingredients.ItemParts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IC2Recipes {
	static List<RecipeDuplicate> recipeDuplicateList = new ArrayList<>();

	static void cloneMachineRecipes(String machine, IBasicMachineRecipeManager recipeManager){
		RecipeHandler.getRecipeClassFromName(machine).forEach(recipeType -> {
			if(recipeType.getInputs().size() == 1 && recipeType.getOutputs().size() == 1){
				Object object = recipeType.getInputs().get(0);
				IRecipeInput input = null;
				if(object instanceof ItemStack){
					input = Recipes.inputFactory.forStack((ItemStack) object);
				} else if (object instanceof String){
					input = Recipes.inputFactory.forOreDict((String) object);
				}
				recipeManager.addRecipe(input, Collections.singletonList(recipeType.getOutputs().get(0)), null, false);
			}

		});
	}

	public static void registerRecipes() {
		recipeDuplicateList.add(new RecipeDuplicate(new ItemStack(ModBlocks.MACHINE_FRAMES, 1, 0),
				IC2Dict.getItem("resource", "machine")));

		for (RecipeDuplicate duplicate : recipeDuplicateList) {
			duplicate.add();
		}

		RebornCraftingHelper.addShapelessRecipe(ItemParts.getPartByName("rubber"),
				IC2Dict.getItem("crafting", "rubber"));
		RebornCraftingHelper.addShapelessRecipe(IC2Dict.getItem("crafting", "rubber"),
				ItemParts.getPartByName("rubber"));
		RebornCraftingHelper.addShapelessRecipe(IC2Dict.getItem("electric_wrench"), new ItemStack(ModItems.WRENCH),
				IC2Dict.getItem("crafting", "small_power_unit"));

		RecipeHandler.addRecipe(new CompressorRecipe(IC2Dict.getItem("crafting", "carbon_mesh"),
				IC2Dict.getItem("crafting", "carbon_plate"), 300, 4));
		RecipeHandler.addRecipe(new CompressorRecipe(IC2Dict.getItem("crafting", "coal_ball"),
				IC2Dict.getItem("crafting", "coal_block"), 300, 4));

		RecipeHandler.addRecipe(new GrinderRecipe(IC2Dict.getItem("crafting", "tin_can"),
				RecipeMethods.getOre("dustTin", 2), 300, 16));

		RecipeHandler.addRecipe(new ExtractorRecipe(IC2Dict.getItem("filled_tin_can"),
				IC2Dict.getItem("crafting", "tin_can"), 300, 16));

		RecipeHandler.addRecipe(
				new ExtractorRecipe(IC2Dict.getItem("misc_resource", "resin"),
						ItemParts.getPartByName("rubber", 3), 400, 2));

		ItemStack f = IC2Dict.getItem("crop_res", "fertilizer");
		ChemicalReactorRecipes.register(RecipeMethods.getMaterial("calcite", RecipeMethods.Type.DUST), RecipeMethods.getMaterial("sulfur", RecipeMethods.Type.DUST), f, 40);
	}

	public static class RecipeDuplicate {
		ItemStack stack1;
		ItemStack stack2;

		public RecipeDuplicate(ItemStack stack1, ItemStack stack2) {
			this.stack1 = stack1;
			this.stack2 = stack2;
		}

		public void add() {
			RebornCraftingHelper.addShapelessRecipe(stack2, stack1);
			RebornCraftingHelper.addShapelessRecipe(stack1, stack2);
		}
	}
}
