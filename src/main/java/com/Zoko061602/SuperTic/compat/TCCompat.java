package com.Zoko061602.SuperTic.compat;

import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class TCCompat {
    public static void thaumic() {
        Thaumcraft.registerRecipes();
        AspectList al = new AspectList().add(Aspect.TOOL, 20).add(Aspect.MAGIC, 20);
        new ResearchItem("TINKERSAUGMENTATION", "ARTIFICE", al, 7, 6, 7, new ItemStack(TinkerTools.titleIcon, 1, 4099))
                .setPages(new ResearchPage("STiC"), new ResearchPage(Thaumcraft.infusion)).setAutoUnlock()
                .registerResearchItem();
    }
}
