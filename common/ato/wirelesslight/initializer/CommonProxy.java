package ato.wirelesslight.initializer;

import ato.wirelesslight.Config;
import ato.wirelesslight.WirelessLight;
import ato.wirelesslight.block.BlockLightBox;
import ato.wirelesslight.block.BlockLightPlate;
import ato.wirelesslight.item.ItemController;
import ato.wirelesslight.item.ItemTransparentizer;
import ato.wirelesslight.renderer.TileEntityForRender;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void load() {
        registerItems();
        GameRegistry.registerTileEntity(TileEntityForRender.class, "dammyForRenderer");
    }

    private void registerItems() {
        Config con = WirelessLight.config;
        GameRegistry.registerBlock(
                new BlockLightBox(con.blockIDLightBox, false).setBlockName("wirelesslight:lightbox"),
                "lightbox"
        );
        GameRegistry.registerBlock(
                new BlockLightBox(con.blockIDLightBoxTransparent, true).setBlockName("wirelesslight:lightbox.transparent"),
                "lightbox.transparent"
        );
        GameRegistry.registerBlock(
                new BlockLightPlate(con.blockIDLightPlate, false).setBlockName("wirelesslight:lightplate"),
                "lightplate"
        );
        GameRegistry.registerBlock(
                new BlockLightPlate(con.blockIDLightPlateTransparent, true).setBlockName("wirelesslight:lightplate.transparent"),
                "lightplate.transparent"
        );
        GameRegistry.registerItem(
                new ItemController(con.itemIDController).setItemName("wirelesslight:controller"),
                "controller"
        );
        GameRegistry.registerItem(
                new ItemTransparentizer(con.itemIDTransparentizer).setItemName("wirelesslight:transparentizer"),
                "transparentizer"
        );
    }
}
