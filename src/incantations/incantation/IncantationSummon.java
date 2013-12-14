package incantations.incantation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class IncantationSummon extends Incantation {

	private HashMap<String, Class> summonableList = new HashMap<String, Class>();
	private ArrayList<String> descriptorList = new ArrayList<String>();

	public IncantationSummon() {
		super("summon");
		//TODO complete list
		summonableList.put("zombie", EntityZombie.class);
		summonableList.put("chicken", EntityChicken.class);
		summonableList.put("pig", EntityPig.class);
		summonableList.put("cow", EntityCow.class);

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
		String[] words = incantation.split(" ");
		if (matchesSummonable(words[1])) {
			try {
				//Will this work? who knows!
				Constructor constructor = summonableList.get(words[1]).getDeclaredConstructor(World.class);
				EntityLiving entityLiving = (EntityLiving) constructor.newInstance(entityPlayer.worldObj);
				//TODO spawn where player is looking
				entityLiving.setLocationAndAngles(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, 0, 0);
				//Apply descriptors
				if (words.length > 2) {
					for (String word:words) {
						if (word.equals("burning")) entityLiving.setFire(10);
						//TODO fix
						if (word.equals("fast")) entityLiving.setAIMoveSpeed(15f);
					}
				}
				entityPlayer.worldObj.spawnEntityInWorld(entityLiving);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doFailedIncantation(String incantation, EntityPlayer entityPlayer) {

	}

	private boolean matchesSummonable(String word) {
		return summonableList.containsKey(word.toLowerCase());
	}

	private boolean matchesDescriptor(String word) {
		return descriptorList.contains(word.toLowerCase());
	}
}
