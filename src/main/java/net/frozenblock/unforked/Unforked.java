package net.frozenblock.unforked;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.unforked.mod_compat.UnforkedIntegrations;
import net.frozenblock.unforked.util.UnforkedConstants;
import net.frozenblock.unforked.util.UnforkedUtils;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;

public class Unforked implements ModInitializer {

	@Override
	public void onInitialize() {
		UnforkedUtils.startMeasuring(this);

		UnforkedIntegrations.init();

		UnforkedUtils.stopMeasuring(this);
	}
}
