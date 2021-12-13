package org.simpledfs.master;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;
import org.simpledfs.core.packet.DefaultPacketHandler;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.packet.PacketMessageCodec;
import org.simpledfs.master.http.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PacketDispatcher extends ByteToMessageDecoder {

    private final static Logger LOGGER = LoggerFactory.getLogger(PacketDispatcher.class);

    private MasterContext context;

    public PacketDispatcher(MasterContext context) {
        this.context = context;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }
        int readerIndex = in.readerIndex();
        final int magic1 = in.getByte(readerIndex);
        final int magic2 = in.getByte(readerIndex + 1);
        if (isPacket(magic1)) {
            dispatchToPacket(ctx);
        } else if (isHttp(magic1, magic2)) {
            dispatchToHttpPacket(ctx);
        } else {
            in.clear();
            ctx.close();
        }
        if (magic1 == Packet.MAGIC_CODE){
            dispatchToPacket(ctx);
        }else{
            in.clear();
            ctx.close();
        }
    }

    private void dispatchToPacket(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new PacketMessageCodec());
        pipeline.addLast(new DefaultMasterPacketHandler(context));
        // 将所有所需的ChannelHandler添加到pipeline之后，一定要将自身移除掉
        // 否则该Channel之后的请求仍会重新执行协议的分发，而这是要避免的
        pipeline.remove(this);
        // 将channelActive事件传递到PacketHandler
        ctx.fireChannelActive();
    }

    private void dispatchToHeartPacket(ChannelHandlerContext ctx){

    }

    private void dispatchToHttpPacket(ChannelHandlerContext ctx){
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        // aggregate HttpRequest/HttpContent/LastHttpContent to FullHttpRequest
        pipeline.addLast(new HttpObjectAggregator(8096));
        pipeline.addLast(new HttpHandler());
        pipeline.remove(this);
        // 将channelActive事件传递到HttpHandler
        ctx.fireChannelActive();
    }

    private static boolean isHttp(int magic1, int magic2) {
        return
                magic1 == 'G' && magic2 == 'E' || // GET
                        magic1 == 'P' && magic2 == 'O' || // POST
                        magic1 == 'P' && magic2 == 'U' || // PUT
                        magic1 == 'H' && magic2 == 'E' || // HEAD
                        magic1 == 'O' && magic2 == 'P' || // OPTIONS
                        magic1 == 'P' && magic2 == 'A' || // PATCH
                        magic1 == 'D' && magic2 == 'E' || // DELETE
                        magic1 == 'T' && magic2 == 'R' || // TRACE
                        magic1 == 'C' && magic2 == 'O';   // CONNECT
    }

    private boolean isPacket(int magic1) {
        return magic1 == Packet.MAGIC_CODE;
    }

    private boolean isHeartPacket(int magic1) {
        return magic1 == Packet.MAGIC_CODE;
    }
}
