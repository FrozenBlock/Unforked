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
		applyDataFixes(UnforkedConstants.MOD_CONTAINER);

		UnforkedIntegrations.init();

		UnforkedUtils.stopMeasuring(this);
	}

	private static void applyDataFixes(final @NotNull ModContainer mod) {
		UnforkedUtils.log("Applying DataFixes for Unforked with Data Version " + UnforkedConstants.DATA_VERSION, true);

		var builder = new QuiltDataFixerBuilder(UnforkedConstants.DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		UnforkedUtils.log("DataFixes for Unforked have been applied", true);
	}
}
