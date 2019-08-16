package techreborn.compatmod.ic2.experimental.recipes;

import techreborn.api.recipe.Recipes;
import techreborn.compatmod.ic2.IC2Dict;

/**
 * @author estebes
 */
public class ExtractorRecipes {
	public static void init() {
		// Tin Can
		Recipes.extractor.createRecipe()
			.withInput(IC2Dict.getItem("filled_tin_can"))
			.withOutput(IC2Dict.getItem("crafting", "tin_can"))
			.withEnergyCostPerTick(30)
			.withOperationDuration(200)
			.register();
	}
}
