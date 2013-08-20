/* Copyright (C)2013 Pantheon Technologies, s.r.o. All rights reserved. */
package org.openflow.clients;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Simple client
*
* @author michal.polkorab
*/
public class SimpleClient {

    private static final Logger logger = LoggerFactory.getLogger(SimpleClient.class);
    private static String filename = null;

    /** 
     * Starts the client with passed parameters or tries default settings
     * @param args 
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String host;
        int port;
        if (args.length != 3) {
            logger.error("Usage: " + SimpleClient.class.getSimpleName()
                    + " <host> <port> <filename>");
            logger.error("Trying to use default setting.");
            InetAddress ia = InetAddress.getLocalHost();
            InetAddress[] all = InetAddress.getAllByName(ia.getHostName());
            host = all[0].getHostAddress();
            port = 6633;
            filename = null;
        } else {
            host = args[0];
            port = Integer.parseInt(args[1]);
            filename = args[2];
        }

        EventLoopGroup group = new NioEventLoopGroup();
        ByteBuf buffy = null;
        Channel ch = null;
        Bootstrap b = null;
        byte[] bytearray = new byte[64];

        try {
            logger.info("SimpleClient - Creating TCP connection");
            b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleClientHandler());

            ch = b.connect(host, port).sync().channel();

            buffy = ch.alloc().buffer(128);

            if (filename != null) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(filename);
                    logger.debug("Size to read (in bytes) : " + fis.available());
                    int lenght;
                    while ((lenght = fis.read(bytearray)) != -1) {
                        buffy.writeBytes(bytearray, 0, lenght);
                    }
                    ch.writeAndFlush(buffy);
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                if ("bye".equals(line.toLowerCase())) {
                    logger.info("bye");
                    in.close();
                    break;
                }
                buffy = ch.alloc().buffer(128);
                buffy.writeBytes(line.getBytes(Charset.defaultCharset()));
                ch.writeAndFlush(buffy);
            }

            ch.closeFuture().sync();
        } finally {
            logger.info("Exiting");
            group.shutdownGracefully();
        }
    }
}