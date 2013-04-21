package ato.wirelesslight.renderer;

import net.minecraftforge.client.IRenderContextHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ContextHandler implements IRenderContextHandler {

    @Override
    public void beforeRenderContext() {
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    @Override
    public void afterRenderContext() {
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
