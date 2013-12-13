package item;

import net.minecraft.item.Item;

public class ItemResearchNotes extends Item {
	public ItemResearchNotes(int id) {
		super(id);
		setMaxDamage(1);
		setMaxStackSize(1);
		setUnlocalizedName("researchNotes");
	}
}
