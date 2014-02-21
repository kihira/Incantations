package kihira.incantations.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabIncantations extends CreativeTabs {
	public CreativeTabIncantations() {
		super("Incantations");
	}

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Incantations.blockWritingDesk);
    }
}
