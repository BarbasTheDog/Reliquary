package xreliquary.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lib.enderwizards.sandstone.init.ContentHandler;
import lib.enderwizards.sandstone.items.ItemToggleable;
import lib.enderwizards.sandstone.util.ContentHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.RecipeSorter;
import xreliquary.Reliquary;
import xreliquary.items.alkahestry.AlkahestryCraftingRecipe;
import xreliquary.items.alkahestry.AlkahestryRedstoneRecipe;
import xreliquary.lib.Names;
import xreliquary.lib.Reference;

public class XRRecipes {

    public static ItemStack gunPart(int m) {
        return gunPart(1, m);
    }

    public static ItemStack gunPart(int i, int m) {
        return new ItemStack(ContentHandler.getItem(Names.gun_part), i, m);
    }

    public static ItemStack magazine(int m) {
        return magazine(1, m);
    }

    public static ItemStack magazine(int i, int m) {
        return new ItemStack(ContentHandler.getItem(Names.magazine), i, m);
    }

    public static ItemStack bullet(int m) {
        return bullet(1, m);
    }

    public static ItemStack bullet(int i, int m) {
        return new ItemStack(ContentHandler.getItem(Names.bullet), i, m);
    }

    public static ItemStack potion(int m) {
        return potion(1, m);
    }

    public static ItemStack potion(int i, int m) {
        return new ItemStack(ContentHandler.getItem(Names.condensed_potion), i, m);
    }

    //this version of the addRecipe method checks first to see if the recipe is disabled in our automated recipe-disabler config
    //if any component of the item is in the recipe disabler list, it will ALSO block the recipe automatically.
    public static void addRecipe(boolean isShapeless, ItemStack result, Object... params) {
        boolean enabled = Reliquary.CONFIG.getGroup(Names.recipe_enabled).containsKey(ContentHelper.getIdent(result.getItem())) && Reliquary.CONFIG.getBool(Names.recipe_enabled, ContentHelper.getIdent(result.getItem()));
        if (!enabled) return;
        for (Object obj : params) {
            String unlocalizedName = null;
            if (obj instanceof Block) {
                unlocalizedName = ContentHelper.getIdent((Block) obj);
            } else if(obj instanceof Item) {
                unlocalizedName = ContentHelper.getIdent((Item) obj);
            } else if(obj instanceof ItemStack) {
                unlocalizedName = ContentHelper.getIdent(((ItemStack)obj).getItem());
            }
            if (!Reliquary.CONFIG.getKeys(Names.recipe_enabled).contains(unlocalizedName))
                continue;
            enabled = enabled && Reliquary.CONFIG.getBool(Names.recipe_enabled, unlocalizedName);
        }
        if (!enabled) return;
        if (!isShapeless) GameRegistry.addRecipe(result, params);
            else GameRegistry.addShapelessRecipe(result, params);
    }

