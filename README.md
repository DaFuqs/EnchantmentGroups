<img src="src/main/resources/assets/enchantment_groups/icon.png?raw=true" align="right" width="180px"/>

# Enchantment Groups

*Easy to set up Enchantment Exclusivity using Groups*

[>> Downloads <<](https://github.com/DaFuqs/EnchantmentGroups/releases)

## Why Enchantment Groups
In big modpacks, a lot of mods come with their own enchantments.
While mods declare Enchantments mutually exclusivity between their own Enchantments, most often all Enchantments from different mods can be combined, despite from a logical standpoint should not be used together on the same tool or armor piece.

That brings up a list of issues:
- Players do not have to make meaningful choices. There is no need to specialize, like in vanilla
- Players end up with equipment that have so many enchantments, that they are pretty much omnipotent beings

## Enchantment Groups Solution
Enchantment Groups tries to resolve that problem immersively and vanilla-like. Using Enchantment Groups:
- Players never have to resolve enchantment conflicts manually. No commands, no additional items, no screen cluttering tooltips
- Easy to set up and maintain for modpack makers

# Config
The config format is as easy as can be. Via json, you are able to create groups of enchantments.
All enchantments in that group are considered mutually exclusive to one another.

Each group is identified by a name.
This name serves no function in itself, but is there for modpack makers to give a descriptive name to make the 'why' understandable.

**Note: Existing enchantment exclusivity, like defined in vanilla or by other mods are not modified, allowing vanilla and mods to do their thing.**

## Config Example
If you were to define enchantment exclusivity like it exists in vanilla, it would look somewhat like this.
- Protection enchantments cannot be combined
- Melee enchantments cannot be combined
- Bow cannot use both Infinity and Mending
- Crossbow cannot use both Multishot and Piercing
- Tridents can use Loyalty and Channeling combined, but both cannot be combined with Riptide

```
{
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
    "bow": [
        "minecraft:infinity",
        "minecraft:mending"
    ],
    "crossbow": [
        "minecraft:multishot",
        "minecraft:piercing"
    ],
    "trident1": [
        "minecraft:loyalty",
        "minecraft:riptide"
    ],
    "trident2": [
        "minecraft:channeling",
        "minecraft:riptide"
    ]
}
```
