package de.randomerror.cocktails.backend;

import org.aeonbits.owner.Config;

public interface AppConfig extends Config {
    String password();

    String adminPassword();
}
