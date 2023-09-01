package gay.aurum.noisereplacer;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoiseReplacer implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("NoiseReplacer");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Perlin Noise replacement here: your worldgen is now cursed, enjoy the weird terrain!");
	}
}
