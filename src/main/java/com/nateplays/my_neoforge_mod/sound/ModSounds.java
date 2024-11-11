package com.nateplays.my_neoforge_mod.sound;

import com.nateplays.my_neoforge_mod.MyNeoForgeMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
        DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MyNeoForgeMod.MODID);

    public static final Supplier<SoundEvent> FELYNE_AMBIENT = registerSoundEvent("palico/felyne_ambient");
    public static final Supplier<SoundEvent> FELYNE_HURT = registerSoundEvent("palico/felyne_hurt");
    public static final Supplier<SoundEvent> FELYNE_DEATH = registerSoundEvent("palico/felyne_death");





    public static final Supplier<SoundEvent> DISC_HUNTERGOFORTH = registerSoundEvent("music/disc_huntergoforth");
    public static final ResourceKey<JukeboxSong> DISCKEY_HUNTERGOFORTH = createSong("huntergoforth");





    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MyNeoForgeMod.MODID, name));
    }

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MyNeoForgeMod.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
