package kihira.incantations.common.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import kihira.incantations.Incantations;
import kihira.incantations.common.inventory.ContainerWritingTable;
import kihira.incantations.util.LanguageUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;

public abstract class IncantationsMessage implements IMessage {
    public abstract IMessage onMessage(IncantationsMessage message, ChannelHandlerContext ctx, Side side);

    public static class WritingDeskScrollUpdateMessage extends IncantationsMessage {
        private String newScrollData;

        public WritingDeskScrollUpdateMessage() {}
        public WritingDeskScrollUpdateMessage(String newScrollData) {
            this.newScrollData = newScrollData;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            this.newScrollData = ByteBufUtils.readUTF8String(buf);
        }

        @Override
        public void toBytes(ByteBuf buf) {
            ByteBufUtils.writeUTF8String(buf, this.newScrollData);
        }

        @Override
        public IMessage onMessage(IncantationsMessage message, ChannelHandlerContext ctx, Side side) {
            if (side == Side.SERVER) {
                NetHandlerPlayServer netHandler = (NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
                EntityPlayer player = netHandler.playerEntity;
                if (player.openContainer instanceof ContainerWritingTable) {
                    ContainerWritingTable writingTable = (ContainerWritingTable) player.openContainer;
                    Slot slot = writingTable.getSlot(0);
                    //Get current modifier on scroll
                    int modifier = 0;
                    if (slot.getHasStack()) {
                        ItemStack scroll = slot.getStack();
                        if (scroll.hasTagCompound()) {
                            NBTTagCompound tagCompound = scroll.getTagCompound();
                            modifier = tagCompound.getInteger("modifier");
                        }
                    }
                    //Calculate new modifier
                    slot = writingTable.getSlot(3);
                    ItemStack itemStack = slot.getStack();
                    if (itemStack != null) {
                        if (itemStack.getItem() == Items.glowstone_dust) modifier += 2;
                        else if (itemStack.getItem() instanceof ItemRedstone) modifier++;
                        slot.decrStackSize(1);
                    }
                    //Write the scroll
                    slot = writingTable.getSlot(0);
                    if (slot.getHasStack()) {
                        ItemStack oldScroll = slot.getStack();
                        if (oldScroll.hasTagCompound()) {
                            NBTTagCompound data = oldScroll.getTagCompound();
                            itemStack = LanguageUtil.writeScroll(data.getString("incantation") + this.newScrollData, modifier);
                        }
                        else itemStack = LanguageUtil.writeScroll(this.newScrollData, modifier);
                    }
                    else itemStack = LanguageUtil.writeScroll(this.newScrollData, modifier);
                    slot.putStack(itemStack);
                    //Damage the writing tools
                    //Ink
                    slot = writingTable.getSlot(2);
                    itemStack = slot.getStack();
                    if (itemStack != null) {
                        if (itemStack.getItemDamage() + this.newScrollData.replace("|", "").length() >= itemStack.getMaxDamage()) slot.putStack(new ItemStack(Items.glass_bottle));
                        else {
                            itemStack.setItemDamage(itemStack.getItemDamage() + this.newScrollData.replace("|", "").length());
                            slot.putStack(itemStack);
                        }
                    }
                    //Quill
                    slot = writingTable.getSlot(4);
                    itemStack = slot.getStack();
                    if (itemStack != null) {
                        if (itemStack.getItemDamage() + this.newScrollData.replace("|", "").length() >= itemStack.getMaxDamage()) slot.putStack(null);
                        else {
                            itemStack.setItemDamage(itemStack.getItemDamage() + this.newScrollData.replace("|", "").length());
                            slot.putStack(itemStack);
                        }
                    }
                    writingTable.getWritingDesk().markDirty();
                }
            }
            Incantations.logger.info("On Message!");
            return null;
        }
    }
}
