package incantations.item;

import incantations.common.Incantations;
import net.minecraft.item.Item;

public class ItemQuill extends Item {

	public ItemQuill(int id) {
		super(id);
		setMaxStackSize(1);
		setMaxDamage(300);
		setUnlocalizedName("quill");
		setTextureName("incantations:feather.png");
		setCreativeTab(Incantations.tabIncantations);
	}
}
