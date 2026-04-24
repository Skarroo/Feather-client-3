package com.featherhud.config;

public class HudModuleConfig {
    public boolean enabled = true;
    public int x = 0;
    public int y = 0;
    public int color = 0xFFFFFF;   // RGB only; alpha is always 0xFF when used
    public boolean showBackground = true;

    // Stored so we can reset to defaults
    public int defaultX = 0;
    public int defaultY = 0;

    /** No-arg constructor required for Gson deserialization */
    public HudModuleConfig() {}

    public HudModuleConfig(int defaultX, int defaultY) {
        this.x = defaultX;
        this.y = defaultY;
        this.defaultX = defaultX;
        this.defaultY = defaultY;
    }

    public void resetToDefault() {
        this.x = defaultX;
        this.y = defaultY;
    }
}
