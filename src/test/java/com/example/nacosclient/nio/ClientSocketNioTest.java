package com.example.nacosclient.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author hf
 * date   2022/2/17 15:17
 * description
 */
public class ClientSocketNioTest {


    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt("11123");
        SocketChannel clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);
        if (!clientChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), port))) {
            while (!clientChannel.finishConnect()) {
                System.out.println(".");
            }
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Selector selector = Selector.open();
        int ops = clientChannel.validOps();
        clientChannel.register(selector, ops);

        System.out.println("Connected to server...");

        while (selector.select() > 0) {

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isValid() && key.isConnectable()) {
                    System.out.println("通道连接事件");
                }

                if (key.isValid() && key.isReadable()) {
                    System.out.println("通道可读事件");
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.read(buffer);
                    buffer.flip();
                    String s = StandardCharsets.UTF_8.decode(buffer).toString();
                    System.out.println(" 接受到的字串 " + s);
                }

                if (key.isValid() && key.isWritable()) {
                    System.out.println("通道可写事件");
                }
            }

        }

    }
}
