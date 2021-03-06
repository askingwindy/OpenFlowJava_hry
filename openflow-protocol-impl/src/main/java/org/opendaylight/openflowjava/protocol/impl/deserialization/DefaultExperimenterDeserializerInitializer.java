/*
 * Copyright (c) 2013 Pantheon Technologies s.r.o. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.openflowjava.protocol.impl.deserialization;

import org.opendaylight.openflowjava.protocol.api.extensibility.DeserializerRegistry;
import org.opendaylight.openflowjava.protocol.api.extensibility.MessageCodeKey;
import org.opendaylight.openflowjava.protocol.impl.deserialization.experimenters.ExperimenterActionDeserializer;
import org.opendaylight.openflowjava.protocol.impl.deserialization.experimenters.OF13ExperimenterInstructionDeserializer;
import org.opendaylight.openflowjava.protocol.impl.util.EncodeConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev130731.actions.grouping.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instructions.grouping.Instruction;

/**
 * @author michal.polkorab
 *
 */
public class DefaultExperimenterDeserializerInitializer {

    /**
     * Registers default experimenter deserializers
     * @param registry registry to be filled with deserializers
     */
    public static void registerDeserializers(DeserializerRegistry registry) {
        // register OF v1.0 default experimenter deserializers
        // - default action deserializer
        registry.registerDeserializer(new MessageCodeKey(EncodeConstants.OF10_VERSION_ID,
                EncodeConstants.EXPERIMENTER_VALUE, Action.class), new ExperimenterActionDeserializer());
        // register OF v1.3 default experimenter deserializers
        // - default action deserializer
        registry.registerDeserializer(new MessageCodeKey(EncodeConstants.OF13_VERSION_ID,
                EncodeConstants.EXPERIMENTER_VALUE, Action.class), new ExperimenterActionDeserializer());
        // - default instruction deserializer
        registry.registerDeserializer(new MessageCodeKey(EncodeConstants.OF13_VERSION_ID,
                EncodeConstants.EXPERIMENTER_VALUE, Instruction.class), new OF13ExperimenterInstructionDeserializer());
    }
}
