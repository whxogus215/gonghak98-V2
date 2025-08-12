package com.gonghak98.v2.legacy.user.service.port;

public interface UserEncoder {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
