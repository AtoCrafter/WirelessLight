package ato.wirelesslight.block;

import ato.wirelesslight.WirelessLight;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

import static net.minecraftforge.common.ForgeDirection.*;

/**
 * 板状の光源ブロック
 */
public class BlockLightPlate extends BlockLight {

    public static final float THINNESS = 1.0F / 32;

    public BlockLightPlate(int id, boolean transparent) {
        super(id, transparent);
        setBlockBounds(0, 0, 0, 1, THINNESS, 1);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @Override
    public void setBlockBoundsForItemRender() {
        setBlockBounds(0, 0, 0, 1, THINNESS, 1);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        int meta = access.getBlockMetadata(x, y, z);
        ForgeDirection dir = ForgeDirection.getOrientation(getDirection(meta));

        switch (dir) {
            case DOWN:
                setBlockBounds(0, 1 - THINNESS, 0, 1, 1, 1);
                break;
            case UP:
                setBlockBounds(0, 0, 0, 1, THINNESS, 1);
                break;
            case NORTH:
                setBlockBounds(0, 0, 1 - THINNESS, 1, 1, 1);
                break;
            case SOUTH:
                setBlockBounds(0, 0, 0, 1, 1, THINNESS);
                break;
            case WEST:
                setBlockBounds(1 - THINNESS, 0, 0, 1, 1, 1);
                break;
            case EAST:
                setBlockBounds(0, 0, 0, THINNESS, 1, 1);
                break;
            default:
                setBlockBounds(0, 0, 0, 0, 0, 0);
                break;
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side,
                             float par6, float par7, float par8, int meta) {
        return side;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        int meta = world.getBlockMetadata(x, y, z);
        if (!canPlaceBlockOnSide(world, x, y, z, getDirection(meta))) {
            dropBlockAsItem(world, x, y, z, 0, 0);
            world.setBlockWithNotify(x, y, z, 0);
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        return (dir == DOWN && world.isBlockSolidOnSide(x, y + 1, z, DOWN)) ||
                (dir == UP && world.isBlockSolidOnSide(x, y - 1, z, UP)) ||
                (dir == NORTH && world.isBlockSolidOnSide(x, y, z + 1, NORTH)) ||
                (dir == SOUTH && world.isBlockSolidOnSide(x, y, z - 1, SOUTH)) ||
                (dir == WEST && world.isBlockSolidOnSide(x + 1, y, z, WEST)) ||
                (dir == EAST && world.isBlockSolidOnSide(x - 1, y, z, EAST));
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.isBlockSolidOnSide(x - 1, y, z, EAST) ||
                world.isBlockSolidOnSide(x + 1, y, z, WEST) ||
                world.isBlockSolidOnSide(x, y, z - 1, SOUTH) ||
                world.isBlockSolidOnSide(x, y, z + 1, NORTH) ||
                world.isBlockSolidOnSide(x, y - 1, z, UP) ||
                world.isBlockSolidOnSide(x, y + 1, z, DOWN);
    }

    @Override
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return WirelessLight.config.blockIDLightPlate;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return WirelessLight.config.blockIDLightPlate;
    }
}
