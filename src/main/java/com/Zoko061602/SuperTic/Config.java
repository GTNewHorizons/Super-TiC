package com.Zoko061602.SuperTic;

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.config.Configuration;

import tconstruct.library.TConstructRegistry;

public class Config {

    static HashMap<Integer, Integer> id_eff = new HashMap<Integer, Integer>();
    static HashMap<Integer, Integer> id_dur = new HashMap<Integer, Integer>();
    static HashMap<Integer, Integer> id_amp = new HashMap<Integer, Integer>();
    static HashMap<Integer, Integer> id_prob = new HashMap<Integer, Integer>();

    static boolean TC;
    static boolean BM;

    public static int TC_lvl;
    public static int BM_LP;

    static void idConfig(File f) {
        Configuration idconf = new Configuration(f);
        idconf.load();
        for (int i = 0; !(i == 3000); i++) if (TConstructRegistry.toolMaterials.get(i) != null) {
            id_eff.put(
                    i,
                    idconf.getInt(
                            "Effect",
                            TConstructRegistry.toolMaterials.get(i).materialName,
                            0,
                            -255,
                            255,
                            "Effect id (if negative gets applied to player)"));
            id_dur.put(
                    i,
                    idconf.getInt(
                            "Duration",
                            TConstructRegistry.toolMaterials.get(i).materialName,
                            200,
                            1,
                            Integer.MAX_VALUE,
                            ""));
            id_amp.put(
                    i,
                    idconf.getInt(
                            "Amplifier",
                            TConstructRegistry.toolMaterials.get(i).materialName,
                            1,
                            1,
                            Integer.MAX_VALUE,
                            "") - 1);
            id_prob.put(
                    i,
                    idconf.getInt(
                            "Probability",
                            TConstructRegistry.toolMaterials.get(i).materialName,
                            1,
                            1,
                            Integer.MAX_VALUE,
                            "Chance effect gets applied 1 in X"));
        } else {
            id_eff.put(i, 0);
            id_dur.put(i, 0);
            id_amp.put(i, 0);
            id_prob.put(i, 0);
        }
        idconf.save();
    }

    static void mainConfig(File f) {
        Configuration mainconf = new Configuration(f);
        mainconf.load();

        TC = mainconf.getBoolean("Thaumcraft", "Compat_Thaumcraft", true, "Enables/Disables Thaumcraft support");
        BM = mainconf.getBoolean("Bloodmagic", "Compat_Bloodmagic", true, "Enables/Disables Bloodmagic support");

        TC_lvl = mainconf.getInt("ThaumcraftLevel", "Compat_Thaumcraft", 2, 1, 3, "Difficulty for Thaumcraft modifier");
        BM_LP = mainconf.getInt(
                "RitualLP",
                "Compat_BloodMagic",
                100000,
                1,
                80000000,
                "LP amount for the Spell of the diligent Tinkerer");
        if (TC_lvl > 3) TC_lvl = 3;
        if (TC_lvl < 1) TC_lvl = 1;
        mainconf.save();
    }
}
