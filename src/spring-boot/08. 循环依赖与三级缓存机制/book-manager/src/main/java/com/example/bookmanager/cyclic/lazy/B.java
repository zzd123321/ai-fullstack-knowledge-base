package com.example.bookmanager.cyclic.lazy;

import org.springframework.stereotype.Component;

@Component
public class B {
    private final A a;

    public B(A a) {
        this.a = a;
    }
}