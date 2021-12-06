package org.simpledfs.work;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDispatcher extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }
        int readerIndex = in.readerIndex();
        byte magic = in.getByte(readerIndex);

        in.clear();
        ctx.close();
    }

    private void dispatchToPacket(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
//        BaseConfig baseConfig = ConfigFactory.getConfig(BaseConfig.class);
//        pipeline.addLast(new IdleStateChecker(baseConfig.readerIdleTime()));
//        pipeline.addLast(new PacketCodec());
//        pipeline.addLast(PacketHandler.getInstance(channelListener));
        // 将所有所需的ChannelHandler添加到pipeline之后，一定要将自身移除掉
        // 否则该Channel之后的请求仍会重新执行协议的分发，而这是要避免的
        pipeline.remove(this);
        // 将channelActive事件传递到PacketHandler
        ctx.fireChannelActive();
    }
}
