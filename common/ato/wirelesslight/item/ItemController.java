package ato.wirelesslight.item;

import ato.wirelesslight.WirelessLight;
import ato.wirelesslight.block.BlockLight;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemController extends Item {

    public static final String[] modeDescription = new String[]{
            "wirelesslight.controller.mode.register",
            "wirelesslight.controller.mode.arearegister",
            "wirelesslight.controller.mode.allregister",
            "wirelesslight.controller.mode.switch",
            "wirelesslight.controller.mode.install",
    };
    private ArrayList<Pos> list;
    private boolean switchOn;
    private Pos startPoint;

    public ItemController(int id) {
        super(id);
        setMaxStackSize(1);
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabRedstone);

        list = new ArrayList<Pos>();
    }

    @Override
    public int getIconFromDamage(int damage) {
        return 16;
    }

    @Override
    public String getTextureFile() {
        return WirelessLight.texturePathBlock;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
                             int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) return false;

        switch (stack.getItemDamage()) {
            case 0:
                int blockID = world.getBlockId(x, y, z);
                if (!(Block.blocksList[blockID] instanceof BlockLight))
                    return false;

                if (!world.isRemote)
                    registerBlockInControll(player, stack, x, y, z);

                return true;
            case 1:
                if (!world.isRemote) {
                    if (startPoint == null) {
                        startPoint = new Pos(x, y, z);
                        player.addChatMessage(StringTranslate.getInstance().translateKey("wirelesslight.controller.setstartpoint") + ": " + startPoint);
                    } else {
                        Pos endPoint = new Pos(x, y, z);
                        registerBlocksInControll(world, player, stack, startPoint, endPoint);
                        startPoint = null;
                    }
                }
                return true;
//            case 4:
//                if (!world.isRemote) {
//                    if (world.getBlockId(x, y, z) == WirelessLight.blockController.blockID) {
//                        if (((BlockController) WirelessLight.blockController).installFromItemStack(world, x, y, z, stack.copy())) {
//                            --stack.stackSize;
//                            return true;
//                        }
//                    }
//                }
//                break;
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (world.isRemote) return itemstack;
        load(itemstack);
        if (player.isSneaking()) {
            changeMode(player, itemstack);
        } else {
            switch (itemstack.getItemDamage()) {
                case 2:
                    unregisterAllBlocks(player, itemstack);
                    break;
                case 3:
                    toggleSwitch(player, itemstack, world);
                    break;
            }
        }
        return itemstack;
    }

    public void registerBlockInControll(EntityPlayer player, ItemStack is, int x, int y, int z) {
        load(is);
        Pos pos = new Pos(x, y, z);
        if (list.contains(pos)) {
            list.remove(pos);
            player.addChatMessage(StringTranslate.getInstance().translateKey("wirelesslight.controller.unregister") + ": " + pos);
        } else {
            list.add(pos);
            player.addChatMessage(StringTranslate.getInstance().translateKey("wirelesslight.controller.register") + ": " + pos);
        }
        save(is);
    }

    public void registerBlocksInControll(World world, EntityPlayer player, ItemStack is, Pos from, Pos to) {
        load(is);
        int size = 0;
        for (int x = Math.min(from.x, to.x); x <= Math.max(from.x, to.x); ++x) {
            for (int y = Math.min(from.y, to.y); y <= Math.max(from.y, to.y); ++y) {
                for (int z = Math.min(from.z, to.z); z <= Math.max(from.z, to.z); ++z) {
                    int blockId = world.getBlockId(x, y, z);
                    if (Block.blocksList[blockId] instanceof BlockLight) {
                        list.add(new Pos(x, y, z));
                        ++size;
                    }
                }
            }
        }
        save(is);
        player.addChatMessage(StringTranslate.getInstance().translateKey("wirelesslight.controller.registerblocks")
                + ": from " + from + " to " + to + " (" + size + ")");
    }

    public void unregisterAllBlocks(EntityPlayer player, ItemStack is) {
        load(is);
        list.clear();
        save(is);
        player.addChatMessage(StringTranslate.getInstance().translateKey("wirelesslight.controller.unregisterblocks"));
    }

//    @Override
//    public void addInformation(ItemStack is, List infoList) {
//        load(is);
//        infoList.add(StringTranslate.getInstance().translateKey("wirelesslight.controller.blocksincontroll")
//                + ": " + list.size());
//    }

    private void save(ItemStack is) {
        NBTTagCompound nbttc = new NBTTagCompound();

        NBTTagList listOfBlocks = new NBTTagList("ListOfBlocks");
        for (Pos p : list) {
            NBTTagCompound postag = new NBTTagCompound("Pos");
            p.writeToNBT(postag);
            listOfBlocks.appendTag(postag);
        }
        nbttc.setTag("ListOfBlocks", listOfBlocks);

        nbttc.setBoolean("switch", switchOn);

        is.setTagCompound(nbttc);
    }

    private void load(ItemStack is) {
        list.clear();
        NBTTagCompound nbttc = is.getTagCompound();
        if (nbttc != null) {

            NBTTagList listOfBlocks = nbttc.getTagList("ListOfBlocks");
            if (list != null) {
                for (int i = 0; i < listOfBlocks.tagCount(); ++i) {
                    NBTTagCompound postag = (NBTTagCompound) listOfBlocks.tagAt(i);
                    list.add(new Pos(postag));
                }
            }

            switchOn = nbttc.getBoolean("switch");
        }
    }

    private void changeMode(EntityPlayer player, ItemStack is) {
        is.setItemDamage((is.getItemDamage() + 1) % modeDescription.length);
        player.addChatMessage(StringTranslate.getInstance().translateKey("wirelesslight.controller.modechange")
                + ": " + StringTranslate.getInstance().translateKey(modeDescription[is.getItemDamage()]));
        startPoint = null;
    }

    public void toggleSwitch(EntityPlayer player, ItemStack is, World world) {
        load(is);
        switchOn(player, is, world, !switchOn);
    }

    public void switchOn(EntityPlayer player, ItemStack is, World world, boolean on) {
        load(is);
        switchOn = on;
        save(is);

        Iterator<Pos> ite = list.iterator();
        while (ite.hasNext()) {
            Pos p = ite.next();
            Block block = Block.blocksList[world.getBlockId(p.x, p.y, p.z)];
            if (block instanceof BlockLight) {
                BlockLight light = (BlockLight) block;
                light.setLighting(world, p.x, p.y, p.z, on);
            }
        }

        player.addChatMessage(StringTranslate.getInstance().translateKey("wirelesslight.controller.switch")
                + (on ? " ON" : " OFF") + " (" + list.size() + ")");
    }

    /**
     * 座標
     */
    private class Pos {

        private final int x, y, z;

        private Pos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        private Pos(NBTTagCompound nbt) {
            x = nbt.getInteger("x");
            y = nbt.getInteger("y");
            z = nbt.getInteger("z");
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "," + z + ")";
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(x).hashCode() + Integer.valueOf(y).hashCode() + Integer.valueOf(z).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pos) {
                Pos other = (Pos) obj;
                return x == other.x && y == other.y && z == other.z;
            } else {
                return false;
            }
        }

        private void writeToNBT(NBTTagCompound nbt) {
            nbt.setInteger("x", x);
            nbt.setInteger("y", y);
            nbt.setInteger("z", z);
        }
    }
}
