package com.Zoko061602.SuperTic.compat;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.Zoko061602.SuperTic.Config;

import cpw.mods.fml.common.Loader;
import tconstruct.armor.TinkerArmor;
import tconstruct.tools.TinkerTools;
import tconstruct.weaponry.TinkerWeaponry;
import tconstruct.world.TinkerWorld;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;

class Thaumcraft {

    private static Item[] tools = new Item[] { TinkerTools.pickaxe, TinkerTools.shovel, TinkerTools.hatchet,
            TinkerTools.broadsword, TinkerTools.longsword, TinkerTools.rapier, TinkerTools.dagger, TinkerTools.cutlass,
            TinkerTools.frypan, TinkerTools.battlesign, TinkerTools.chisel, TinkerTools.mattock, TinkerTools.scythe,
            TinkerTools.lumberaxe, TinkerTools.cleaver, TinkerTools.hammer, TinkerTools.battleaxe,
            TinkerTools.excavator, TinkerWeaponry.shuriken, TinkerWeaponry.throwingknife, TinkerWeaponry.javelin,
            TinkerWeaponry.shortbow, TinkerWeaponry.longbow, TinkerWeaponry.crossbow, TinkerWeaponry.arrowAmmo,
            TinkerWeaponry.boltAmmo, TinkerWeaponry.boneana };

    private static ItemStack appleeasy = new ItemStack(TinkerArmor.diamondApple);
    private static ItemStack applehard = new ItemStack(Items.golden_apple, 1, 1);

    static ItemStack[] input1 = new ItemStack[] { new ItemStack(TinkerWorld.metalBlock, 1, 7),
            new ItemStack(Items.golden_apple), new ItemStack(Items.golden_apple) };
    static ItemStack[] input2 = new ItemStack[] { new ItemStack(TinkerWorld.metalBlock, 1, 2), appleeasy, appleeasy };
    static ItemStack[] input3 = new ItemStack[] { new ItemStack(Items.nether_star), applehard, applehard };
    private static AspectList list1 = new AspectList().add(Aspect.TOOL, 16);
    private static AspectList list2 = new AspectList().add(Aspect.TOOL, 32);
    private static AspectList list3 = new AspectList().add(Aspect.TOOL, 64);
    static InfusionRecipe infusion;

    static AspectList getListfromLevel(int lvl) {
        switch (Config.TC_lvl) {
            case 1:
                return list1;
            case 3:
                return list3;
            default:
                return list2;

        }
    }

    static ItemStack[] getInputfromLevel(int lvl) {
        switch (Config.TC_lvl) {
            case 1:
                return input1;
            case 3:
                return input3;
            default:
                return input2;

        }
    }

    static void registerRecipes() {
        if (Loader.isModLoaded("dreamcraft")) {
            appleeasy = applehard;
            applehard = new ItemStack(TinkerArmor.diamondApple);
        }
        infusion = new InfusionModifierRecipe(new ItemStack(tools[0]));
        for (int i = 1; !(i == tools.length); i++) new InfusionModifierRecipe(new ItemStack(tools[i]));

    }
}
