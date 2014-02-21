package kihira.incantations.item;

import kihira.incantations.common.Incantations;
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
