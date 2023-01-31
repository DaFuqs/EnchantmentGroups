package de.dafuqs.enchantmentgroups;

import me.wawwior.config.ConfigProvider;
import me.wawwior.config.io.impl.FileInfo;
import me.wawwior.config.io.impl.JsonFileAdapter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class EnchantmentGroups implements ModInitializer {

    public static final String MOD_ID = "enchantment_groups";

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
        Runtime.getRuntime().addShutdownHook(new Thread(config::save));
    }

}
