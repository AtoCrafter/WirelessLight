package ato.wirelesslight.item;

import ato.wirelesslight.WirelessLight;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTransparentizer extends Item {

    public ItemTransparentizer(int id) {
        super(id);
        setMaxStackSize(1);
        setMaxDamage(256);
        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public int getIconFromDamage(int par1) {
        return 17;
    }

    @Override
    public String getTextureFile() {
        return WirelessLight.texturePathBlock;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
                             int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        int blockID = world.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (blockID == WirelessLight.config.blockIDLightBox) {
            world.setBlockAndMetadataWithNotify(x, y, z, WirelessLight.config.blockIDLightBoxTransparent, meta);
        } else if (blockID == WirelessLight.config.blockIDLightPlate) {
            world.setBlockAndMetadataWithNotify(x, y, z, WirelessLight.config.blockIDLightPlateTransparent, meta);
        } else {
            return false;
        }
        stack.damageItem(1, player);
        return true;
    }
}