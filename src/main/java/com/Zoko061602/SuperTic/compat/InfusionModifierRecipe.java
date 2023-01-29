package com.Zoko061602.SuperTic.compat;

import com.Zoko061602.SuperTic.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tconstruct.library.tools.ToolCore;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.crafting.InfusionRecipe;

import java.util.ArrayList;

public class InfusionModifierRecipe extends InfusionRecipe {

    InfusionModifierRecipe(ItemStack input) {
        super("TINKERSAUGUMENTATION", getOutput(input), Config.TC_lvl * 2, Thaumcraft.getListfromLevel(Config.TC_lvl), input, Thaumcraft.getInputfromLevel(Config.TC_lvl));
    }

    private static Object getOutput(ItemStack input) {
        if (input == null) return null;
        if (input.getItem() == null) return null;
        if (input.getItem() instanceof ToolCore)
            if (input.getTagCompound() != null) {
                NBTTagCompound nbt = input.getTagCompound();
                nbt.setBoolean("STicTC", true);
                int mod = nbt.getCompoundTag("InfiTool").getInteger("Modifiers") + 1;
                nbt.getCompoundTag("InfiTool").setInteger("Modifiers", mod);
                input.setTagCompound(nbt);
            }
        return input;
    }

    @Override
    public boolean matches(ArrayList<ItemStack> input, ItemStack in, World world, EntityPlayer player) {
        if (in == null) return false;
        if (!(in.getItem() instanceof ToolCore)) return false;
        if (in.getTagCompound().getBoolean("STicTC")) return false;
        if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) return false;
        ItemStack i2 = null;

        ArrayList<ItemStack> ii = new ArrayList<ItemStack>();
        for (ItemStack is : input) ii.add(is.copy());

        for (ItemStack comp : getComponents()) {
            boolean b = false;
            for (int a = 0; a < ii.size(); a++) {
                i2 = ii.get(a).copy();
                if (comp.getItemDamage() == 32767)
                    i2.setItemDamage(32767);
                if (areItemStacksEqual(i2, comp, true)) {
                    ii.remove(a);
                    b = true;
                    break;
                }
            }
            if (!b) return false;
        }
        return ii.size() == 0;
    }

    @Override
    public Object getRecipeOutput(ItemStack input) {
        if (input == null || input.getItem() == null)
            return null;
        if (input.getItem() instanceof ToolCore)
            if (input.getTagCompound() != null) {
                NBTTagCompound nbt = input.getTagCompound();
                nbt.setBoolean("STicTC", true);
                int mod = nbt.getCompoundTag("InfiTool").getInteger("Modifiers") + 1;
                nbt.getCompoundTag("InfiTool").setInteger("Modifiers", mod);
                input.setTagCompound(nbt);
            }
        return input;
    }

    public ItemStack[] getComponents() {
        switch (Config.TC_lvl) {
            case 1:
                return Thaumcraft.input1;
            case 3:
                return Thaumcraft.input3;
            default:
                return Thaumcraft.input2;
        }

    }

}
