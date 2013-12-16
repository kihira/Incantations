package incantations.incantation;

import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Random;

public abstract class Incantation {

	public static final HashMap<String, Incantation> incantationHashMap = new HashMap<String, Incantation>();

	protected Random random = new Random();

	/***
	 * Create a new instance of this for each incantations.incantation. The word should be the first word of the scroll.
	 * TODO Make it so incantation words don't have to be the first word of a scroll. Possible issue with collisions though
	 * @param word
	 */
	public Incantation(String word) {
		if (incantationHashMap.get(word.toLowerCase()) == null) {
			incantationHashMap.put(word.toLowerCase(), this);
		}
		else throw new IllegalArgumentException("The incantations.incantation word " + word + " is already registered!");
	}

	/***
	 * The incantations.incantation passed to this is the translated version of the scroll. This function will split it at spaces and then return to what word number it is valid.
	 * First word (including the actual incantations.incantation word itself) is 0, second is 1 etc.
	 * @param incantation The incantations.incantation to be checked
	 * @return What word count the incantations.incantation is valid
	 */
	public abstract int isValidIncantation(String incantation, EntityPlayer entityPlayer);

	/***
	 * When the scroll has finished being casted/read, this is called.
	 * @param incantation A valid incantations.incantation
	 * @param entityPlayer The player casting the scroll
	 */
	public abstract void doIncantation(String incantation, EntityPlayer entityPlayer);

	/***
	 * Called if the incantations.incantation isn't valid. Maybe do bad stuff here?
	 * @param incantation
	 * @param entityPlayer
	 */
	public abstract void doFailedIncantation(String incantation, int validWordCount, EntityPlayer entityPlayer);

}
