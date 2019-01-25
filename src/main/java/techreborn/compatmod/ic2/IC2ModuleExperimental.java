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

package techreborn.compatmod.ic2;

import ic2.api.item.IC2Items;
import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.item.tool.ItemTreetap;
import ic2.core.ref.ItemName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import reborncore.api.recipe.RecipeHandler;
import reborncore.common.registration.RebornRegistry;
import reborncore.common.registration.impl.ConfigRegistry;
import reborncore.common.util.RebornCraftingHelper;
import techreborn.api.IC2Helper;
import techreborn.api.Reference;
import techreborn.api.TechRebornAPI;
import techreborn.api.recipe.machines.CompressorRecipe;
import techreborn.api.recipe.machines.ExtractorRecipe;
import techreborn.api.recipe.machines.GrinderRecipe;
import techreborn.compat.ICompatModule;
import techreborn.init.IC2Duplicates;
import techreborn.init.ModBlocks;
import techreborn.init.ModItems;
import techreborn.init.recipes.ChemicalReactorRecipes;
import techreborn.init.recipes.RecipeMethods;
import techreborn.items.ingredients.ItemParts;
import techreborn.lib.ModInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mark on 06/06/2016.
 */
//We load this as TR so we can get the config options in there, and this is mainly recipes
@RebornRegistry(modOnly = "ic2,!ic2-classic-spmod", modID = ModInfo.MOD_ID)
public class IC2ModuleExperimental implements ICompatModule, IC2Helper {

	@ConfigRegistry(config = "ic2", comment = "When enabled all of TR's compressor recipes are added to the IC2 compressor (Requies deduplication) (Requires restart)")
	public static boolean syncCompressorRecipes = true;

	List<RecipeDuplicate> recipeDuplicateList = new ArrayList<>();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		TechRebornAPI.ic2Helper = this;
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if(IC2Duplicates.deduplicate() && syncCompressorRecipes){
			cloneMachineRecipes(Reference.COMPRESSOR_RECIPE, Recipes.compressor);
		}
	}

	private void cloneMachineRecipes(String machine, IBasicMachineRecipeManager recipeManager){
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

	// LOW is used as we want it to load as late as possible, but before crafttweaker
	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		recipeDuplicateList.add(new RecipeDuplicate(new ItemStack(ModBlocks.MACHINE_FRAMES, 1, 0),
				IC2Items.getItem("resource", "machine")));

		for (RecipeDuplicate duplicate : recipeDuplicateList) {
			duplicate.add();
		}

		RebornCraftingHelper.addShapelessRecipe(ItemParts.getPartByName("rubber"),
				IC2Items.getItem("crafting", "rubber"));
		RebornCraftingHelper.addShapelessRecipe(IC2Items.getItem("crafting", "rubber"),
				ItemParts.getPartByName("rubber"));
		RebornCraftingHelper.addShapelessRecipe(IC2Items.getItem("electric_wrench"), new ItemStack(ModItems.WRENCH),
				IC2Items.getItem("crafting", "small_power_unit"));

		RecipeHandler.addRecipe(new CompressorRecipe(IC2Items.getItem("crafting", "carbon_mesh"),
				IC2Items.getItem("crafting", "carbon_plate"), 300, 4));
		RecipeHandler.addRecipe(new CompressorRecipe(IC2Items.getItem("crafting", "coal_ball"),
				IC2Items.getItem("crafting", "coal_block"), 300, 4));

		RecipeHandler.addRecipe(new GrinderRecipe(ItemName.crafting.getItemStack("tin_can"),
				RecipeMethods.getOre("dustTin", 2), 300, 16));

		RecipeHandler.addRecipe(new ExtractorRecipe(ItemName.filled_tin_can.getItemStack(),
				ItemName.crafting.getItemStack("tin_can"), 300, 16));

		RecipeHandler.addRecipe(
				new ExtractorRecipe(IC2Dict.getItem("misc_resource", "resin"),
						ItemParts.getPartByName("rubber", 3), 400, 2));

		ItemStack f = IC2Items.getItem("crop_res", "fertilizer");
		ChemicalReactorRecipes.register(RecipeMethods.getMaterial("calcite", RecipeMethods.Type.DUST), RecipeMethods.getMaterial("sulfur", RecipeMethods.Type.DUST), f, 40);
	}

	@Override
	public void initDuplicates() {
		IC2Dict.init();
	}

	@Override
	public boolean extractSap(EntityPlayer player, World world, BlockPos pos, EnumFacing side, IBlockState state, List<ItemStack> stacks) {
		if(state.getBlock() != Block.getBlockFromItem(IC2Items.getItem("rubber_wood").getItem())){
			return false;
		}
		return ItemTreetap.attemptExtract(player, world, pos, side, state, null);
	}

	public class RecipeDuplicate {
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
