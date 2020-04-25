package net.notfabricmcatall.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cc.hyperium.mods.sk1ercommon.ResolutionUtil;
import io.github.CoolMineman.NextTickDisplayer;

import org.spongepowered.asm.mixin.injection.At;

import net.notfabricmcatall.example.ExampleMod;
import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class ToBeNamedOne {
    @Inject(method = "render", at = @At(value = "TAIL"))
    public void render(CallbackInfo ci) {
        NextTickDisplayer.tick();
        ExampleMod.elementrenderer.onRenderTick();
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void tick(CallbackInfo ci) {
        ResolutionUtil.tick();
        ExampleMod.elementrenderer.tick();
    }
}