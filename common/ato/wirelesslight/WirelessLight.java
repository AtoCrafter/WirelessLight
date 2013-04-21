package ato.wirelesslight;

import ato.wirelesslight.initializer.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
        modid = "WirelessLight",
        name = "Wireless Light",
        version = "1.1.0"
)
@NetworkMod(
        clientSideRequired = true,
        serverSideRequired = true
)
public class WirelessLight {

    @Instance("WirelessLight")
    public static WirelessLight instance;
    @SidedProxy(
            serverSide = "ato.wirelesslight.initializer.CommonProxy",
            clientSide = "ato.wirelesslight.initializer.ClientProxy"
    )
    public static CommonProxy initializer;
    public static Config config;

    public static final String texturePathBright = "/mods/wirelesslight/textures/bright.png";
    public static final String texturePathBlock = "/mods/wirelesslight/textures/Light.png";

    @PreInit
    public void preLoad(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Init
    public void load(FMLInitializationEvent event) {
        initializer.load();
    }
}
