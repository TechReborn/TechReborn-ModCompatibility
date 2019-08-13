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

package techreborn.compatmod.ic2.experimental;

import ic2.api.item.IC2Items;
import ic2.core.profile.ProfileManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import reborncore.api.recipe.RecipeHandler;
import reborncore.common.util.ItemUtils;
import reborncore.common.util.RebornCraftingHelper;
import techreborn.api.Reference;
import techreborn.api.recipe.machines.BlastFurnaceRecipe;
import techreborn.api.recipe.machines.IndustrialGrinderRecipe;
import techreborn.compatmod.ic2.IC2Dict;
import techreborn.init.ModBlocks;
import techreborn.init.ModFluids;
import techreborn.init.ModItems;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ingredients.ItemIngots;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IC2RecipesExperimental {
	public static void registerRecipes() {
		RebornCraftingHelper.addShapelessRecipe(IC2Dict.getItem("electric_wrench"), new ItemStack(ModItems.WRENCH),
				IC2Dict.getItem("crafting", "small_power_unit"));

		// Reactor
		if (ProfileManager.selected.equals(ProfileManager.get("Experimental"))) {
			// Nuclear Reactor
			RebornCraftingHelper.addShapedRecipe(IC2Dict.getItem("te", "nuclear_reactor"), "PCP", "RRR", "PGP", 'C', "circuitAdvanced", 'R', IC2Dict.getItem("te", "reactor_chamber"), 'G', new ItemStack(ModBlocks.SOLID_FUEL_GENEREATOR), 'P', IC2Dict.getItem("plating"));

			// Reactor Chamber
			RebornCraftingHelper.addShapedRecipe(IC2Dict.getItem("te", "reactor_chamber"), " P ", "PBP", " P ", 'P', IC2Dict.getItem("plating"), 'B', "machineBlockBasic");

			// Reactor Plating
			RebornCraftingHelper.addShapelessRecipe(IC2Dict.getItem("plating"), "plateLead",
				"plateAdvancedAlloy");
		}

		// Ores
		if (ProfileManager.selected.equals(ProfileManager.get("Experimental"))) {
			// Iridium related recipes
			// Blast furnace
			RecipeHandler.addRecipe(new BlastFurnaceRecipe("gemIridium", null, ItemIngots.getIngotByName("iridium"), null, 1700,
				128, 1700));

			// Industrial Grinder
			removeRecipe(Reference.INDUSTRIAL_GRINDER_RECIPE, "oreIridium");

			ItemStack iridium = IC2Items.getItem("misc_resource", "iridium_ore");
			RecipeHandler.addRecipe(new IndustrialGrinderRecipe(RecipeMethods.getOre("oreIridium"), WATER, iridium.copy(), RecipeMethods.getMaterial("iridium", 6, RecipeMethods.Type.SMALL_DUST), RecipeMethods.getMaterial("platinum", 2, RecipeMethods.Type.SMALL_DUST), null, 100, 128));
			RecipeHandler.addRecipe(new IndustrialGrinderRecipe(RecipeMethods.getOre("oreIridium"), MERCURY, iridium.copy(), RecipeMethods.getMaterial("iridium", 6, RecipeMethods.Type.SMALL_DUST), RecipeMethods.getMaterial("platinum", 2, RecipeMethods.Type.DUST), null, 100, 128));
		}
	}

	public static void removeCraftingTableDuplicates(RegistryEvent.Register<IRecipe> event) {
		IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();

		List<ResourceLocation> recipes = Stream.of(modRegistry.getKeys().toArray())
			.map(entry -> (ResourceLocation) entry)
			.filter(entry -> entry.getNamespace().equalsIgnoreCase("ic2"))
			.collect(Collectors.toList());

		recipes.forEach(entry -> {
			IRecipe recipe = (IRecipe) modRegistry.getValue(entry);

			if (recipe != null) {
				ItemStack output = recipe.getRecipeOutput();
				if (ItemUtils.isItemEqual(output, IC2Dict.getItem("plating"), true, true))
					modRegistry.remove(entry);
			}
		});
	}

	public static void removeRecipe(String machine, String entry) {
		RecipeHandler.recipeList.removeIf(recipeType -> {
				if (!recipeType.getRecipeName().equals(machine)) return false;

				return recipeType.getInputs().stream()
					.anyMatch(ingredient ->
						ingredient instanceof ItemStack && ItemUtils.isInputEqual(entry, (ItemStack) ingredient, false, false, true));
			});
	}

	// Fields >>
	private static FluidStack WATER = new FluidStack(FluidRegistry.WATER, 1000);
	private static FluidStack MERCURY = new FluidStack(ModFluids.MERCURY, 1000);
	private static FluidStack SODIUM_PERSULFATE = new FluidStack(ModFluids.SODIUMPERSULFATE, 1000);
	// << Fields
}
