package incantations.incantation;

import incantations.util.LanguageUtil;
import net.minecraft.entity.player.EntityPlayer;

public class IncantationAttack extends Incantation {

	public IncantationAttack() {
		super("mortka");
	}

	@Override
	public int isValidIncantation(String incantation, EntityPlayer entityPlayer) {
		incantation = LanguageUtil.cleanIncantation(incantation);
		String[] strings = incantation.split(" ");
		int validCount = 1;
		if (entityPlayer.worldObj.getPlayerEntityByName(strings[1]) != null) validCount++;
		for (int i = 1; i < strings.length; i++) {
			if (strings[i].equals("tooret") || strings[i].equals("spooh")) validCount++;
			else break;
		}
		return validCount;
	}

	@Override
	public void doIncantation(String incantation, EntityPlayer entityPlayer) {

	}

	@Override
	public void doFailedIncantation(String incantation, int validWordCount, EntityPlayer entityPlayer) {

	}
}
