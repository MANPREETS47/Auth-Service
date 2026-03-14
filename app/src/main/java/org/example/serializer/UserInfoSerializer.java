package org.example.serializer;

import org.apache.kafka.common.serialization.Serializer;
import org.example.model.UserinfoDto;

import com.fasterxml.jackson.databind.ObjectMapper;


public class UserInfoSerializer implements Serializer<UserinfoDto> {

    @Override
    public void configure(java.util.Map<String, ?> configs, boolean isKey) {
        // No configuration needed for this serializer
    }
    

    @Override
    public byte[] serialize(String topic, UserinfoDto data) {
        byte[] retval = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retval = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }

    @Override
    public void close() {
        // No resources to close for this serializer
    }

    
}
