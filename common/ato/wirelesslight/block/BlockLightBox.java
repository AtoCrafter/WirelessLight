package ato.wirelesslight.block;

import ato.wirelesslight.WirelessLight;
import net.minecraft.world.World;

import java.util.Random;

/**
 * 箱状の光源ブロック
 */
public class BlockLightBox extends BlockLight {

    public BlockLightBox(int id, boolean transparent) {
        super(id, transparent);
        setLightOpacity(255);
    }

    @Override
    protected int getDirection(int meta) {
        return 6;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return WirelessLight.config.blockIDLightBox;
    }

    @Override
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return WirelessLight.config.blockIDLightBox;
    }
}
