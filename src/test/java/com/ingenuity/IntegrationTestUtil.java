package com.ingenuity;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IntegrationTestUtil {

    public static MockHttpServletRequest addParameters(Object formObject, MockHttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

        Map<String, Object> propertyValues = mapper.convertValue(formObject, Map.class);

        Set<String> propertyNames = propertyValues.keySet();
        Iterator<String> nameIter = propertyNames.iterator();

        for (int index=0; index < propertyNames.size(); index++) {
            String currentKey = nameIter.next();
            Object currentValue = propertyValues.get(currentKey);

            request.setParameter(currentKey, currentValue.toString());
        }

        return request;
    }
}