package com.Zoko061602.SuperTic;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.util.IToolPart;

@SideOnly(Side.CLIENT) // this class doesnt need to run on the Server, or be referenced by it
class TooltipEventHandler {

    private static TooltipEventHandler INSTANCE = new TooltipEventHandler();

    static TooltipEventHandler getInstance() {
        return INSTANCE;
    }

    private static String getLatin(int a) {
        String r;
        switch (a) {
            case 0:
                r = "";
                break;
            case 1:
                r = " I";
                break;
            case 2:
                r = " II";
                break;
            case 3:
                r = " III";
                break;
            case 4:
                r = " IV";
                break;
            case 5:
                r = " V";
                break;
            case 6:
                r = " VI";
                break;
            case 7:
                r = " VII";
                break;
            case 8:
                r = " VIII";
                break;
            case 9:
                r = " IX";
                break;
            case 10:
                r = " X";
                break;
            default:
                r = " X+";
                break;
        }
        return r;
    }

    void addTooltips(ItemTooltipEvent e) {
        addPartTooltips(e);
        addPotionTooltips(e);
        addGregTooltips(e);
    }

    private void addPartTooltips(ItemTooltipEvent e) {
        if (e.itemStack.getItem() instanceof IToolPart) {
            IToolPart item = (IToolPart) e.itemStack.getItem();
            int id = Config.id_eff.get(item.getMaterialID(e.itemStack));
            if (id > 0) e.toolTip.add(
                    EnumChatFormatting.DARK_AQUA
                            + StatCollector.translateToLocal(new PotionEffect(id, 1).getEffectName())
                            + getLatin(Config.id_amp.get(id)));
            if (id < 0) {
                id *= -1;
                e.toolTip.add(
                        EnumChatFormatting.AQUA
                                + StatCollector.translateToLocal(new PotionEffect(id, 1).getEffectName())
                                + getLatin(Config.id_amp.get(id)));
            }
        }
    }

