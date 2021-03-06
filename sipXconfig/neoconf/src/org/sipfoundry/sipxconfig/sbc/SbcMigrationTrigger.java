/*
 *
 *
 * Copyright (C) 2008 Pingtel Corp., certain elements licensed under a Contributor Agreement.
 * Contributors retain copyright to elements licensed under a Contributor Agreement.
 * Licensed to the User under the LGPL license.
 *
 * $
 */
package org.sipfoundry.sipxconfig.sbc;

import org.sipfoundry.sipxconfig.admin.InitTaskListener;

public class SbcMigrationTrigger extends InitTaskListener {
    private SbcMigrationContext m_sbcMigrationContext;

    public void setSbcMigrationContext(SbcMigrationContext sbcMigrationContext) {
        m_sbcMigrationContext = sbcMigrationContext;
    }

    @Override
    public void onInitTask(String task) {
        if ("sbc_address_migrate_sbc_device".equals(task)) {
            m_sbcMigrationContext.migrateSbc();
        }
    }
}
