package com.suyh.demo0301.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

// 自定义服务端处理器
// 需求：用户提交一个请求后，在浏览器上就会看到hello netty world
public class SomeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当Channel中有来自于客户端的数据时就会触发该方法的执行
     *
     * @param ctx 上下文对象
     * @param msg 就是来自于客户端的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("-------------- " + ctx.channel());


        System.out.println("msg = " + msg.getClass());
        System.out.println("客户端地址 = " + ctx.channel().remoteAddress());

        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("请求方式：" + request.method().name());
            System.out.println("请求URI：" + request.uri());

            if ("/favicon.ico".equals(request.uri())) {
                System.out.println("不处理/favicon.ico请求");
                return;
            }

            // 构造response的响应体
            ByteBuf body = Unpooled.copiedBuffer("hello netty world", CharsetUtil.UTF_8);
            // 生成响应对象
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
            // 获取到response的头部后进行初始化
            HttpHeaders headers = response.headers();
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            headers.set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());

            // 将响应对象写入到Channel
            // ctx.write(response);
            // ctx.flush();
            // ctx.writeAndFlush(response);
            // ctx.channel().close();
            ctx.writeAndFlush(response)
                    // 添加channel关闭监听器，一般情况我们不会在这里关闭它。
                    .addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 当Channel中的数据在处理过程中出现异常时会触发该方法的执行
     *
     * @param ctx   上下文
     * @param cause 发生的异常对象
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 关闭Channel
        // 这里执行关闭之后，就会触发closeFuture 的执行，从而导致整个netty 服务器的关闭。即main 方法中的finally 块的执行
        // 所以如果这里不调用close ，则即使服务异常不对外提供服务，整个系统也不会正常结束。
        ctx.close();
    }
}