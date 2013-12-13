package common;

import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabIncantations extends CreativeTabs {
	public CreativeTabIncantations() {
		super("Incantations");
	}

	public int getTabIconItemIndex(){
		return Incantations.config.writingDeskID;
	}
}
