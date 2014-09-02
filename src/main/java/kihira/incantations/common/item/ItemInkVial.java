package kihira.incantations.common.item;

import kihira.incantations.Incantations;
import net.minecraft.item.Item;

public class ItemInkVial extends Item {

	public ItemInkVial() {
		super();
		setMaxDamage(50);
		setMaxStackSize(1);
		setUnlocalizedName("inkVial");
		setTextureName("incantations:inkVial");
		setCreativeTab(Incantations.tabIncantations);
	}
}
