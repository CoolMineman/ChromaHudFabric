package net.notfabricmcatall.example;

import cc.hyperium.mods.chromahud.ChromaHUD;
import cc.hyperium.mods.chromahud.ElementRenderer;
import net.fabricmc.api.ModInitializer;

public class ChromaHudFabric implements ModInitializer {
	public static ChromaHUD chromahud;
	public static ElementRenderer elementrenderer;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
		chromahud = new ChromaHUD();
		chromahud.init();
		
		elementrenderer  = new ElementRenderer(chromahud);

	}
}
