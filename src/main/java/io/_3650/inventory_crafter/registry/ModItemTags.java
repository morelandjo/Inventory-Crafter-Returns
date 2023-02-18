package io._3650.inventory_crafter.registry;

import io._3650.inventory_crafter.InventoryCrafter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
	
	public static final TagKey<Item> CRAFTING_TABLE = ItemTags.create(new ResourceLocation(InventoryCrafter.MOD_ID, "crafting_tables"));
	
}