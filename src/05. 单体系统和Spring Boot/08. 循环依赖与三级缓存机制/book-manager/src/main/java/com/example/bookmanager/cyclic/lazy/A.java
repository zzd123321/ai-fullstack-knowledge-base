package com.example.bookmanager.cyclic.lazy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class A {
    private final B b;

    public A(@Lazy B b) {
        this.b = b;
    }
}