package com.featherhud.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("featherhud.json");

    /** Module id → config */
    public static Map<String, HudModuleConfig> configs = new HashMap<>();

    public static void load() {
        File file = CONFIG_PATH.toFile();
        if (!file.exists()) return;
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, HudModuleConfig>>() {}.getType();
            Map<String, HudModuleConfig> loaded = GSON.fromJson(reader, type);
            if (loaded != null) {
                configs = loaded;
            }
        } catch (Exception e) {
            System.err.println("[FeatherHUD] Failed to load config: " + e.getMessage());
        }
    }

    public static void save() {
        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(configs, writer);
        } catch (Exception e) {
            System.err.println("[FeatherHUD] Failed to save config: " + e.getMessage());
        }
    }

    /**
     * Returns the stored config for the given module id, creating a new entry
     * with the supplied defaults if one does not yet exist.
     */
    public static HudModuleConfig getOrCreate(String id, int defaultX, int defaultY) {
        return configs.computeIfAbsent(id, k -> new HudModuleConfig(defaultX, defaultY));
    }
}
