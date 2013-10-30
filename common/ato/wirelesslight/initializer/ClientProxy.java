package ato.wirelesslight.initializer;

import ato.wirelesslight.WirelessLight;
import ato.wirelesslight.renderer.LightRenderer;
import ato.wirelesslight.renderer.TileEntityForRender;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void load() {
        super.load();
        registerLocalizations();
        registerRenderers();
    }

    /**
     * 言語ファイルの登録
     */
    private void registerLocalizations() {
        registerLocalization("en_US");
        registerLocalization("ja_JP");
    }

    /**
     * 言語ファイルの登録
     */
    private void registerLocalization(String lang) {
        LanguageRegistry.instance().loadLocalization("/mods/wirelesslight/lang/" + lang + ".properties", lang, false);
    }

    /**
     * レンダラの登録
     */
    private void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForRender.class, new LightRenderer());
    }
}