    public static void init() {
        // tome and alkahestry recipes
        GameRegistry.addRecipe(new AlkahestryRedstoneRecipe());
        GameRegistry.addRecipe(new AlkahestryCraftingRecipe());

        RecipeSorter.register(Reference.MOD_ID + ":alkahest_crafting", AlkahestryCraftingRecipe.class, RecipeSorter.Category.SHAPELESS, "before:minecraft:shaped");
        RecipeSorter.register(Reference.MOD_ID + ":alkahest_redstone", AlkahestryRedstoneRecipe.class, RecipeSorter.Category.SHAPELESS, "before:" + Reference.MOD_ID + ":alkahest_crafting");

        addRecipe(true, ((ItemToggleable) ContentHandler.getItem(Names.alkahestry_tome)).newItemStack(), Items.book, ContentHandler.getItem(Names.witch_hat), Items.magma_cream, Items.gold_ingot, Blocks.glowstone, Items.nether_wart, new ItemStack(Items.skull, 1, 1), Items.ghast_tear, Items.lava_bucket);

        addRecipe(false, new ItemStack(ContentHandler.getBlock(Names.altar_idle), 1), "olo", "lel", "olo", 'o', Blocks.obsidian, 'l', Blocks.redstone_lamp, 'e', Items.emerald);
        addRecipe(false, new ItemStack(ContentHandler.getBlock(Names.lilypad), 1), "www", "wlw", "www", 'w', XRRecipes.potion(Reference.FERTILIZER_META), 'l', Blocks.waterlily);
        addRecipe(false, new ItemStack(ContentHandler.getBlock(Names.wraith_node), 1), "vv", "vv", 'v', new ItemStack(ContentHandler.getItem(Names.void_tear), 1, 0));
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.interdiction_torch), 1, 0), "mdm", "gbg", "gbg", 'm', Items.magma_cream, 'd', Items.diamond, 'g', ContentHandler.getItem(Names.glowing_water), 'b', Items.blaze_rod);

        addRecipe(true, new ItemStack(ContentHandler.getItem(Names.glowing_bread), 3), Items.bread, Items.bread, Items.bread, ContentHandler.getItem(Names.glowing_water));
        // bullets...
        // empty cases back into nuggets
        addRecipe(true, new ItemStack(Items.gold_nugget, 1), bullet(1, 0));
        // neutral
        addRecipe(false, bullet(4, 1), "sis", "ngn", "ngn", 's', Blocks.stone, 'i', Items.iron_ingot, 'n', Items.gold_nugget, 'g', Items.gunpowder);
        // exorcist
        addRecipe(false, bullet(8, 2), "bbb", "bhb", "bbb", 'b', bullet(1, 1), 'h', ContentHandler.getItem(Names.glowing_water));
        // blaze
        addRecipe(false, bullet(4, 3), "prp", "npn", "ngn", 'p', Items.blaze_powder, 'r', Items.blaze_rod, 'n', Items.gold_nugget, 'g', Items.gunpowder);
        // ender
        addRecipe(false, bullet(8, 4), "bbb", "beb", "bbb", 'b', bullet(1, 7), 'e', Items.ender_eye);
        // venom
        addRecipe(false, bullet(4, 5), "gfg", "ngn", "ngn", 'f', Items.slime_ball, 'n', Items.gold_nugget, 'g', Items.gunpowder);
        // buster
        addRecipe(true, bullet(4, 6), bullet(1, 5), bullet(1, 5), bullet(1, 5), bullet(1, 5), Items.gunpowder, Items.gunpowder, Items.gunpowder, Items.gunpowder, Blocks.tnt);
        // seeker
        addRecipe(false, bullet(4, 7), "sls", "nbn", "ngn", 's', Items.string, 'l', new ItemStack(Items.dye, 1, 4), 'b', Items.slime_ball, 'n', Items.gold_nugget, 'g', Items.gunpowder);

        // sand
        addRecipe(true, bullet(4, 8), bullet(1, 1), bullet(1, 1), bullet(1, 1), bullet(1, 1), Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sandstone);

        // storm
        addRecipe(false, bullet(8, 9), "bbb", "bhb", "bbb", 'b', bullet(1, 1), 'h', new ItemStack(Items.dye, 1, 4));

        // magazines...
        addRecipe(false, magazine(5, 0), "i i", "igi", "sis", 's', Blocks.stone, 'i', Items.iron_ingot, 'g', Blocks.glass);

        // neutral
        addRecipe(true, magazine(1, 1), bullet(1, 1), bullet(1, 1), bullet(1, 1), bullet(1, 1), bullet(1, 1), bullet(1, 1), bullet(1, 1), bullet(1, 1), magazine(1, 0));
        // exorcist
        addRecipe(true, magazine(1, 2), bullet(1, 2), bullet(1, 2), bullet(1, 2), bullet(1, 2), bullet(1, 2), bullet(1, 2), bullet(1, 2), bullet(1, 2), magazine(1, 0));
        // blaze
        addRecipe(true, magazine(1, 3), bullet(1, 3), bullet(1, 3), bullet(1, 3), bullet(1, 3), bullet(1, 3), bullet(1, 3), bullet(1, 3), bullet(1, 3), magazine(1, 0));
        // ender
        addRecipe(true, magazine(1, 4), bullet(1, 4), bullet(1, 4), bullet(1, 4), bullet(1, 4), bullet(1, 4), bullet(1, 4), bullet(1, 4), bullet(1, 4), magazine(1, 0));
        // venom
        addRecipe(true, magazine(1, 5), bullet(1, 5), bullet(1, 5), bullet(1, 5), bullet(1, 5), bullet(1, 5), bullet(1, 5), bullet(1, 5), bullet(1, 5), magazine(1, 0));
        // buster
        addRecipe(true, magazine(1, 6), bullet(1, 6), bullet(1, 6), bullet(1, 6), bullet(1, 6), bullet(1, 6), bullet(1, 6), bullet(1, 6), bullet(1, 6), magazine(1, 0));
        // seeker
        addRecipe(true, magazine(1, 7), bullet(1, 7), bullet(1, 7), bullet(1, 7), bullet(1, 7), bullet(1, 7), bullet(1, 7), bullet(1, 7), bullet(1, 7), magazine(1, 0));

        // sand
        addRecipe(true, magazine(1, 8), bullet(1, 8), bullet(1, 8), bullet(1, 8), bullet(1, 8), bullet(1, 8), bullet(1, 8), bullet(1, 8), bullet(1, 8), magazine(1, 0));

        // storm
        addRecipe(true, magazine(1, 9), bullet(1, 9), bullet(1, 9), bullet(1, 9), bullet(1, 9), bullet(1, 9), bullet(1, 9), bullet(1, 9), bullet(1, 9), magazine(1, 0));

        // gunpart 0 = grip, 1 = barrel, 2 = mechanism
        addRecipe(false, gunPart(1, 0), "iii", "imi", "ici", 'i', Items.iron_ingot, 'c', magazine(1, 0), 'm', Items.magma_cream);
        addRecipe(false, gunPart(1, 1), "iii", "eme", "iii", 'i', Items.iron_ingot, 'e', Items.ender_pearl, 'm', Items.magma_cream);
        addRecipe(false, gunPart(1, 2), "iib", "rmi", "iii", 'i', Items.iron_ingot, 'b', Blocks.stone_button, 'r', Items.blaze_rod, 'm', Items.magma_cream);

        // handgun
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.handgun), 1, 0), "bim", "isi", "igi", 'i', Items.iron_ingot, 'b', gunPart(1, 1), 'm', gunPart(1, 2), 'g', gunPart(1, 0), 's', Items.slime_ball);

		/* other items */

        // fortune coin
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.fortune_coin), 1), "ege", "ghg", "ege", 'e', Items.ender_eye, 'g', Items.gold_ingot, 'h', ContentHandler.getItem(Names.glowing_water));

        // cross of mercy
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.mercy_cross), 1), "wgw", "glg", "wgw", 'w', ContentHandler.getItem(Names.glowing_water), 'g', Items.gold_ingot, 'l', Items.leather);

        // holy hand grenade
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.holy_hand_grenade), 4), "wgw", "gtg", "wgw", 'w', ContentHandler.getItem(Names.glowing_water), 'g', Items.gold_nugget, 't', Blocks.tnt);

        // sojourner's staff
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.sojourner_staff), 1), "cvc", "gbg", "wgw", 'w', ContentHandler.getItem(Names.glowing_water), 'g', Items.gold_ingot, 'b', Items.blaze_rod, 'c', Items.magma_cream, 'v', ((ItemToggleable) ContentHandler.getItem(Names.void_tear)).newItemStack());

        // lantern of paranoia
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.lantern_of_paranoia), 1), "ici", "gfg", "ivi", 'i', Items.iron_ingot, 'c', Items.magma_cream, 'g', Blocks.glass, 'f', Items.flint_and_steel, 'v', ((ItemToggleable) ContentHandler.getItem(Names.void_tear)).newItemStack());

        // touchstone
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.midas_touchstone), 1, 0), "bbb", "rvr", "ggg", 'b', Blocks.glowstone, 'r', Items.blaze_rod, 'g', Items.gold_ingot, 'v', ((ItemToggleable) ContentHandler.getItem(Names.void_tear)).newItemStack());

        // chalice
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.emperor_chalice), 1, 0), "gtg", "ege", "tgt", 't', Items.ghast_tear, 'e', Items.emerald, 'g', Items.gold_ingot);

        // salamander's eye
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.salamander_eye), 1), "bcb", "tet", "bcb", 'b', Items.blaze_rod, 'c', Items.magma_cream, 't', Items.ghast_tear, 'e', Items.ender_eye);

        // ice rod
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.ice_magus_rod), 1, 0), "dtd", "tpt", "tpt", 'd', Items.diamond, 't', ((ItemToggleable) ContentHandler.getItem(Names.void_tear)).newItemStack(), 'p', Blocks.packed_ice);

        // ender staff
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.ender_staff), 1, 0), " ve", " vv", "s  ", 'v', ContentHandler.getItem(Names.void_tear), 'e', Items.ender_eye, 's', Items.stick);

        // magicbane
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.magicbane), 1, 0), "eee", "ege", "iee", 'e', Items.ender_eye, 'g', Items.gold_ingot, 'i', Items.iron_ingot);

        // rose
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.witherless_rose), 1), "hnh", "nrn", "hnh", 'h', ContentHandler.getItem(Names.glowing_water), 'n', Items.nether_star, 'r', new ItemStack(Blocks.double_plant, 1, 4));

        // crimson cloth
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.crimson_cloth), 1), "prp", "bdb", "prp", 'p', potion(Reference.INVISIBILITY_META), 'r', new ItemStack(Blocks.wool, 1, Reference.RED_WOOL_META), 'b', new ItemStack(Blocks.wool, 1, Reference.BLACK_WOOL_META), 'd', potion(Reference.BLINDING_META));

        // cloak
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.twilight_cloak), 1), "bcb", "cvc", "cbc", 'b', new ItemStack(Blocks.wool, 1, Reference.BLACK_WOOL_META), 'c', ContentHandler.getItem(Names.crimson_cloth), 'v', new ItemStack(ContentHandler.getItem(Names.void_tear), 1, 0));

        // void tear
        addRecipe(false, ((ItemToggleable) ContentHandler.getItem(Names.void_tear)).newItemStack(), "et", "te", 'e', Items.ender_eye, 't', Items.ghast_tear);

        // angelic feather
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.angelic_feather), 1), "aja", "vfv", "aja", 'a', ContentHandler.getItem(Names.angelheart_vial), 'v', new ItemStack(ContentHandler.getItem(Names.void_tear), 1, 0), 'f', Items.feather, 'j', potion(Reference.BOUNDING_META));

        // phoenix down
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.phoenix_down), 1), "ama", "mfm", "ama", 'm', Items.magma_cream, 'a', ContentHandler.getItem(Names.angelheart_vial), 'f', ContentHandler.getItem(Names.angelic_feather));

        // dragon talon
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.dragon_talon), 1), "flf", "lvl", "flf", 'f', potion(Reference.FIRE_WARDING_META), 'l', Items.leather, 'v', new ItemStack(ContentHandler.getItem(Names.void_tear), 1, 0));

        // dragon claws
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.dragon_claws), 1), "cmc", "cvc", "mcm", 'c', ContentHandler.getItem(Names.dragon_talon), 'm', Items.magma_cream, 'v', new ItemStack(ContentHandler.getItem(Names.void_tear), 1, 0));

        // claws of the firedrinker
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.claws_of_the_firedrinker), 1), "cmc", "mem", "cmc", 'c', ContentHandler.getItem(Names.dragon_claws), 'm', Items.magma_cream, 'e', ContentHandler.getItem(Names.salamander_eye));

        // squid beak to bonemeal
        addRecipe(true, new ItemStack(Items.dye, 1, Reference.WHITE_DYE_META), ContentHandler.getItem(Names.squid_beak));

        // kraken shell fragment
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.kraken_shell_fragment), 1, 0), "awa", "wbw", "awa", 'a', ContentHandler.getItem(Names.angelheart_vial), 'w', potion(Reference.BREATHING_META), 'b', ContentHandler.getItem(Names.squid_beak));

        // kraken shell
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.kraken_shell), 1, 0), "sss", "svs", "sss", 's', ContentHandler.getItem(Names.kraken_shell_fragment), 'v', new ItemStack(ContentHandler.getItem(Names.void_tear), 1, 0));

        // hero medallion
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.hero_medallion), 1), "eie", "ivi", "eie", 'e', Items.ender_eye, 'i', Items.iron_ingot, 'v', new ItemStack(ContentHandler.getItem(Names.void_tear), 1, 0));

        // destruction catalyst
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.destruction_catalyst), 1, 0), "rrr", "rtr", "rrf", 'f', Items.flint, 't', new ItemStack(ContentHandler.getItem(Names.midas_touchstone), 1, -1), 'r', Items.blaze_rod);

        // serpent staff
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.serpent_staff), 1), "oeo", "sks", " s ", 'o', Blocks.obsidian, 'e', Items.ender_eye, 's', Items.stick, 'k', ContentHandler.getItem(Names.kraken_shell));

        // infernal tear
        addRecipe(false, new ItemStack(ContentHandler.getItem(Names.infernal_tear), 1, 0), "cmc", "mtm", "clc", 'c', Items.coal, 'm', Items.magma_cream, 't', new ItemStack(ContentHandler.getItem(Names.void_tear), 1, 0), 'l', Items.lava_bucket);

		/* potions and splash potions */

        // empty vial
        addRecipe(false, potion(5, Reference.EMPTY_VIAL_META), "g g", "g g", " g ", 'g', Blocks.glass_pane);

        // base solvent
        addRecipe(true, potion(Reference.POTION_META), Items.nether_wart, potion(Reference.WATER_META));

        // base splash solvent
        addRecipe(true, potion(Reference.SPLASH_META), Items.nether_wart, Items.gunpowder, potion(Reference.WATER_META));
        addRecipe(true, potion(Reference.SPLASH_META), potion(Reference.POTION_META), Items.gunpowder);

        // glowing water
        addRecipe(true, new ItemStack(ContentHandler.getItem(Names.glowing_water), 1), potion(Reference.SPLASH_META), Items.glowstone_dust, Items.glowstone_dust, Items.glowstone_dust);

        // angelheart vial
        addRecipe(true, new ItemStack(ContentHandler.getItem(Names.angelheart_vial), 2), potion(Reference.PANACEA_META), ContentHandler.getItem(Names.glowing_water));

        // speed potion
        addRecipe(true, potion(Reference.SPEED_META), potion(Reference.POTION_META), Items.sugar, Items.redstone, Items.glowstone_dust);

        // dig potion
        addRecipe(true, potion(Reference.DIGGING_META), potion(Reference.POTION_META), Items.bone, Items.redstone, Items.glowstone_dust);

        // strength potion
        addRecipe(true, potion(Reference.STRENGTH_META), potion(Reference.POTION_META), Items.blaze_powder, Items.redstone, Items.glowstone_dust);

        // heal potion
        addRecipe(true, potion(Reference.HEALING_META), potion(Reference.POTION_META), Items.speckled_melon, Items.glowstone_dust);

        // jump potion
        addRecipe(true, potion(Reference.BOUNDING_META), potion(Reference.POTION_META), Items.feather, Items.redstone, Items.glowstone_dust);

        // regen potion
        addRecipe(true, potion(Reference.REGENERATION_META), potion(Reference.POTION_META), Items.ghast_tear, Items.redstone, Items.glowstone_dust);

        // resist potion
        addRecipe(true, potion(Reference.RESISTANCE_META), potion(Reference.POTION_META), Items.leather, Items.redstone, Items.glowstone_dust);

        // fire resist potion
        addRecipe(true, potion(Reference.FIRE_WARDING_META), potion(Reference.POTION_META), Items.magma_cream, Items.redstone);

        // breathing potion
        addRecipe(true, potion(Reference.BREATHING_META), potion(Reference.POTION_META), new ItemStack(Items.dye, 1, 0), Items.redstone);

        // invis potion
        addRecipe(true, potion(Reference.INVISIBILITY_META), potion(Reference.INFRAVISION_META), Items.fermented_spider_eye, Items.redstone);

        // vision potion
        addRecipe(true, potion(Reference.INFRAVISION_META), potion(Reference.POTION_META), Items.golden_carrot, Items.redstone);

        // protection potion
        addRecipe(true, potion(8, Reference.PROTECTION_META), potion(Reference.FIRE_WARDING_META), potion(Reference.FIRE_WARDING_META), potion(Reference.FIRE_WARDING_META), potion(Reference.FIRE_WARDING_META), Items.glowstone_dust, potion(Reference.RESISTANCE_META), potion(Reference.RESISTANCE_META), potion(Reference.RESISTANCE_META), potion(Reference.RESISTANCE_META));

        // potency potion
        addRecipe(true, potion(8, Reference.POTENCE_META), potion(Reference.STRENGTH_META), potion(Reference.STRENGTH_META), potion(Reference.STRENGTH_META), potion(Reference.STRENGTH_META), Items.glowstone_dust, potion(Reference.BOUNDING_META), potion(Reference.BOUNDING_META), potion(Reference.BOUNDING_META), potion(Reference.BOUNDING_META));

        // celerity potion
        addRecipe(true, potion(8, Reference.CELERITY_META), potion(Reference.DIGGING_META), potion(Reference.DIGGING_META), potion(Reference.DIGGING_META), potion(Reference.DIGGING_META), Items.glowstone_dust, potion(Reference.SPEED_META), potion(Reference.SPEED_META), potion(Reference.SPEED_META), potion(Reference.SPEED_META));

        // stalker potion
        addRecipe(true, potion(8, Reference.STALKER_META), potion(Reference.INFRAVISION_META), potion(Reference.INFRAVISION_META), potion(Reference.INFRAVISION_META), potion(Reference.INFRAVISION_META), Items.glowstone_dust, potion(Reference.INVISIBILITY_META), potion(Reference.INVISIBILITY_META), potion(Reference.INVISIBILITY_META), potion(Reference.INVISIBILITY_META));

        // panacea potion
        addRecipe(true, potion(8, Reference.PANACEA_META), potion(Reference.HEALING_META), potion(Reference.HEALING_META), potion(Reference.HEALING_META), potion(Reference.HEALING_META), Items.milk_bucket, potion(Reference.REGENERATION_META), potion(Reference.REGENERATION_META), potion(Reference.REGENERATION_META), potion(Reference.REGENERATION_META));

        // aphrodite
        addRecipe(true, potion(Reference.APHRODITE_META), potion(Reference.SPLASH_META), new ItemStack(Items.dye, 1, 3), new ItemStack(Items.dye, 1, 1), Items.redstone);

        // poison
        addRecipe(true, potion(Reference.POISON_META), potion(Reference.SPLASH_META), Items.spider_eye, Items.fermented_spider_eye, Items.redstone);

        // harm
        addRecipe(true, potion(Reference.ACID_META), potion(Reference.SPLASH_META), Items.speckled_melon, Items.fermented_spider_eye, Items.glowstone_dust);

        // confusion
        addRecipe(true, potion(Reference.CONFUSION_META), potion(Reference.SPLASH_META), Items.golden_carrot, Items.fermented_spider_eye, Items.redstone);

        // slowness
        addRecipe(true, potion(Reference.SLOWING_META), potion(Reference.SPLASH_META), Items.sugar, Items.fermented_spider_eye, Items.glowstone_dust);

        // weakness
        addRecipe(true, potion(Reference.WEAKNESS_META), potion(Reference.SPLASH_META), Items.blaze_powder, Items.fermented_spider_eye, Items.glowstone_dust);

        // wither
        addRecipe(true, potion(Reference.WITHER_META), potion(Reference.SPLASH_META), new ItemStack(Items.skull, 1, 1), Items.glowstone_dust, Items.glowstone_dust);

        // blindness
        addRecipe(true, potion(Reference.BLINDING_META), potion(Reference.SPLASH_META), Items.golden_carrot, Items.fermented_spider_eye, Items.golden_carrot);

        // ruin
        addRecipe(true, potion(3, Reference.RUINATION_META), potion(Reference.SLOWING_META), potion(Reference.WEAKNESS_META), potion(Reference.POISON_META), Items.glowstone_dust);

        // fertility
        addRecipe(true, potion(Reference.FERTILIZER_META), potion(Reference.SPLASH_META), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15));

    }

}
