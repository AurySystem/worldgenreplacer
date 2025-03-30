package gay.aurum.noisereplacer;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoiseReplacer implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("NoiseReplacer");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Perlin Noise replacement here: your worldgen is now cursed, enjoy the weird terrain!");
	}
}
