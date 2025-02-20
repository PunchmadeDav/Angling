package com.eightsidedsquare.angling.core;

import com.eightsidedsquare.angling.common.block.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.eightsidedsquare.angling.core.AnglingMod.MOD_ID;

public class AnglingBlocks {
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Block ROE = create("roe", new RoeBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).noCollision().nonOpaque().breakInstantly().sounds(BlockSoundGroup.FROGSPAWN)), null);
    public static final Block SEA_SLUG_EGGS = create("sea_slug_eggs", new SeaSlugEggsBlock(AbstractBlock.Settings.copy(ROE).dynamicBounds()), null);
    public static final Block DUCKWEED = create("duckweed", new WaterFloatingPlant(AbstractBlock.Settings.create().mapColor(MapColor.LIME).breakInstantly().nonOpaque().noCollision().sounds(BlockSoundGroup.WET_GRASS)), null);
    public static final Block ALGAE = create("algae", new AlgaeBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).sounds(BlockSoundGroup.FROGSPAWN).noCollision().nonOpaque().strength(0.1f).ticksRandomly()), ItemGroups.NATURAL);
    public static final Block WORMY_DIRT = create("wormy_dirt", new WormyDirtBlock(AbstractBlock.Settings.copy(Blocks.DIRT)), ItemGroups.BUILDING_BLOCKS);
    public static final Block WORMY_MUD = create("wormy_mud", new WormyMudBlock(AbstractBlock.Settings.copy(Blocks.MUD)), ItemGroups.BUILDING_BLOCKS);
    public static final Block OYSTERS = create("oysters", new OystersBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(0.5f).nonOpaque().sounds(AnglingSounds.SHELL_SOUND_GROUP)), ItemGroups.NATURAL);
    public static final Block STARFISH = create("starfish", new StarfishBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).strength(0.1f).nonOpaque().noCollision().sounds(AnglingSounds.SHELL_SOUND_GROUP).ticksRandomly(), false), ItemGroups.NATURAL);
    public static final Block DEAD_STARFISH = create("dead_starfish", new StarfishBlock(AbstractBlock.Settings.copy(STARFISH), true), ItemGroups.NATURAL);
    public static final Block CLAM = create("clam", new ClamBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).sounds(AnglingSounds.SHELL_SOUND_GROUP).strength(0.05f).nonOpaque()), ItemGroups.NATURAL);
    public static final Block ANEMONE = create("anemone", new AnemoneBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).strength(0.1f).nonOpaque().noCollision().sounds(BlockSoundGroup.SLIME).ticksRandomly()), ItemGroups.NATURAL);
    public static final Block URCHIN = create("urchin", new UrchinBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_BLUE).strength(0.1f).nonOpaque().noCollision().sounds(AnglingSounds.SHELL_SOUND_GROUP)), null);
    public static final Block SARGASSUM = create("sargassum", new WaterFloatingPlant(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).breakInstantly().nonOpaque().noCollision().sounds(BlockSoundGroup.WET_GRASS)), null);
    public static final Block PAPYRUS = create("papyrus", new PapyrusBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0).breakInstantly().nonOpaque().noCollision().sounds(BlockSoundGroup.AZALEA).ticksRandomly().dynamicBounds()), ItemGroups.NATURAL);

    private static <T extends Block> T create(String name, T block, RegistryKey<ItemGroup> itemGroup) {
        Identifier id = new Identifier(MOD_ID, name);
        BLOCKS.put(block, id);
        if (itemGroup != null) {
            BlockItem item = new BlockItem(block, new Item.Settings());
            ITEMS.put(item, id);
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
        }
        return block;
    }


    public static void init() {
        BLOCKS.forEach((block, id) -> Registry.register(Registries.BLOCK, id, block));
        ITEMS.forEach((item, id) -> Registry.register(Registries.ITEM, id, item));
    }
}
