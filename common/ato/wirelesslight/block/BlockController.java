package ato.wirelesslight.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ato.wirelesslight.WirelessLight;
import ato.wirelesslight.item.ItemController;
import ato.wirelesslight.tileentity.TileEntityController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockController extends BlockContainer {

    public BlockController(int id) {
        super(id, Material.circuits);
        setCreativeTab(CreativeTabs.tabRedstone);
        setHardness(0.2F);
    }

    @Override
    public boolean isBlockNormalCube(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityController();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
        if (world.isRemote) return;
        ItemStack is = getItemStack(world, x, y, z);
        if (is == null) return;
        if (Block.blocksList[blockID] instanceof BlockLight) return;
        ((ItemController) Item.itemsList[WirelessLight.config.itemIDController + 256])
                .switchOn(null, is, world, world.isBlockIndirectlyGettingPowered(x, y, z));
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        ItemStack is = getItemStack(world, x, y, z);
        if (is != null) {
            EntityItem ei = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, is);
            world.spawnEntityInWorld(ei);
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
                                    int par6, float par7, float par8, float par9) {
        if (player.isSneaking()) return false;
        if (world.isRemote) return true;
        ItemStack is = getItemStack(world, x, y, z);
        if (is == null) return false;
        ((ItemController) Item.itemsList[WirelessLight.config.itemIDController + 256])
                .toggleSwitch(player, is, world);
        return true;
    }

    @Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
        blockIcon = ir.registerIcon("gravel");
	}

	public boolean installFromItemStack(IBlockAccess access, int x, int y, int z, ItemStack is) {
        TileEntityController tileEntity = (TileEntityController) access.getBlockTileEntity(x, y, z);
        if (tileEntity == null) {
            return false;
        } else {
            if (tileEntity.getStackInSlot(0) != null) return false;
            tileEntity.setInventorySlotContents(0, is);
            return true;
        }
    }

    private ItemStack getItemStack(IBlockAccess access, int x, int y, int z) {
        TileEntityController tileEntity = (TileEntityController) access.getBlockTileEntity(x, y, z);
        if (tileEntity == null) return null;
        return tileEntity.getStackInSlot(0);
    }

}
