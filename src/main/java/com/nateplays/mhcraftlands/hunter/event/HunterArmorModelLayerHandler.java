package com.nateplays.mhcraftlands.hunter.event;

import com.nateplays.mhcraftlands.MHMod;
import com.nateplays.mhcraftlands.common.client.rendering.BaseHuntingArmorModel;
import com.nateplays.mhcraftlands.entity.client.ModModelLayers;
import com.nateplays.mhcraftlands.hunter.armor.model.ChaosPlateAModel;
import com.nateplays.mhcraftlands.hunter.armor.model.ChaosPlateBModel;
import com.nateplays.mhcraftlands.hunter.armor.model.ChaoshroomHelmetAModel;
import com.nateplays.mhcraftlands.hunter.armor.model.ChaoshroomHelmetBModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@EventBusSubscriber(modid = MHMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HunterArmorModelLayerHandler {

    @SubscribeEvent
    public static <T extends BaseHuntingArmorModel<?>> void registerHunterArmorLayers(EntityRenderersEvent.RegisterLayerDefinitions event) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException {

        event.registerLayerDefinition(BaseHuntingArmorModel.DEFAULT_HUNTING_ARMOR_LAYER,
                () -> LayerDefinition.create(BaseHuntingArmorModel.createMesh(new CubeDeformation(0.3F), 0.0f), 64, 32));


        List<Class<?>> modelClasses = List.of(
                ChaoshroomHelmetAModel.class,
                ChaoshroomHelmetBModel.class,
                ChaosPlateAModel.class,
                ChaosPlateBModel.class
        );

        for (Class<?> modelClass : modelClasses) {
            Field layerLocationField = modelClass.getField("LAYER_LOCATION");
            Method bodyLayerMethod = modelClass.getMethod("createBodyLayer");

            event.registerLayerDefinition((ModelLayerLocation) layerLocationField.get(null),
                () -> {
                    try {
                        return (LayerDefinition) bodyLayerMethod.invoke(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
        }

//        event.registerLayerDefinition(ChaoshroomHelmetAModel.LAYER_LOCATION, ChaoshroomHelmetAModel::createBodyLayer);
//        event.registerLayerDefinition(ChaoshroomHelmetBModel.LAYER_LOCATION, ChaoshroomHelmetBModel::createBodyLayer);

    }
}
