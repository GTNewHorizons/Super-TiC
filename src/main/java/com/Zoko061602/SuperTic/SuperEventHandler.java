package com.Zoko061602.SuperTic;

import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SuperEventHandler {

    @SubscribeEvent
    public void PlayerAttack(AttackEntityEvent event) {
        PotionEventHandler.getInstance().applyEffects(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT) // this method doesnt need to run on the Server, or be referenced by it
    public void Tooltip(ItemTooltipEvent event) {
        if (event == null || event.itemStack == null || event.itemStack.getItem() == null) return;
        TooltipEventHandler.getInstance().addTooltips(event);
    }

}
