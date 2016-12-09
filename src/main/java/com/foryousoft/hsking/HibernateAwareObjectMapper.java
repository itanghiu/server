package com.foryousoft.hsking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Created by i-tang on 16/09/16.
 *
 * Used to support JSON serialization and deserialization of Hibernate specific datatypes
 * and properties; especially LAZY-LOADING aspects.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}
