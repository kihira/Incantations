package kihira.incantations.common.item;

import kihira.incantations.Incantations;
import net.minecraft.item.Item;

public class ItemQuill extends Item {

	public ItemQuill() {
		setMaxStackSize(1);
		setMaxDamage(300);
		setUnlocalizedName("quill");
		setTextureName("incantations:quill");
		setCreativeTab(Incantations.tabIncantations);
	}
}
