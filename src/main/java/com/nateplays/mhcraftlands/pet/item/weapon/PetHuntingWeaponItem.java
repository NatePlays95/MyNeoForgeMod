package com.nateplays.mhcraftlands.pet.item.weapon;

import com.nateplays.mhcraftlands.common.weapon.HuntingWeaponItem;
import com.nateplays.mhcraftlands.pet.entity.HuntingBuddyEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;

public class PetHuntingWeaponItem<E extends HuntingBuddyEntity> extends HuntingWeaponItem {
    private final Class<E> entityClass;

    public PetHuntingWeaponItem(Tier tier, Properties properties, Class<E> entityClassIn) {
        super(tier, properties);
        this.entityClass = entityClassIn;
    }

    public static Tool createToolProperties() {
        return HuntingWeaponItem.createToolProperties();
    }

    public static ItemAttributeModifiers createAttributes(Tier tier) {
        return HuntingWeaponItem.createAttributes(tier, 1f, -1.0f, 0.0f);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot equipmentSlot, LivingEntity entity) {
        if (equipmentSlot != EquipmentSlot.MAINHAND) return false;
        if (!this.entityClass.isInstance(entity)) return false;
        return true;
    }
}