    private void addPotionTooltips(ItemTooltipEvent e) {
        if (e.itemStack.getItem() instanceof ToolCore) {
            ToolCore core = (ToolCore) e.itemStack.getItem();
            if (e.itemStack.hasTagCompound()) {
                NBTTagCompound tags = e.itemStack.getTagCompound();

                PotionEffect head = new PotionEffect(0, 1);
                PotionEffect handle = new PotionEffect(0, 1);
                PotionEffect binding = new PotionEffect(0, 1);
                PotionEffect extra = new PotionEffect(0, 1);
                int p1;
                int p2;
                int p3;
                int p4;

                try {
                    p1 = tags.getCompoundTag("InfiTool").getInteger("Head");
                    if (!(Config.id_eff.get(p1) == 0)) head = new PotionEffect(
                            Config.id_eff.get(tags.getCompoundTag("InfiTool").getInteger("Head")),
                            1,
                            Config.id_amp.get(tags.getCompoundTag("InfiTool").getInteger("Head")));

                    p2 = tags.getCompoundTag("InfiTool").getInteger("Handle");
                    if (!(Config.id_eff.get(p2) == 0)) handle = new PotionEffect(
                            Config.id_eff.get(tags.getCompoundTag("InfiTool").getInteger("Handle")),
                            1,
                            Config.id_amp.get(tags.getCompoundTag("InfiTool").getInteger("Handle")));

                    if (core.getPartAmount() >= 3) {
                        p3 = tags.getCompoundTag("InfiTool").getInteger("Accessory");
                        if (!(Config.id_eff.get(p3) == 0)) binding = new PotionEffect(
                                Config.id_eff.get(tags.getCompoundTag("InfiTool").getInteger("Accessory")),
                                1,
                                Config.id_amp.get(tags.getCompoundTag("InfiTool").getInteger("Accessory")));
                    }
                    if (core.getPartAmount() == 4) {
                        p4 = tags.getCompoundTag("InfiTool").getInteger("Extra");
                        if (!(Config.id_eff.get(p4) == 0)) extra = new PotionEffect(
                                Config.id_eff.get(tags.getCompoundTag("InfiTool").getInteger("Extra")),
                                1,
                                Config.id_amp.get(tags.getCompoundTag("InfiTool").getInteger("Extra")));
                    }
                    // Head
                    if (!(head.getPotionID() == 0)) {
                        if (head.getPotionID() < 0) {
                            int c = head.getAmplifier();
                            if (handle.getPotionID() == head.getPotionID() && c < handle.getAmplifier())
                                c = handle.getAmplifier();
                            if (binding.getPotionID() == head.getPotionID() && c < binding.getAmplifier())
                                c = binding.getAmplifier();
                            if (extra.getPotionID() == head.getPotionID() && c < extra.getAmplifier())
                                c = extra.getAmplifier();
                            e.toolTip.add(
                                    1,
                                    EnumChatFormatting.AQUA
                                            + StatCollector.translateToLocal(
                                                    new PotionEffect(head.getPotionID() * (-1), 1).getEffectName())
                                            + getLatin(c));
                        } else {
                            int c = head.getAmplifier();
                            if (handle.getPotionID() == head.getPotionID() && c < handle.getAmplifier())
                                c = handle.getAmplifier();
                            if (binding.getPotionID() == head.getPotionID() && c < binding.getAmplifier())
                                c = binding.getAmplifier();
                            if (extra.getPotionID() == head.getPotionID() && c < extra.getAmplifier())
                                c = extra.getAmplifier();
                            e.toolTip.add(
                                    1,
                                    EnumChatFormatting.DARK_AQUA
                                            + StatCollector.translateToLocal(
                                                    new PotionEffect(head.getPotionID(), 1).getEffectName())
                                            + getLatin(c));
                        }
                    }
                    // Handle
                    if (!(handle.getPotionID() == 0) && !(handle.getPotionID() == head.getPotionID())) {
                        if (handle.getPotionID() < 0) {
                            int c = handle.getAmplifier();
                            if (binding.getPotionID() == handle.getPotionID() && c < binding.getAmplifier())
                                c = binding.getAmplifier();
                            if (extra.getPotionID() == handle.getPotionID() && c < extra.getAmplifier())
                                c = extra.getAmplifier();
                            e.toolTip.add(
                                    1,
                                    EnumChatFormatting.AQUA
                                            + StatCollector.translateToLocal(
                                                    new PotionEffect(handle.getPotionID() * (-1), 1).getEffectName())
                                            + getLatin(c));
                        } else {
                            int c = handle.getAmplifier();
                            if (binding.getPotionID() == handle.getPotionID() && c < binding.getAmplifier())
                                c = binding.getAmplifier();
                            if (extra.getPotionID() == handle.getPotionID() && c < extra.getAmplifier())
                                c = extra.getAmplifier();
                            e.toolTip.add(
                                    1,
                                    EnumChatFormatting.DARK_AQUA
                                            + StatCollector.translateToLocal(
                                                    new PotionEffect(handle.getPotionID(), 1).getEffectName())
                                            + getLatin(c));
                        }
                    }
                    // Binding
                    if (core.getPartAmount() >= 3)
                        if (!(binding.getPotionID() == 0) && !(binding.getPotionID() == head.getPotionID())
                                && !(binding.getPotionID() == handle.getPotionID())) {
                                    if (binding.getPotionID() < 0) {
                                        int c = binding.getAmplifier();
                                        if (extra.getPotionID() == binding.getPotionID() && c < extra.getAmplifier())
                                            c = extra.getAmplifier();
                                        e.toolTip.add(
                                                1,
                                                EnumChatFormatting.AQUA + StatCollector.translateToLocal(
                                                        new PotionEffect(binding.getPotionID() * (-1), 1)
                                                                .getEffectName())
                                                        + getLatin(c));
                                    } else {
                                        int c = binding.getAmplifier();
                                        if (extra.getPotionID() == binding.getPotionID() && c < extra.getAmplifier())
                                            c = extra.getAmplifier();
                                        e.toolTip.add(
                                                1,
                                                EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(
                                                        new PotionEffect(binding.getPotionID(), 1).getEffectName())
                                                        + getLatin(c));
                                    }
                                }
                    // Extra
                    if (core.getPartAmount() == 4)
                        if (!(extra.getPotionID() == 0) && !(extra.getPotionID() == head.getPotionID())
                                && !(extra.getPotionID() == handle.getPotionID())
                                && !(extra.getPotionID() == binding.getPotionID())) {
                                    if (extra.getPotionID() < 0) e.toolTip.add(
                                            1,
                                            EnumChatFormatting.AQUA + StatCollector.translateToLocal(
                                                    new PotionEffect(extra.getPotionID() * (-1), 1).getEffectName())
                                                    + getLatin(extra.getAmplifier()));
                                    else e.toolTip.add(
                                            1,
                                            EnumChatFormatting.DARK_AQUA
                                                    + StatCollector.translateToLocal(
                                                            new PotionEffect(extra.getPotionID(), 1).getEffectName())
                                                    + getLatin(extra.getAmplifier()));

                                }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void addGregTooltips(ItemTooltipEvent e) {
        Item item = e.itemStack.getItem();
        if (item instanceof IToolPart) {
            GameRegistry.UniqueIdentifier ui = GameRegistry.findUniqueIdentifierFor(item);
            if (ui != null && ui.modId.equals("TGregworks")) {
                ToolMaterial mat = TConstructRegistry.toolMaterials.get(((IToolPart) item).getMaterialID(e.itemStack));
                if (mat != null) {
                    if (mat.stonebound > 0) e.toolTip.add("Stonebound: x" + mat.stonebound);
                    if (mat.stonebound < 0) e.toolTip.add("Jagged: x" + mat.stonebound * -1);

                    // This is already added by TGregworks...
                    // if (mat.reinforced != 0)
                    // e.toolTip.add("Reinforced" + getLatin(mat.reinforced));
                }
            }
        }

    }
}
