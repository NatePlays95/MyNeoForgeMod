package com.nateplays.my_neoforge_mod.item.weapons;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class SwordAndShieldItem extends HuntingWeaponItem{

    static final Logger LOGGER = LoggerFactory.getLogger(SwordAndShieldItem.class);

    public SwordAndShieldItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    public static ItemAttributeModifiers createAttributes(Tier tier) {
        return HuntingWeaponItem.createAttributes(tier, getAttackDamageMultiplier(), -2.5f, -0.3f);
    }

    public static float getAttackDamageMultiplier() {
        return 1.4F;
    }

    public float getUseItemSlowdown(Player player, ItemStack stack) {
        return 0.5F;
    }


    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
//        LOGGER.debug(livingEntity.toString() + livingEntity.getUseItem().toString() + String.valueOf(livingEntity.isUsingItem()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        // Check if the player's attack cooldown is fully reset

        if (player.getAttackStrengthScale(0.0F) >= 1.0F) {
            // Allow blocking
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        } else {
            // If the cooldown is not fully reset, do not allow blocking
            player.stopUsingItem();
            player.displayClientMessage(Component.literal("Attack cooldown not reset!"), true);
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility)
                || ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player) {
            forceServerSwing(player, InteractionHand.OFF_HAND);
            return true;
        }
        return super.onEntitySwing(stack, entity);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
