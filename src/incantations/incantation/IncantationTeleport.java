package incantations.incantation;

import incantations.util.LanguageUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.DimensionManager;

public class IncantationTeleport extends Incantation {

	public IncantationTeleport() {
		super("letivo-est");
		//Teleport x y z (dimension)
	}

	@Override
	public int isValidIncantation(String incantation, EntityPlayer entityPlayer) {
		incantation = LanguageUtil.cleanIncantation(incantation);
		String[] strings = incantation.split(" ");
		int validCount = 1;
		for (int i = 1; i < strings.length; i++) {
			if (i > 3) break;
			try {
				Integer.parseInt(strings[i]);
				validCount++;
			}
			catch (NumberFormatException e) {
				break;
			}
		}
		return validCount;
	}

	@Override
	public void doIncantation(String incantation, EntityPlayer entityPlayer) {
		incantation = LanguageUtil.cleanIncantation(incantation);
		String[] strings = incantation.split(" ");
		int[] stuff = new int[3];
		for (int i = 0; i < strings.length; i++) {
			if (i > 4) break;
			try {
				stuff[i] = Integer.parseInt(strings[i-1]);
			}
			catch (NumberFormatException e) {
				break;
			}
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityPlayer;
		entityPlayerMP.playerNetServerHandler.setPlayerLocation(stuff[0], stuff[1], stuff[2], 0, 0);
		}
	}

	@Override
	public void doFailedIncantation(String incantation, int validWordCount, EntityPlayer entityPlayer) {

	}
}
