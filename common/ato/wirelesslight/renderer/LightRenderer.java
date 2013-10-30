package ato.wirelesslight.renderer;

import ato.wirelesslight.WirelessLight;
import ato.wirelesslight.block.BlockLight;
import ato.wirelesslight.block.BlockLightPlate;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class LightRenderer extends TileEntitySpecialRenderer {

    private static final ModelLightBox model = new ModelLightBox();

    @Override
    public void renderTileEntityAt(TileEntity te, double xCoord, double yCoord, double zCoord, float var8) {
        if (te instanceof TileEntityForRender) {
            BlockLight block = ((TileEntityForRender) te).getBlock();
            if (block != null
                    && block.isRenderable()
                    && block.isLighting(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord)) {

                final float expansion = 0.05F;
                final float scale = 1F + expansion * 2;
                final float alpha = 0.5F;
                final float shift = 1 - BlockLightPlate.THINNESS;

                GL11.glPushMatrix();
//                ForgeHooksClient.bindTexture(WirelessLight.texturePathBright, 1);
                GL11.glDepthMask(false);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);

                int meta = block.getDirection(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
                ForgeDirection dir = ForgeDirection.getOrientation(meta);
                switch (dir) {
                    case UP:
                        GL11.glTranslatef(
                                (float) xCoord - expansion / scale,
                                (float) yCoord - expansion / scale,
                                (float) zCoord - expansion / scale
                        );
                        GL11.glScalef(scale, scale - shift, scale);
                        break;
                    case DOWN:
                        GL11.glTranslatef(
                                (float) xCoord - expansion / scale,
                                (float) yCoord - expansion / scale + shift,
                                (float) zCoord - expansion / scale
                        );
                        GL11.glScalef(scale, scale - shift, scale);
                        break;
                    case SOUTH:
                        GL11.glTranslatef(
                                (float) xCoord - expansion / scale,
                                (float) yCoord - expansion / scale,
                                (float) zCoord - expansion / scale
                        );
                        GL11.glScalef(scale, scale, scale - shift);
                        break;
                    case NORTH:
                        GL11.glTranslatef(
                                (float) xCoord - expansion / scale,
                                (float) yCoord - expansion / scale,
                                (float) zCoord - expansion / scale + shift
                        );
                        GL11.glScalef(scale, scale, scale - shift);
                        break;
                    case EAST:
                        GL11.glTranslatef(
                                (float) xCoord - expansion / scale,
                                (float) yCoord - expansion / scale,
                                (float) zCoord - expansion / scale
                        );
                        GL11.glScalef(scale - shift, scale, scale);
                        break;
                    case WEST:
                        GL11.glTranslatef(
                                (float) xCoord - expansion / scale + shift,
                                (float) yCoord - expansion / scale,
                                (float) zCoord - expansion / scale
                        );
                        GL11.glScalef(scale - shift, scale, scale);
                        break;
                    default:
                        GL11.glTranslatef(
                                (float) xCoord - expansion / scale,
                                (float) yCoord - expansion / scale,
                                (float) zCoord - expansion / scale
                        );
                        GL11.glScalef(scale, scale, scale);
                        break;
                }

                model.render();

//                ForgeHooksClient.unbindTexture();
                GL11.glDepthMask(true);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
        }
    }
}
