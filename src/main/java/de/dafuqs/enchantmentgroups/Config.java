package de.dafuqs.enchantmentgroups;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {

    public static final String DEFAULT_CONFIG = """
{
    /* This default config serves as an example on how to set up groups
       Since these enchantments are mutually exclusive by default already
       you will probably not notice any changes when enchanting.
       Create your own enchantment groups here or expand on these defaults, if you wish
    */
    "protection": [
        "minecraft:protection",
        "minecraft:blast_protection",
        "minecraft:fire_protection",
        "minecraft:projectile_protection"
    ],
    "melee_damage": [
        "minecraft:bane_of_arthropods",
        "minecraft:smite",
        "minecraft:sharpness"
    ],
    "mining": [
        "minecraft:fortune",
        "minecraft:silk_touch"
    ],
    "bow": [
        "minecraft:infinity",
        "minecraft:mending"
    ],
    "trident1": [
        "minecraft:loyalty",
        "minecraft:riptide"
    ],
    "trident2": [
        "minecraft:channeling",
        "minecraft:riptide"
    ],
    "crossbow": [
        "minecraft:multishot",
        "minecraft:piercing"
    ]
}
""";

    private final HashMap<Identifier, List<Identifier>> mutualExclusivities;

    public Config(HashMap<String, List<String>> input) {
        HashMap<Identifier, List<Identifier>> mutualExclusivities = new HashMap<>();

        for(List<String> entries : input.values()) {
            // Try parsing all entries in each list as identifiers
            List<Identifier> entryIdentifiers = new ArrayList<>();
            for(String entry :entries) {
                Identifier identifier = Identifier.tryParse(entry);
                if(identifier == null) {
                    EnchantmentGroups.log(Level.WARN, "Config entry '" + entry + "' could not be parsed as a valid identifier. Will be ignored.");
                } else {
                    entryIdentifiers.add(identifier);
                }
            }

            // creating a map for each identifier for fast lookup
            for(int i = 0; i < entryIdentifiers.size(); i++) {
                List<Identifier> currentExclusives = new ArrayList<>();
                for (int j = 0; j < entryIdentifiers.size(); j++) {
                    if(i != j) { // an enchantment is always incompatible with itself anyways
                        currentExclusives.add(entryIdentifiers.get(j));
                    }
                }
                mutualExclusivities.put(entryIdentifiers.get(i), currentExclusives);
            }
        }

        this.mutualExclusivities = mutualExclusivities;
    }

    public boolean canCombine(Enchantment enchantment1, Enchantment enchantment2) {
        // we only have to check for a single enchantment here
        // since both exclusivity lists would return either true or false everytime
        Identifier id1 = Registries.ENCHANTMENT.getId(enchantment1);
        if(mutualExclusivities.containsKey(id1)) {
            List<Identifier> id1Exclusivities = mutualExclusivities.get(id1);
            Identifier id2 = Registries.ENCHANTMENT.getId(enchantment2);
            return !id1Exclusivities.contains(id2);
        }
        return true;
    }

}
