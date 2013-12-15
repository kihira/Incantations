package incantations.item;

import incantations.common.Incantations;
import net.minecraft.item.Item;

public class ItemWritingTools extends Item {

	public ItemWritingTools(int id) {
		super(id);
		setMaxDamage(50);
		setMaxStackSize(1);
		setUnlocalizedName("writingTools");
		setCreativeTab(Incantations.tabIncantations);
	}
}
