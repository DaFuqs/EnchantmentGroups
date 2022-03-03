package de.dafuqs.enchantmentgroups;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class EnchantmentGroups implements ModInitializer {

    public static final String MODID = "enchantment_groups";
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String CONFIG_NAME = "EnchantmentGroups.json";
    public static final Gson GSON = new Gson();

    public static Config config;


    @Override
    public void onInitialize() {
        loadConfig();
    }

    public static void log(Level level, String text) {
        LOGGER.log(level, "[EnchantmentGroups] " + text);
    }

    public void loadConfig() {
        try {
            File file = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_NAME).toFile();
            if (!file.exists()) {
                EnchantmentGroups.log(Level.WARN, "'EnchantmentGroups.json' config file not found! Creating default config.");
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file, false);
                out.write(Config.DEFAULT_CONFIG.getBytes());
                out.flush();
                out.close();
            }

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                Type type = new TypeToken<HashMap<String, List<String>>>(){}.getType();
                config = new Config(GSON.fromJson(br, type));

                EnchantmentGroups.log(Level.INFO, "Init complete");
            } catch (Exception e) {
                log(Level.ERROR, "Error reading config file 'EnchantmentGroups.json': " + e.getMessage());
            }
        } catch (IOException e) {
            log(Level.ERROR, "Error loading config file 'EnchantmentGroups.json': " + e.getMessage());
        }
    }

}
