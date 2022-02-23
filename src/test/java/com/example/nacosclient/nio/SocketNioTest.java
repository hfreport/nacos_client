package com.example.nacosclient.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author hf
 * date   2022/2/17 10:07
 * description
 */
public class SocketNioTest {


    public static void main(String[] args) throws IOException {

        SocketNioTest socketNioTest = new SocketNioTest();

        new Thread(socketNioTest::terminate).start();

        socketNioTest.server();

    }

    private void server() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(11123));

        Selector selector = Selector.open();

        int ops = channel.validOps();

        SelectionKey register = channel.register(selector, ops);
        register.attach(new String("attach"));
        while (true) {
            // 阻塞两秒
            if (selector.select() == 0) {
                System.out.println(" socket select continue ");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                // 通道事件
                if (key.isValid() && key.isAcceptable()) {
                    System.out.println("通道接受事件");
                    key.attachment();
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = serverSocketChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(key.selector(), SelectionKey.OP_READ);
                    clientChannel.write(ByteBuffer.wrap("Welcome".getBytes(UTF_8)));
                }

                if (key.isValid() && key.isConnectable()) {
                    System.out.println("通道连接事件");
                }

                if (key.isValid() && key.isReadable()) {
                    System.out.println("通道可读事件");
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    try {
                        ByteBuffer readBuffer = ByteBuffer.allocate(10);
                        // 网络中断时会抛出异常
                        int readBytes = clientChannel.read(readBuffer);
                        if (readBytes == -1) {
                            System.out.println("closed.......");
                            clientChannel.close();
                            continue;
                        } else if (readBytes > 0) {
                            String s = new String(readBuffer.array(), UTF_8);
                            System.out.println("Client said: " + s.trim());
                            // attachment is content used to write
                            key.interestOps(SelectionKey.OP_WRITE);
                            key.attach("Welcome");
                        }
                    } catch (IOException e) {
                        try {
                            System.out.println(e);
                            System.out.println(" 通道 closed ");
                            clientChannel.close();
                        } catch (Exception e1) {
                            System.out.println(e1);
                        }
                    }
                }

                if (key.isValid() && key.isWritable()) {
                    System.out.println("通道可写事件");
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    // get content from attachment
                    String content = (String) key.attachment();
                    // write content to socket channel
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        clientChannel.write(byteBuffer);
                    }
//                    clientChannel.write(StandardCharsets.UTF_8.encode(content));
                    key.interestOps(SelectionKey.OP_READ);
                }

            }
        }
    }

    private ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

    private void terminate() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String msg;
            while ((msg = br.readLine()) != null) {
                System.out.println("监听输入");
                synchronized (byteBuffer) {
                    byteBuffer.put((msg + "\r\n").getBytes(UTF_8));
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
