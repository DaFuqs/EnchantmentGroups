package de.dafuqs.enchantmentgroups;

import me.wawwior.config.ConfigProvider;
import me.wawwior.config.io.impl.FileInfo;
import me.wawwior.config.io.impl.JsonFileAdapter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EnchantmentGroups implements ModInitializer {

    public static final String MOD_ID = "enchantment_groups";

    public static final Logger LOGGER = LoggerFactory.getLogger("EnchantmentGroups");
    public static final String CONFIG_NAME = "EnchantmentGroups";

    private static final ConfigProvider<FileInfo> provider = new ConfigProvider<>(
            new JsonFileAdapter("config")
                    .withAdapter(Identifier.class, new Identifier.Serializer()),
            true
    );

    public static Config config = new Config(provider);


    @Override
    public void onInitialize() {
        config.load();
        config.transform();
        config.save();
    }

}
