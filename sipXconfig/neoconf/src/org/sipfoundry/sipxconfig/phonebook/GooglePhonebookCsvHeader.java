/*
 *
 *
 * Copyright (C) 2009 Pingtel Corp., certain elements licensed under a Contributor Agreement.
 * Contributors retain copyright to elements licensed under a Contributor Agreement.
 * Licensed to the User under the LGPL license.
 *
 *
 */
package org.sipfoundry.sipxconfig.phonebook;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class GooglePhonebookCsvHeader extends PhonebookCsvHeader {
    private static final String FIRST_NAME = "Given Name";
    private static final String LAST_NAME = "Family Name";
    private static final String PHONE = "Phone 1 - Value";
    private static final String JOB_TITLE = "Organization 1 - Title";
    private static final String JOB_DEPT = "Organization 1 - Department";
    private static final String COMPANY_NAME = "Organization 1 - Name";
    private static final String TYPE_HOME = "Home";
    private static final String TYPE_OFFICE = "Work";
    private static final String TYPE_OTHER = "Other";
    private static final String LOCATION = "Location";
    private static final String RELATION_NAME = "Relation";
    private static final String RELATION_TYPE_ASSISTANT = "Assistant";
    private static final String PHONE_NAME = "Phone";
    private static final String PHONE_TYPE_MOBILE = "Mobile";
    private static final String PHONE_TYPE_WORK_FAX = "Work Fax";
    private static final String IM_NAME = "IM";
    private static final String ADDRESS = "Address";
    private static final String ADDRESS_VALUE_CITY = "City";
    private static final String ADDRESS_VALUE_COUNTRY = "Country";
    private static final String ADDRESS_VALUE_STATE = "Region";
    private static final String ADDRESS_VALUE_STREET = "Street";
    private static final String ADDRESS_VALUE_ZIP = "Postal Code";
    private static final String EMAIL_ADDRESS = "E-mail";

    public GooglePhonebookCsvHeader(Map<String, Integer> header) {
        super(header);
    }

    private String getGoogleValueForType(String[] row, String property, String type, String valueSuffix) {
        for (int index = 1;; index++) {
            String key = String.format("%s %d - Type", property, index);
            Integer rowIndex = getIndexForSymbol(key);
            if (rowIndex == null) {
                break;
            }
            String currentType = getValueForSymbol(row, key);
            if (type.equals(currentType) || currentType.equals("* " + type)) {
                String valueKey = String.format("%s %d - %s", property, index, valueSuffix);
                return getValueForSymbol(row, valueKey);
            }
        }
        return null;
    }

    private String getGoogleValueForType(String[] row, String property, String type) {
        return getGoogleValueForType(row, property, type, "Value");
    }

    public String getFirstName(String[] row) {
        return getValueForSymbol(row, FIRST_NAME);
    }

    public String getLastName(String[] row) {
        return getValueForSymbol(row, LAST_NAME);
    }

    public String getNumber(String[] row) {
        return StringUtils.defaultString(getValueForSymbol(row, PHONE));
    }

    public AddressBookEntry getAddressBookEntry(String[] row) {
        if (row.length < 25) {
            return null;
        }

        AddressBookEntry abe = new AddressBookEntry();
        abe.setJobTitle(getValueForSymbol(row, JOB_TITLE));
        abe.setJobDept(getValueForSymbol(row, JOB_DEPT));
        abe.setCompanyName(getValueForSymbol(row, COMPANY_NAME));
        abe.setAssistantName(getGoogleValueForType(row, RELATION_NAME, RELATION_TYPE_ASSISTANT));
        abe.setCellPhoneNumber(getGoogleValueForType(row, PHONE_NAME, PHONE_TYPE_MOBILE));
        abe.setHomePhoneNumber(getGoogleValueForType(row, PHONE_NAME, TYPE_HOME));
        abe.setFaxNumber(getGoogleValueForType(row, PHONE_NAME, PHONE_TYPE_WORK_FAX));
        abe.setAlternateImId(getGoogleValueForType(row, IM_NAME, TYPE_OTHER));
        abe.setLocation(getValueForSymbol(row, LOCATION));
        abe.setEmailAddress(getGoogleValueForType(row, EMAIL_ADDRESS, TYPE_OFFICE));
        if (getGoogleValueForType(row, EMAIL_ADDRESS, TYPE_HOME) != null) {
            abe.setAlternateEmailAddress(getGoogleValueForType(row, EMAIL_ADDRESS, TYPE_HOME));
        } else {
            abe.setAlternateEmailAddress(getGoogleValueForType(row, EMAIL_ADDRESS, TYPE_OTHER));
        }
        Address homeAddress = getAddress(row, TYPE_HOME);
        abe.setHomeAddress(homeAddress);

        Address officeAddress = getAddress(row, TYPE_OFFICE);
        abe.setOfficeAddress(officeAddress);

        return abe;
    }

    private Address getAddress(String[] row, String type) {
        Address address = new Address();
        address.setCity(getGoogleValueForType(row, ADDRESS, type, ADDRESS_VALUE_CITY));
        address.setCountry(getGoogleValueForType(row, ADDRESS, type, ADDRESS_VALUE_COUNTRY));
        address.setState(getGoogleValueForType(row, ADDRESS, type, ADDRESS_VALUE_STATE));
        address.setStreet(getGoogleValueForType(row, ADDRESS, type, ADDRESS_VALUE_STREET));
        address.setZip(getGoogleValueForType(row, ADDRESS, type, ADDRESS_VALUE_ZIP));
        return address;
    }
}
