package com.example.api;

import com.intuit.karate.junit5.Karate;

/**
 * Karate testlerini çalıştırmak için JUnit 5 runner sınıfı.
 */
public class KarateRunner {

    @Karate.Test
    Karate testUsers() {
        return Karate.run("users").relativeTo(getClass());
    }
}
