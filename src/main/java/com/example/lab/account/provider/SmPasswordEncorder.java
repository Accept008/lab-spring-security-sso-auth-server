package com.example.lab.account.provider;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SmPasswordEncorder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
