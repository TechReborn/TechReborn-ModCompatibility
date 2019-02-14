package techreborn.compatmod.ic2.experimental;

import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.api.ICustomToolHandler;

public class IC2EletricalWrench implements ICustomToolHandler {

	Item electricWrench;

	public IC2EletricalWrench() {
		electricWrench = IC2Items.getItem("electric_wrench").getItem();
	}

	@Override
	public boolean canHandleTool(ItemStack stack) {
		if(stack.getItem() == electricWrench){
			return ElectricItem.manager.canUse(stack, 100);
		}
		return false;
	}

	@Override
	public boolean handleTool(ItemStack stack, BlockPos pos, World world, EntityPlayer player, EnumFacing side, boolean damage) {
		if(canHandleTool(stack)){
			ElectricItem.manager.use(stack, 100, player);
			return true;
		}
		return false;
	}
}
