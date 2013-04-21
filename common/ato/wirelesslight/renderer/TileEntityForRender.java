package ato.wirelesslight.renderer;

import ato.wirelesslight.block.BlockLight;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * レンダリング用のダミー
 * ISimpleBlockRenderer では全てのブロックの描画後に透過処理をすることができないため
 */
public class TileEntityForRender extends TileEntity {

    public BlockLight getBlock() {
        int id = getWorldObj().getBlockId(xCoord, yCoord, zCoord);
        return (BlockLight) Block.blocksList[id];
    }
}
