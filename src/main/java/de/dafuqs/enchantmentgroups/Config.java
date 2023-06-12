package de.dafuqs.enchantmentgroups;

import me.wawwior.config.ConfigProvider;
import me.wawwior.config.Configurable;
import me.wawwior.config.IConfig;
import me.wawwior.config.io.impl.FileInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config extends Configurable<Config.EnchantmentConfig, FileInfo> {

    public static class EnchantmentConfig implements IConfig {
        Map<String, List<Identifier>> groups = new HashMap<>(Map.of(
                "protection", List.of(
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "protection"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "blast_protection"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "fire_protection"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "projectile_protection")
                ),
                "melee_damage", List.of(
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "bane_of_arthropods"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "smite"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "sharpness")
                ),
                "mining", List.of(
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "fortune"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "silk_touch")
                ),
                "bow", List.of(
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "mending"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "infinity")
                ),
                "trident1", List.of(
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "loyalty"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "riptide")
                ),
                "trident2", List.of(
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "channeling"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "riptide")
                ),
                "crossbow", List.of(
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "multishot"),
                        new Identifier(Identifier.DEFAULT_NAMESPACE, "piercing")
                )
        ));
        List<Identifier> treasures = new ArrayList<>(List.of());

    }

    HashMap<Identifier, List<Identifier>> exclusivityGroups = new HashMap<>();

    public Config(ConfigProvider<FileInfo> provider) {
        super(EnchantmentConfig.class, FileInfo.of("", EnchantmentGroups.MOD_ID), provider);
    }

    public void transform() {
        config.groups.values().forEach(s -> s.forEach(i -> {
            if(Registries.ENCHANTMENT.containsId(i)) {
                exclusivityGroups.put(i, s);
            }
        }));
    }

    public boolean canCombine(Enchantment enchantment1, Enchantment enchantment2) {
        Identifier id1 = Registries.ENCHANTMENT.getId(enchantment1);
        if (exclusivityGroups.containsKey(id1)) {
            List<Identifier> id1Exclusives = exclusivityGroups.get(id1);
            Identifier id2 = Registries.ENCHANTMENT.getId(enchantment2);
            return !id1Exclusives.contains(id2);

        }
        return true;
    }

    public boolean isTreasure(Enchantment enchantment) {
        return config.treasures.contains(Registries.ENCHANTMENT.getId(enchantment));
    }

}
