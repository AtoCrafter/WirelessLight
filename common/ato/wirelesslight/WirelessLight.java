package ato.wirelesslight;

import ato.wirelesslight.initializer.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
        modid = "WirelessLight",
        name = "Wireless Light",
        version = "@VERSION@"
)
@NetworkMod(
        clientSideRequired = true,
        serverSideRequired = true
)
public class WirelessLight {

    @Mod.Instance("WirelessLight")
    public static WirelessLight instance;
    @SidedProxy(
            serverSide = "ato.wirelesslight.initializer.CommonProxy",
            clientSide = "ato.wirelesslight.initializer.ClientProxy"
    )
    public static CommonProxy initializer;
    public static Config config;

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        initializer.load();
    }
}
