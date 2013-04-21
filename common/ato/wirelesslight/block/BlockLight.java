package ato.wirelesslight.block;

import ato.wirelesslight.WirelessLight;
import ato.wirelesslight.item.ItemController;
import ato.wirelesslight.item.ItemTransparentizer;
import ato.wirelesslight.renderer.TileEntityForRender;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * この MOD で使いされる光源ブロック
 */
public abstract class BlockLight extends Block {

    /**
     * 透明化されたブロックか？
     */
    private final boolean transparent;
    /**
     * 見えるか？
     */
    protected boolean visible;

    public BlockLight(int id, boolean transparent) {
        super(id, 0, Material.redstoneLight);
        setHardness(0);
        setCreativeTab(CreativeTabs.tabRedstone);
        this.transparent = transparent;
    }

    @Override
    public String getTextureFile() {
        return WirelessLight.texturePathBlock;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        return isLighting(meta) ? 1 : 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
                                    int par6, float par7, float par8, float par9) {
        if (player.getCurrentEquippedItem() != null) {
            Item item = player.getCurrentEquippedItem().getItem();
            if (item instanceof ItemTransparentizer || item instanceof ItemController) {
                return false;
            }
        }
        setLighting(world, x, y, z, !isLighting(world, x, y, z));
        return true;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return isLighting(world, x, y, z) ? 15 : 0;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityForRender();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        ItemStack cis = FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem();
        if (cis != null) {
            int id = cis.getItem().itemID - 256;
            visible = id == WirelessLight.config.itemIDController ||
                    id == WirelessLight.config.itemIDTransparentizer;
        } else {
            visible = false;
        }
        world.markBlockForRenderUpdate(x, y, z);
    }

    @Override
    public int getRenderType() {
        return isRenderable() ? super.getRenderType() : -1;
    }

    /**
     * 指定ブロックが発光しているかどうか
     */
    public boolean isLighting(IBlockAccess access, int x, int y, int z) {
        return isLighting(access.getBlockMetadata(x, y, z));
    }

    /**
     * 指定ブロックの向き
     */
    public int getDirection(IBlockAccess access, int x, int y, int z) {
        return getDirection(access.getBlockMetadata(x, y, z));
    }

    /**
     * 指定ブロックが発光するかしないかを設定
     */
    public void setLighting(World world, int x, int y, int z, boolean light) {
        int meta = world.getBlockMetadata(x, y, z);
        int direction = getDirection(meta);
        world.setBlockMetadataWithNotify(x, y, z, getMeta(direction, light));
        world.markBlockForUpdate(x, y, z);
    }

    /**
     * ブロックの向きと発光の有無からメタデータを作成
     */
    protected int getMeta(int direction, boolean lighting) {
        return ((lighting ? 1 : 0) << 3) + (direction & 7);
    }

    /**
     * ブロックが張り付いている向きを取得
     */
    protected int getDirection(int meta) {
        return meta & 7;
    }

    /**
     * ブロックが発光しているかどうか
     */
    protected boolean isLighting(int meta) {
        return ((meta >> 3) & 1) != 0;
    }

    /**
     * ブロックをレンダリングするか？
     */
    public boolean isRenderable() {
        return !transparent || visible;
    }
}
