package com.Zoko061602.SuperTic.compat;

import com.Zoko061602.SuperTic.Config;

import WayofTime.alchemicalWizardry.api.rituals.Rituals;

public class BMCompat {

    public static void bloody() {
        try {
            Rituals.registerRitual(
                    "SuperTiC_Modifier",
                    1,
                    Config.BM_LP,
                    new RitualTinkerer(),
                    "Spell of the diligent Tinkerer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
