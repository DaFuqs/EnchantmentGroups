package de.dafuqs.enchantmentgroups;

import com.google.gson.*;
import net.fabricmc.loader.api.*;
import net.minecraft.enchantment.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class Config {
	
	private static final List<Identifier> treasures = new ArrayList<>(List.of());
	private static final HashMap<Identifier, List<Identifier>> exclusivityGroups = new HashMap<>();
	
	public static void loadConfig() {
		try {
			File file = FabricLoader.getInstance().getConfigDir().resolve("enchantment_groups.json").toFile();
			if (!file.exists()) {
				EnchantmentGroups.LOGGER.warn("Config file not found! Generating default example file.");
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file, false);
				out.write(DEFAULT_CONFIG.getBytes());
				out.flush();
				out.close();
				return;
			}
			
			JsonObject json = JsonHelper.deserialize(new FileReader(file), true);
			loadEntries(json);
			
		} catch (Exception e) {
			EnchantmentGroups.LOGGER.error("Error loading config: " + e.getMessage());
		}
	}
	
	private static void loadEntries(@NotNull JsonObject json) {
		treasures.clear();
		exclusivityGroups.clear();
		
		JsonArray treasureArray = json.getAsJsonArray("treasures");
		for (JsonElement element : treasureArray) {
			if (element instanceof JsonPrimitive primitive) {
				Identifier id = Identifier.tryParse(primitive.getAsString());
				if (id != null) {
					treasures.add(id);
				}
			}
		}
		
		Map<String, JsonElement> groups = json.getAsJsonObject("groups").asMap();
		for (Map.Entry<String, JsonElement> entry : groups.entrySet()) {
			List<Identifier> ids = new ArrayList<>();
			if (entry.getValue() instanceof JsonArray array) {
				for (JsonElement element : array) {
					Identifier id = Identifier.tryParse(element.getAsString());
					if (id != null) {
						ids.add(id);
					}
				}
			}
			
			for (Identifier outerId : ids) {
				for (Identifier innerId : ids) {
					if (outerId.equals(innerId)) {
						continue;
					}
					
					if (exclusivityGroups.containsKey(outerId)) {
						exclusivityGroups.get(outerId).add(innerId);
					} else {
						List<Identifier> newList = new ArrayList<>();
						newList.add(innerId);
						exclusivityGroups.put(outerId, newList);
					}
				}
			}
		}
	}
	
	public static boolean canCombine(Enchantment enchantment1, Enchantment enchantment2) {
		Identifier id1 = Registries.ENCHANTMENT.getId(enchantment1);
		if (exclusivityGroups.containsKey(id1)) {
			List<Identifier> id1Exclusives = exclusivityGroups.get(id1);
			Identifier id2 = Registries.ENCHANTMENT.getId(enchantment2);
			return !id1Exclusives.contains(id2);
			
		}
		return true;
	}
	
	public static boolean isTreasure(Enchantment enchantment) {
		return treasures.contains(Registries.ENCHANTMENT.getId(enchantment));
	}
	
	private static final String DEFAULT_CONFIG = """
{
  "groups": {
	"trident1": [
	  "minecraft:loyalty",
	  "minecraft:riptide"
	],
	"melee_damage": [
	  "minecraft:bane_of_arthropods",
	  "minecraft:smite",
	  "minecraft:sharpness"
	],
	"crossbow": [
	  "minecraft:multishot",
	  "minecraft:piercing"
	],
	"protection": [
	  "minecraft:protection",
	  "minecraft:blast_protection",
	  "minecraft:fire_protection",
	  "minecraft:projectile_protection"
	],
	"bow": [
	  "minecraft:mending",
	  "minecraft:infinity"
	],
	"mining": [
	  "minecraft:fortune",
	  "minecraft:silk_touch"
	],
	"trident2": [
	  "minecraft:channeling",
	  "minecraft:riptide"
	]
  },
  "treasures": [
	"minecraft:frost_walker"
  ]
}
""";
	
}
