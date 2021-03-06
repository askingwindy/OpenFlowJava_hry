/*
 * Copyright (c) 2013 Pantheon Technologies s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.openflowjava.protocol.api.extensibility;


/**
 * More specific key for {@link SerializerRegistry}
 * @author michal.polkorab
 * @param <E> main type
 * @param <F> specific type
 */
public class EnhancedMessageTypeKey<E, F> extends MessageTypeKey<E> {

    private final Class<F> msgType2;

    /**
     * @param msgVersion
     * @param msgType
     * @param msgType2
     */
    public EnhancedMessageTypeKey(short msgVersion, Class<E> msgType, Class<F> msgType2) {
        super(msgVersion, msgType);
        this.msgType2 = msgType2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        EnhancedMessageTypeKey other = (EnhancedMessageTypeKey) obj;
        if (msgType2 == null) {
            if (other.msgType2 != null)
                return false;
        } else if (!msgType2.getName().equals(other.msgType2.getName()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " msgType2: " + msgType2.getName();
    }
}
