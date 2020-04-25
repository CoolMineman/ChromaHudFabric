package net.notfabricmcatall.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.notfabricmcatall.example.ExampleMod;
import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class CommandMixin {
    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
	private void sendChatMessage(String string, CallbackInfo info) {
		if ("/chromahud".equals(string)) {
            ExampleMod.chromahud.commandchromahud.onExecute();
            info.cancel();
        }
	}
}