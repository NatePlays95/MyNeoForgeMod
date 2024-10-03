package com.nateplays.my_neoforge_mod.datagen;

import com.nateplays.my_neoforge_mod.MyNeoForgeMod;
import com.nateplays.my_neoforge_mod.block.ModBlocks;
import com.nateplays.my_neoforge_mod.item.ModItems;
import com.nateplays.my_neoforge_mod.item.armor.ModArmorItems;
import com.nateplays.my_neoforge_mod.item.weapons.SwordAndShieldItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MyNeoForgeMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.EARTH_CRYSTAL.get());
        basicItem(ModItems.MALACHITE_CHUNK.get());
        basicItem(ModItems.MALACHITE_INGOT.get());
        basicItem(ModItems.NULBERRY.get());
//        basicItem(ModItems.CHISEL.get()); //custom model in assets folder

        buttonItem(ModBlocks.MACHALITE_BUTTON, ModBlocks.MACHALITE_BLOCK);
        fenceItem(ModBlocks.MACHALITE_FENCE, ModBlocks.MACHALITE_BLOCK);
        wallItem(ModBlocks.MACHALITE_WALL, ModBlocks.MACHALITE_BLOCK);

        basicItem(ModBlocks.MACHALITE_DOOR.asItem());

        handheldItem(ModItems.MACHALITE_SWORD);
        handheldItem(ModItems.MACHALITE_PICKAXE);
        handheldItem(ModItems.MACHALITE_SHOVEL);
        handheldItem(ModItems.MACHALITE_AXE);
        handheldItem(ModItems.MACHALITE_HOE);

        handheldItem(ModItems.MACHALITE_HAMMER);

        snsItem(ModItems.MACHALITE_SNS);

        withExistingParent(ModItems.MOSSWINE_SPAWN_EGG.getRegisteredName(), mcLoc("item/template_spawn_egg"));

        armorItem(ModArmorItems.F_ACORN_HELM);
        armorItem(ModArmorItems.F_ACORN_MAIL);
        armorItem(ModArmorItems.F_BONE_HELM);
        armorItem(ModArmorItems.F_BONE_MAIL);
    }


    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(MyNeoForgeMod.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(MyNeoForgeMod.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(MyNeoForgeMod.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(), ResourceLocation.parse("item/handheld"))
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder armorItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(), ResourceLocation.parse("item/handheld"))
                .texture("layer0", modLoc("item/armor/" + item.getId().getPath()));
    }

    private ItemModelBuilder snsItem(DeferredItem<SwordAndShieldItem> item) {
        String itemId = item.getId().getPath();
        String modelPath = "item/weapon/";

        withExistingParent(modelPath + itemId + "_mainhand_blocking", modLoc("item/weapon/sns_shield_blocking"))
//                .texture("layer0", modLoc("item/" + itemId + "_shield"));
                .texture("layer0", modLoc("item/" + itemId + "_shield"));

        withExistingParent(modelPath + itemId + "_mainhand", modLoc("item/weapon/sns_shield"))
                .texture("layer0", modLoc("item/" + itemId + "_shield"));
//                .override()
//                    .predicate(mcLoc("blocking"), 1.0F)
//                    .model(new ModelFile.UncheckedModelFile(modLoc(modelPath + itemId + "_mainhand_blocking")))
//                    .end();

        withExistingParent(modelPath + itemId + "_offhand", ResourceLocation.parse("item/handheld"))
                .texture("layer0", modLoc("item/" + itemId));

        return withExistingParent(itemId, ResourceLocation.parse("item/handheld"))
                .texture("layer0", modLoc("item/" + itemId))
//                .override()
//                    .predicate(mcLoc("blocking"), 1.0F)
//                    .model(new ModelFile.UncheckedModelFile(modLoc(modelPath + itemId + "_mainhand_blocking")))
//                    .end();

                .override()
                    .predicate(modLoc("hand"), 1.0F)
                    .model(new ModelFile.UncheckedModelFile(modLoc(modelPath + itemId + "_mainhand")))
                    .end()
                .override()
                    .predicate(modLoc("hand"), 2.0F)
                    .model(new ModelFile.UncheckedModelFile(modLoc(modelPath + itemId + "_offhand")))
                    .end()
                .override()
                    .predicate(modLoc("hand"), 3.0F)
                    .model(new ModelFile.UncheckedModelFile(modLoc(modelPath + itemId + "_mainhand_blocking")))
                    .end();
    }
}
