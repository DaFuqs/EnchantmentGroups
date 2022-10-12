package de.dafuqs.enchantmentgroups;

import me.wawwior.config.ConfigProvider;
import me.wawwior.config.Configurable;
import me.wawwior.config.IConfig;
import me.wawwior.config.io.impl.FileInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config extends Configurable<Config.EnchantmentConfig, FileInfo> {

    public static class EnchantmentConfig implements IConfig {
        HashMap<String, List<Identifier>> groups = new HashMap<>();
        List<Identifier> treasures = new ArrayList<>();

    }

    HashMap<Identifier, List<Identifier>> exclusivityGroups = new HashMap<>();

    public Config(ConfigProvider<FileInfo> provider) {
        super(EnchantmentConfig.class, FileInfo.of("", EnchantmentGroups.MOD_ID), provider);
    }

    public void transform() {
        config.groups.values().forEach(s -> s.forEach(i -> {
            if(Registry.ENCHANTMENT.containsId(i)) {
                exclusivityGroups.put(i, s);
            }
        }));
    }

    public boolean canCombine(Enchantment enchantment1, Enchantment enchantment2) {
        Identifier id1 = Registry.ENCHANTMENT.getId(enchantment1);
        if (exclusivityGroups.containsKey(id1)) {
            List<Identifier> id1Exclusives = exclusivityGroups.get(id1);
            Identifier id2 = Registry.ENCHANTMENT.getId(enchantment2);
            return !id1Exclusives.contains(id2);

        }
        return true;
    }

    public boolean isTreasure(Enchantment enchantment) {
        return config.treasures.contains(Registry.ENCHANTMENT.getId(enchantment));
    }

}
