package ato.wirelesslight.initializer;

import ato.wirelesslight.Config;
import ato.wirelesslight.WirelessLight;
import ato.wirelesslight.block.BlockController;
import ato.wirelesslight.block.BlockLightBox;
import ato.wirelesslight.block.BlockLightPlate;
import ato.wirelesslight.item.ItemController;
import ato.wirelesslight.item.ItemTransparentizer;
import ato.wirelesslight.renderer.TileEntityForRender;
import ato.wirelesslight.tileentity.TileEntityController;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommonProxy {

    public void load() {
        registerItems();
        GameRegistry.registerTileEntity(TileEntityForRender.class, "dammyForRenderer");
        registerRecipes();
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
        GameRegistry.registerBlock(
                new BlockController(con.blockIDControllerBox).setBlockName("wirelesslight:controllerbox"),
                "controllerbox"
        );
        GameRegistry.registerItem(
                new ItemController(con.itemIDController).setItemName("wirelesslight:controller"),
                "controller"
        );
        GameRegistry.registerItem(
                new ItemTransparentizer(con.itemIDTransparentizer).setItemName("wirelesslight:transparentizer"),
                "transparentizer"
        );
        GameRegistry.registerTileEntity(TileEntityController.class, "tileentity.controller");
    }

    private void registerRecipes() {
        Item itemLightBox = Item.itemsList[WirelessLight.config.blockIDLightBox];
        Item itemLightPlate = Item.itemsList[WirelessLight.config.blockIDLightPlate];
        Item itemControllerBox = Item.itemsList[WirelessLight.config.blockIDControllerBox];
        Item itemController = Item.itemsList[WirelessLight.config.itemIDController+256];
        Item itemTranparentizer = Item.itemsList[WirelessLight.config.itemIDTransparentizer+256];

        GameRegistry.addRecipe(new ItemStack(itemLightBox), new Object[]{
                "PPP",
                "RGR",
                "PPP",
                'P', Block.thinGlass,
                'R', Item.redstone,
                'G', Item.lightStoneDust,
        });
        GameRegistry.addRecipe(new ItemStack(itemLightPlate, 6), new Object[] {
                "PRP",
                "GGG",
                "PPP",
                'P', Block.thinGlass,
                'R', Item.redstone,
                'G', Item.lightStoneDust,
        });
        GameRegistry.addRecipe(new ItemStack(itemControllerBox), new Object[] {
                "ISI",
                "R R",
                "ISI",
                'I', Item.ingotIron,
                'R', Item.redstone,
                'S', Item.stick,
        });
        GameRegistry.addRecipe(new ItemStack(itemController), new Object[] {
                "I",
                "W",
                "L",
                'I', Item.ingotIron,
                'W', itemLightBox,
                'L', Block.lever,
        });
        GameRegistry.addRecipe(new ItemStack(itemTranparentizer), new Object[] {
                "P",
                "R",
                "S",
                'P', Item.fermentedSpiderEye,
                'R', Item.redstone,
                'S', Item.stick,
        });
    }
}
