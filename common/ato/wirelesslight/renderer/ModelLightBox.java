package ato.wirelesslight.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class ModelLightBox extends ModelBase {

    private final ModelRenderer renderer;

    public ModelLightBox() {
        renderer = new ModelRenderer(this, 16, 16);
        renderer.addBox(0, 0, 0, 1, 1, 1);
    }

    public void render() {
        renderer.render(1F);
    }
}
