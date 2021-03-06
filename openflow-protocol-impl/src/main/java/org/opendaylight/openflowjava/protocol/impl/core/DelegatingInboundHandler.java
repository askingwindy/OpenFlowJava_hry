/*
 * Copyright (c) 2013 Pantheon Technologies s.r.o. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.openflowjava.protocol.impl.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.opendaylight.openflowjava.protocol.impl.connection.ConnectionAdapterImpl;
import org.opendaylight.openflowjava.protocol.impl.connection.MessageConsumer;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.system.rev130927.DisconnectEventBuilder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Holds reference to {@link ConnectionAdapterImpl} and passes messages for further processing.
 * Also informs on switch disconnection.
 * @author michal.polkorab
 */
public class DelegatingInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelegatingInboundHandler.class);

    private final MessageConsumer consumer;
    private boolean inactiveMessageSent = false;

    /**
     * Constructs class + creates and sets MessageConsumer
     * @param connectionAdapter reference for adapter communicating with upper layers outside library
     */
    public DelegatingInboundHandler(final MessageConsumer connectionAdapter) {
        LOGGER.trace("Creating DelegatingInboundHandler");
        consumer = Preconditions.checkNotNull(connectionAdapter);
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        consumer.consume((DataObject) msg);
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        LOGGER.debug("Channel inactive");
        if (!inactiveMessageSent) {
            DisconnectEventBuilder builder = new DisconnectEventBuilder();
            builder.setInfo("Channel inactive");
            consumer.consume(builder.build());
            inactiveMessageSent = true;
        }
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) {
        LOGGER.debug("Channel unregistered");
        if (!inactiveMessageSent) {
            DisconnectEventBuilder builder = new DisconnectEventBuilder();
            builder.setInfo("Channel unregistered");
            consumer.consume(builder.build());
            inactiveMessageSent = true;
        }
    }

}
