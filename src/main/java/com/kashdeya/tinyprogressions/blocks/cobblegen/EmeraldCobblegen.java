package com.kashdeya.tinyprogressions.blocks.cobblegen;

import java.util.List;

import com.kashdeya.tinyprogressions.main.TinyProgressions;
import com.kashdeya.tinyprogressions.tiles.TileEntityEmeraldCobblegen;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EmeraldCobblegen extends Block implements ITileEntityProvider{

	public EmeraldCobblegen()
	{
		super(Material.IRON);
		this.setHardness(2.5F);
		this.setHarvestLevel("pickaxe", 0);
		this.setResistance(1000.0F);
		this.setLightLevel(0.5F);
		this.setLightOpacity(1);
		this.setCreativeTab(TinyProgressions.tabTP);
		this.setSoundType(blockSoundType.METAL);
		this.setUnlocalizedName("emerald_cobblegen_block");
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return false;
    }
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if(world.isRemote)
    	{
    		return true;
    	}
    	
    	TileEntity tile = world.getTileEntity(pos);
    	
    	if(tile != null && tile instanceof TileEntityEmeraldCobblegen)
    	{
    		TileEntityEmeraldCobblegen ttest = (TileEntityEmeraldCobblegen)tile;
    		
    		if(!player.isSneaking())
    		{
        		ItemStack stack = ttest.removeStackFromSlot(0);
        		
	    		if(stack != null)
	    		{
		    		if(!player.inventory.addItemStackToInventory(stack))
		    		{
		    			//player.dropItem(stack, false);
		    			ForgeHooks.onPlayerTossEvent(player, stack, false);
		    		}
	    		}
    		} else
    		{
        		ItemStack stack = ttest.getStackInSlot(0);
    			player.addChatComponentMessage(new TextComponentString(Blocks.COBBLESTONE.getLocalizedName() + " x " + (stack == null? 0 : stack.stackSize)));
    		}
    	}
    	
        return true;
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tile = world.getTileEntity(pos);
        
        if (tile != null && tile instanceof TileEntityEmeraldCobblegen)
        {
            InventoryHelper.dropInventoryItems(world, pos, (TileEntityEmeraldCobblegen)tile);
        }
        
        super.breakBlock(world, pos, state);
    }
    
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityEmeraldCobblegen();
	}
    
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
    {
		list.add(TextFormatting.YELLOW + new TextComponentTranslation("tooltip.emeraldcobblegen_1").getFormattedText());
		list.add(TextFormatting.YELLOW + new TextComponentTranslation("tooltip.emeraldcobblegen_2").getFormattedText());
		list.add(TextFormatting.YELLOW + new TextComponentTranslation("tooltip.emeraldcobblegen_3").getFormattedText());
		list.add(TextFormatting.YELLOW + new TextComponentTranslation("tooltip.emeraldcobblegen_4").getFormattedText());
    }

}
