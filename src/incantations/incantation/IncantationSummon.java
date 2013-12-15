package incantations.incantation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class IncantationSummon extends Incantation {

	private HashMap<String, Class> summonableList = new HashMap<String, Class>();
	private HashMap<String, String> incantationList = new HashMap<String, String>();
	private ArrayList<String> descriptorList = new ArrayList<String>();

	public IncantationSummon() {
		super("summon");
		summonableList.put("zombie", EntityZombie.class);
		summonableList.put("skeleton", EntitySkeleton.class);
		summonableList.put("creeper", EntityCreeper.class);
		summonableList.put("dragon", EntityDragon.class);
		summonableList.put("slime", EntitySlime.class);
		summonableList.put("chicken", EntityChicken.class);
		summonableList.put("sheep", EntitySheep.class);
		summonableList.put("cow", EntityCow.class);

		incantationList.put("zombie", "long gone still lingering nearby body dying soul alive i now command you to rise from the grave");
		incantationList.put("burning zombie", "the sun shines burning the lands the dead alive rise from the grave to bathe in the rays of light");
		incantationList.put("skeleton", "ancient bones cracked and fragile soldier of old i now command you to rise from the grave");
		incantationList.put("creeper", "sulphur and nitrate timed charge malformed pig stalking death i summon you here");
		incantationList.put("dragon", "wings as black as the night eyes a fierce purple scales as hard as diamonds to which no one prevails flying fast through the air breaking mountains towns cities Ruining lives and generations fowl beast of the sky I call you here to a challenge fight me now");
		incantationList.put("slime", "soft and green sticky and foul animated life i summon you here");
		incantationList.put("chicken", "unable to fly feathers so soft eggs all around oh feathered god hear my plea");
		incantationList.put("cow", "skin of leather food for days milking the riches out of this beast");
		incantationList.put("sheep", "wool so soft so light so fluffy i am sleeping tight tonight");

		descriptorList.add("burning");
		descriptorList.add("fast");
	}

	@Override
	public boolean isValidIncantation(String incantation) {
		String[] strings = incantation.split(" ");
		return (incantationList.containsKey(strings[1])) && (incantationList.get(strings[1]).equals(incantation));
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
