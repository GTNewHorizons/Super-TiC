package com.Zoko061602.SuperTic.compat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import tconstruct.library.tools.ToolCore;

public class RitualTinkerer extends RitualEffect {

    ItemStack stack = null;
    EntityItem item = null;
    NBTTagCompound nbt = new NBTTagCompound();

    @Override
    public int getCostPerRefresh() {
        return 0;
    }

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();

        if (ritualStone.getVar1() == 0) {
            @SuppressWarnings("unchecked")
            List<EntityItem> list = world.getEntitiesWithinAABB(
                    EntityItem.class,
                    AxisAlignedBB.getBoundingBox(x, y + 1.0D, z, x + 1, y + 2, z + 1));

            for (int i = 0; !(i == list.size()); i++) {
                item = list.get(i);
                stack = item.getEntityItem();
                if (stack != null) {
                    if (stack.getItem() instanceof ToolCore) {
                        nbt = stack.getTagCompound();
                        if (!nbt.getBoolean("STicBM")) {
                            ritualStone.setVar1(1);
                            world.addWeatherEffect(new EntityLightningBolt(world, x, y + 1, z));
                            ritualStone.setCooldown(399);
                            item.setDead();
                            break;
                        } else {
                            @SuppressWarnings("unchecked")
                            List<EntityPlayer> listp = world.getEntitiesWithinAABB(
                                    EntityPlayer.class,
                                    AxisAlignedBB.getBoundingBox(x - 6, y - 6, z - 6, x + 6, y + 6, z + 6));
                            for (int j = 0; !(listp.size() == j); j++) listp.get(j)
                                    .addChatMessage(new ChatComponentText("This tool seems to resist the energy"));
                            ritualStone.setActive(false);
                            return;
                        }
                    }

                    if (world.rand.nextInt(10) == 0) {
                        SpellHelper.sendIndexedParticleToAllAround(
                                world,
                                x,
                                y,
                                z,
                                20,
                                world.provider.dimensionId,
                                1,
                                x,
                                y,
                                z);
                    }
                }
            }
        } else {
            ritualStone.setCooldown(ritualStone.getCooldown() - 1);

            if (world.rand.nextInt(30) == 0) {
                int lightningPoint = world.rand.nextInt(8);

                switch (lightningPoint) {
                    case 0:
                        world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z + 0));
                        break;

                    case 1:
                        world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z + 0));
                        break;

                    case 2:
                        world.addWeatherEffect(new EntityLightningBolt(world, x + 0, y, z + 4));
                        break;

                    case 3:
                        world.addWeatherEffect(new EntityLightningBolt(world, x - 0, y, z - 4));
                        break;

                    case 4:
                        world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y + 2, z + 2));
                        break;

                    case 5:
                        world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y + 2, z - 2));
                        break;

                    case 6:
                        world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y + 2, z + 2));
                        break;

                    case 7:
                        world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y + 2, z - 2));
                }

            }

            if (ritualStone.getCooldown() <= 0) {

                ItemStack spawnedItem = stack;
                nbt.setBoolean("STicBM", true);
                int mod = nbt.getCompoundTag("InfiTool").getInteger("Modifiers") + 1;
                nbt.getCompoundTag("InfiTool").setInteger("Modifiers", mod);
                spawnedItem.setTagCompound(nbt);

                if (spawnedItem != null) {
                    EntityItem newItem = new EntityItem(world, x + 0.5D, y + 1, z + 0.5D, spawnedItem);
                    world.spawnEntityInWorld(newItem);
                }
                ritualStone.setVar1(0);
                ritualStone.setActive(false);
            }
        }
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> boundSoulRitual = new ArrayList<RitualComponent>();
        boundSoulRitual.add(new RitualComponent(1, -1, 1, 2));
        boundSoulRitual.add(new RitualComponent(1, -1, -1, 3));
        boundSoulRitual.add(new RitualComponent(-1, -1, 1, 3));
        boundSoulRitual.add(new RitualComponent(-1, -1, -1, 2));

        boundSoulRitual.add(new RitualComponent(2, 0, 2, 0));
        boundSoulRitual.add(new RitualComponent(2, 0, -2, 0));
        boundSoulRitual.add(new RitualComponent(-2, 0, 2, 0));
        boundSoulRitual.add(new RitualComponent(-2, 0, -2, 0));

        boundSoulRitual.add(new RitualComponent(2, 1, 2, 3));
        boundSoulRitual.add(new RitualComponent(2, 1, -2, 2));
        boundSoulRitual.add(new RitualComponent(-2, 1, 2, 2));
        boundSoulRitual.add(new RitualComponent(-2, 1, -2, 3));

        boundSoulRitual.add(new RitualComponent(3, -1, 0, 1));
        boundSoulRitual.add(new RitualComponent(4, -1, 0, 5));
        boundSoulRitual.add(new RitualComponent(4, -1, 1, 4));
        boundSoulRitual.add(new RitualComponent(4, -1, -1, 4));

        boundSoulRitual.add(new RitualComponent(-3, -1, 0, 1));
        boundSoulRitual.add(new RitualComponent(-4, -1, 0, 5));
        boundSoulRitual.add(new RitualComponent(-4, -1, 1, 4));
        boundSoulRitual.add(new RitualComponent(-4, -1, -1, 4));

        boundSoulRitual.add(new RitualComponent(0, -1, 3, 1));
        boundSoulRitual.add(new RitualComponent(0, -1, 4, 5));
        boundSoulRitual.add(new RitualComponent(1, -1, 4, 4));
        boundSoulRitual.add(new RitualComponent(-1, -1, 4, 4));

        boundSoulRitual.add(new RitualComponent(0, -1, -3, 1));
        boundSoulRitual.add(new RitualComponent(0, -1, -4, 5));
        boundSoulRitual.add(new RitualComponent(1, -1, -4, 4));
        boundSoulRitual.add(new RitualComponent(-1, -1, -4, 4));

        return boundSoulRitual;
    }

}
