package net.frozenblock.unforked;


import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;

public final class UnforkedLanguageAdapter implements LanguageAdapter {
	@Override
	public native <T> T create(ModContainer mod, String value, Class<T> type);

	static {
		UnforkedLoader.init();
	}
}
