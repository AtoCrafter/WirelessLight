package ato.wirelesslight.block;

/**
 * 箱状の光源ブロック
 */
public class BlockLightBox extends BlockLight {

    public BlockLightBox(int id, boolean transparent) {
        super(id, transparent);
    }

    @Override
    protected int getDirection(int meta) {
        return 6;
    }
}
