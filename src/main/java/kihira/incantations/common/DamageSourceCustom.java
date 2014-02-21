package kihira.incantations.common;

import net.minecraft.util.DamageSource;

public class DamageSourceCustom extends DamageSource {

	public DamageSourceCustom(String par1Str) {
		super(par1Str);
	}

	public DamageSourceCustom(String par1Str, boolean ignoreArmour) {
		this(par1Str);
		if (ignoreArmour) this.setDamageBypassesArmor();
	}
}
