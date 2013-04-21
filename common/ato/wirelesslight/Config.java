package ato.wirelesslight;

import net.minecraftforge.common.Configuration;

import java.io.File;

/**
 * ユーザーが設定可能な値の一覧
 */
public class Config {

    /**
     * 見える状態の箱状光源ブロックの ID
     */
    public final int blockIDLightBox;
    /**
     * 見えない状態の箱状光源ブロックの ID
     */
    public final int blockIDLightBoxTransparent;
    /**
     * 見える状態の板状光源ブロックの ID
     */
    public final int blockIDLightPlate;
    /**
     * 見えない状態の板状光源ブロックの ID
     */
    public final int blockIDLightPlateTransparent;
    /**
     * コントロールボックスのブロック ID
     */
    public final int blockIDControllerBox;
    public final int itemIDController;
    public final int itemIDTransparentizer;

    public Config(File file) {
        Configuration config = new Configuration(file);
        config.load();
        blockIDLightBox = config.getBlock("LightBox", 2189).getInt();
        blockIDLightBoxTransparent = config.getBlock("LightBoxTransparent", 2190).getInt();
        blockIDLightPlate = config.getBlock("LightPlate", 2191).getInt();
        blockIDLightPlateTransparent = config.getBlock("LightPlateTransparent", 2192).getInt();
        blockIDControllerBox = config.getBlock("ControllerBox", 2193).getInt();
        itemIDController = config.getItem("Controller", 21154).getInt();
        itemIDTransparentizer = config.getItem("Transparentizer", 21155).getInt();
        config.save();
    }
}
