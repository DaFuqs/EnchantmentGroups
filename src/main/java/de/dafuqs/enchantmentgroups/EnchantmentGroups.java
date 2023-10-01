package de.dafuqs.enchantmentgroups;

import net.fabricmc.api.*;
import org.slf4j.*;

public class EnchantmentGroups implements ModInitializer {
	
	public static final String MOD_ID = "enchantment_groups";
	public static final Logger LOGGER = LoggerFactory.getLogger("EnchantmentGroups");
	
	@Override
	public void onInitialize() {
		Config.loadConfig();
	}
	
}
