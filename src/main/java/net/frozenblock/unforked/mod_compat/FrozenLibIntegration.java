package net.frozenblock.unforked.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.unforked.util.UnforkedConstants;
import net.frozenblock.unforked.util.UnforkedUtils;

public class FrozenLibIntegration extends ModIntegration {
	public FrozenLibIntegration() {
		super("frozenlib");
	}

	@Override
	public void init() {
		UnforkedUtils.log("FrozenLib integration ran!", UnforkedConstants.UNSTABLE_LOGGING);
	}
}
