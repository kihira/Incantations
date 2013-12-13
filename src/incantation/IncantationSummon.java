package incantation;

import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

public class IncantationSummon extends Incantation {

	private ArrayList<String> summonableList = new ArrayList<String>();
	private ArrayList<String> descriptorList = new ArrayList<String>();

	public IncantationSummon() {
		super("summon");
		//TODO complete list
		summonableList.add("zombie");
		summonableList.add("chicken");
		summonableList.add("pig");
		summonableList.add("cow");

		descriptorList.add("burning");
		descriptorList.add("fast");
	}

	@Override
	public int isValidIncantation(String incantation) {
		String[] strings = incantation.split(" ");
		int validWordCount = 0;
		for (int i = 1; i <= strings.length; i++) {
			if (i == 1) {
				if (matchesSummonable(strings[i])) validWordCount++;
			}
			else {
				if (matchesDescriptor(strings[i])) validWordCount++;
			}
		}
		return validWordCount;
	}

	@Override
	public void doIncantation(String incantation, EntityPlayer entityPlayer) {

	}

	@Override
	public void doFailedIncantation(String incantation, EntityPlayer entityPlayer) {

	}

	private boolean matchesSummonable(String word) {
		return summonableList.contains(word.toLowerCase());
	}

	private boolean matchesDescriptor(String word) {
		return descriptorList.contains(word.toLowerCase());
	}
}
