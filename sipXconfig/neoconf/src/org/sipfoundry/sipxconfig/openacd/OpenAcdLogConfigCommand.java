/**
 *
 *
 * Copyright (c) 2010 / 2011 eZuce, Inc. All rights reserved.
 * Contributed to SIPfoundry under a Contributor Agreement
 *
 * This software is free software; you can redistribute it and/or modify it under
 * the terms of the Affero General Public License (AGPL) as published by the
 * Free Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 */
package org.sipfoundry.sipxconfig.openacd;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.sipfoundry.commons.mongo.MongoConstants;
import org.sipfoundry.sipxconfig.common.BeanWithId;
import org.sipfoundry.sipxconfig.common.Replicable;
import org.sipfoundry.sipxconfig.commserver.imdb.AliasMapping;
import org.sipfoundry.sipxconfig.commserver.imdb.DataSet;

public class OpenAcdLogConfigCommand extends BeanWithId implements Replicable {

    private final String m_logLevel;
    private final String m_logFile;

    public OpenAcdLogConfigCommand(String level, String logFile) {
        m_logLevel = level;
        m_logFile = logFile;
    }

    public String getLogLevel() {
        return m_logLevel;
    }

    public String getLogFile() {
        return m_logFile;
    }

    @Override
    public Map<String, Object> getMongoProperties(String domain) {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(MongoConstants.LOG_LEVEL, getLogLevel());
        props.put(MongoConstants.LOG_FILE, getLogFile());
        return props;
    }

    @Override
    public Set<DataSet> getDataSets() {
        return Collections.singleton(DataSet.OPENACD);
    }

    @Override
    public String getIdentity(String domainName) {
        return null;
    }

    @Override
    public Collection<AliasMapping> getAliasMappings(String domainName) {
        return null;
    }

    @Override
    public boolean isValidUser() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {
    }

}
