/*
 * Copyright (c) 2013 Pantheon Technologies s.r.o. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.openflowjava.protocol.impl.serialization.action;

import org.opendaylight.openflowjava.protocol.impl.util.ActionConstants;


/**
 * @author michal.polkorab
 *
 */
public class OF13PushVlanActionSerializer extends AbstractEthertypeActionSerializer {

    @Override
    protected int getType() {
        return ActionConstants.PUSH_VLAN_CODE;
    }

}
