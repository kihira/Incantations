package incantation;

import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public abstract class Incantation {

	private HashMap<String, Incantation> incantationHashMap = new HashMap<String, Incantation>();

	/***
	 * Create a new instance of this for each incantation. The word should be the first word of the scroll.
	 * TODO Make it so incantation words don't have to be the first word of a scroll. Possible issue with collisions though
	 * @param word
	 */
	public Incantation(String word) {
		if (incantationHashMap.get(word.toLowerCase()) == null) {
			incantationHashMap.put(word.toLowerCase(), this);
		}
		else throw new IllegalArgumentException("The incantation word " + word + " is already registered!");
	}

	/***
	 * The incantation passed to this is the translated version of the scroll. This function will split it at spaces and then return to what word number it is valid.
	 * First word (including the actual incantation word itself) is 0, second is 1 etc.
	 * @param incantation The incantation to be checked
	 * @return What word count the incantation is valid
	 */
	public abstract int isValidIncantation(String incantation);

	/***
	 * When the scroll has finished being casted/read, this is called.
	 * @param incantation A valid incantation
	 * @param entityPlayer The player casting the scroll
	 */
	public abstract void doIncantation(String incantation, EntityPlayer entityPlayer);

	/***
	 * Called if the incantation isn't valid. Maybe do bad stuff here?
	 * @param incantation
	 * @param entityPlayer
	 */
	public abstract void doFailedIncantation(String incantation, EntityPlayer entityPlayer);

}
