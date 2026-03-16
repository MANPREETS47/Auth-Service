package org.example.model;

import org.example.entities.userinfo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.micrometer.common.lang.NonNull;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserinfoDto extends userinfo {
    

    @NonNull
    private String userName;
    
    @NonNull
    private String lastName;

    @NonNull
    private Long phoneNumber;

    @NonNull
    private String email;
}
