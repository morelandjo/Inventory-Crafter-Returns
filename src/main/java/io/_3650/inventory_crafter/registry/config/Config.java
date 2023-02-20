package io._3650.inventory_crafter.registry.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class Config {
	
	// Behold my abomination (I know some of these finals arent needed but its funny)
	public static final class Presets {
		public static final class PDefault {
			public static final int X = 140;
			public static final int Y = 61;
		}
		public static final class PClose {
			public static final int X = 124;
			public static final int Y = 61;
		}
		public static final class PGrid {
			public static final int X = 76;
			public static final int Y = 26;
		}
		public static final class PBad {
			public static final int X = -36;
			public static final int Y = 64;
		}
	}
	
	public static class Common {
		
		public final BooleanValue requireCraftingTable;
		
		Common(ForgeConfigSpec.Builder builder) {
			builder.push("balance");
			
			requireCraftingTable = builder.comment("Does the inventory crafting table require a crafting table to work?","Keeping this on is recommended for balance reasons.","[Default: true]").define("requireCraftingTable", true);
			
			builder.pop();
		}
		
	}
	
	public static class Client {
		
		public final ConfigValue<String> preset;
		public final IntValue buttonLeftX;
		public final IntValue buttonTopY;
		
		Client(ForgeConfigSpec.Builder builder) {
			builder.push("button");
			
			preset = builder.comment(
					"Button Position Preset",
					" NONE - No preset loading",
					" DEFAULT - [140, 61] - To the right of the recipe book",
					" CLOSE - [124, 61] - Close to the right of the recipe book",
					" GRID - [76, 26] - To the left of the inventory crafting grid",
					" BAD - [-36, 64] - At least none of the other presets are this bad").define("preset", "NONE");
			buttonLeftX = builder.comment("X of the left edge of the button", "[Default: 140]").defineInRange("buttonLeftX", Presets.PDefault.X, -500, 500);
			buttonTopY = builder.comment("Y of the top edge of the button", "[Default: 61]").defineInRange("buttonTopY", Presets.PDefault.Y, -500, 500);
			
			builder.pop();
		}
		
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;
	
	static {
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();
		
		final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
	}
	
}