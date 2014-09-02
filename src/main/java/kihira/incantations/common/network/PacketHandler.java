package kihira.incantations.common.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.EnumMap;

public class PacketHandler extends FMLIndexedMessageToMessageCodec<IncantationsMessage> {

    private EnumMap<Side, FMLEmbeddedChannel> channels;

    public PacketHandler() {
        this.channels = NetworkRegistry.INSTANCE.newChannel("Incantations", this);

        addDiscriminator(0, IncantationsMessage.WritingDeskScrollUpdateMessage.class);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IncantationsMessage msg, ByteBuf target) throws Exception {
        msg.toBytes(target);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, IncantationsMessage msg) {
        msg.fromBytes(source);
        IMessage reply = msg.onMessage(msg, ctx, FMLCommonHandler.instance().getEffectiveSide());
        if (reply != null) ctx.writeAndFlush(reply).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    //Code below from SimpleNetworkWrapper by cpw

    /**
     * Send this message to everyone.
     * The {@link cpw.mods.fml.common.network.simpleimpl.IMessageHandler} for this message type should be on the CLIENT side.
     *
     * @param message The message to send
     */
    public void sendToAll(IMessage message) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    /**
     * Send this message to the specified player.
     * The {@link cpw.mods.fml.common.network.simpleimpl.IMessageHandler} for this message type should be on the CLIENT side.
     *
     * @param message The message to send
     * @param player The player to send it to
     */
    public void sendTo(IMessage message, EntityPlayerMP player) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    /**
     * Send this message to everyone within a certain range of a point.
     * The {@link cpw.mods.fml.common.network.simpleimpl.IMessageHandler} for this message type should be on the CLIENT side.
     *
     * @param message The message to send
     * @param point The {@link cpw.mods.fml.common.network.NetworkRegistry.TargetPoint} around which to send
     */
    public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    /**
     * Send this message to everyone within the supplied dimension.
     * The {@link cpw.mods.fml.common.network.simpleimpl.IMessageHandler} for this message type should be on the CLIENT side.
     *
     * @param message The message to send
     * @param dimensionId The dimension id to target
     */
    public void sendToDimension(IMessage message, int dimensionId) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    /**
     * Send this message to the server.
     * The {@link cpw.mods.fml.common.network.simpleimpl.IMessageHandler} for this message type should be on the SERVER side.
     *
     * @param message The message to send
     */
    public void sendToServer(IMessage message) {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}
