package net.frozenblock.unforked;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public class UnforkedLoader {

	void init() {
		System.out.println("This is working");
	}
}
