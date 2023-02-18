package io._3650.inventory_crafter.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class PersistentCraftingMenu extends CraftingMenu {
	
	public PersistentCraftingMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
		super(containerId, inventory, access);
	}
	
	@Override
	public boolean stillValid(Player player) {
		return true;
	}
	
}