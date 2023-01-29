package com.Zoko061602.SuperTic;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import tconstruct.library.tools.ToolCore;

class PotionEventHandler {

    private static PotionEventHandler INSTANCE = new PotionEventHandler();

    static PotionEventHandler getInstance() {
        return INSTANCE;
    }

    void applyEffects(AttackEntityEvent event) {
        if (((event.target instanceof EntityLivingBase)) && ((event.entityPlayer instanceof EntityPlayerMP))) {
            EntityLivingBase target = (EntityLivingBase) event.target;
            EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;

            ItemStack toolStack = player.getCurrentEquippedItem();

            if (toolStack == null) {
                return;
            }

            if (((target instanceof EntityPlayer)) && (!player.canAttackPlayer((EntityPlayer) target))) {
                return;
            }

            if ((toolStack.getItem() instanceof ToolCore)) {
                ToolCore tool = (ToolCore) toolStack.getItem();

                if (tool == null) {
                    return;
                }

                NBTTagCompound tags = toolStack.getTagCompound();

                if (tags == null) {
                    return;
                }

                int head = tags.getCompoundTag("InfiTool").getInteger("Head");
                int handle = tags.getCompoundTag("InfiTool").getInteger("Handle");

                int accessory = -1;
                int extra = -1;

                if (tool.getPartAmount() >= 3) {
                    accessory = tags.getCompoundTag("InfiTool").getInteger("Accessory");
                }

                if (tool.getPartAmount() >= 4) {
                    extra = tags.getCompoundTag("InfiTool").getInteger("Extra");
                }

                if (Config.id_eff.containsKey(head)) {
                    if (Config.id_eff.get(head) > 0) addEffect(target, head);
                    else if (Config.id_eff.get(head) < 0) addEffect(player, head);

                }
                if (Config.id_eff.containsKey(handle)) {
                    if (Config.id_eff.get(handle) > 0) addEffect(target, handle);
                    else if (Config.id_eff.get(handle) < 0) addEffect(player, handle);

                }
                if (Config.id_eff.containsKey(accessory)) {
                    if (Config.id_eff.get(accessory) > 0) addEffect(target, accessory);
                    else if (Config.id_eff.get(accessory) < 0) addEffect(player, accessory);

                }
                if (Config.id_eff.containsKey(extra)) {
                    if (Config.id_eff.get(extra) > 0) addEffect(target, extra);
                    else if (Config.id_eff.get(extra) < 0) addEffect(player, extra);

                }

            }
        }
    }

    private void addEffect(EntityLivingBase e, int id) {
        int r = new Random().nextInt(Config.id_prob.get(id));
        if (r == 0) e.addPotionEffect(
                new PotionEffect(Config.id_eff.get(id) * (-1), Config.id_dur.get(id), Config.id_amp.get(id)));
    }

}
