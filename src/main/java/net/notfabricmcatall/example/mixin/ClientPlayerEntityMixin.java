package net.notfabricmcatall.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.notfabricmcatall.example.ChromaHudFabric;
import net.minecraft.entity.player.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
	private void sendChatMessage(String string, CallbackInfo info) {
		if ("/chromahud".equals(string)) {
            ChromaHudFabric.chromahud.commandchromahud.onExecute();
            info.cancel();
        }
	}
}