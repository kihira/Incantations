package incantations.incantation;

import incantations.util.LanguageUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class IncantationSummon extends Incantation {

	private final HashMap<String, Class> summonableList = new HashMap<String, Class>();
	//private HashMap<String, String> incantationList = new HashMap<String, String>();
	//private ArrayList<String> descriptorList = new ArrayList<String>();

	public IncantationSummon() {
		super("ah'zivuud");
		summonableList.put("endroquer", EntityZombie.class);
		summonableList.put("kaskal", EntitySkeleton.class);
		summonableList.put("xyzaah'il", EntityCreeper.class);
		summonableList.put("dragon", EntityDragon.class);
		summonableList.put("saklida'es", EntitySlime.class);
		summonableList.put("cuckoo", EntityChicken.class);
		summonableList.put("sode'looni", EntitySheep.class);
		summonableList.put("meh'lir", EntityCow.class);

		/*
		incantationList.put("endroquer", "senth tiled, calter hestitalet nestol, licham mortet, savis viseris, ai poselis nu tes ta elemar af le mortular.");
		incantationList.put("endroquer tooret", "le lumer sines, tooret le terer, le morteris viseris, poselis elemar af le mortular ta aserel un le lumentes at lumis.");
		incantationList.put("tooret endroquer", "le lumer sines, tooret le terer, le morteris viseris, poselis elemar af le mortular ta aserel un le lumentes at lumis.");
		incantationList.put("kaskal", "kask martes, kraskt ren metir, waskor at mart, ai poselis nu tes ta elemar af le mortular.");
		incantationList.put("xyzaah'il", "salmec ren nostrinum, mesuid faset, destolix posk'ilar, itrelet mortu, ai ah'zivuud tes oy.");
		incantationList.put("dragon", "wings as black as the night eyes a fierce purple scales as hard as diamonds to which no one prevails flying fast through the air breaking mountains towns cities Ruining lives and generations fowl beast of the sky I call you here to a challenge fight me now");
		incantationList.put("saklida'es", "fehah ren cregret, quelanuil ren purtin, motspu'renah vis'muh, ai ah'zivuud tes oy.");
		incantationList.put("chicken", "unable to fly feathers so soft eggs all around oh feathered god hear my plea");
		incantationList.put("cow", "skin of leather food for days milking the riches out of this beast");
		incantationList.put("sheep", "wool so soft so light so fluffy i am sleeping tight tonight");
		*/
	}

	@Override
	public int isValidIncantation(String incantation, EntityPlayer entityPlayer) {
		incantation = LanguageUtil.cleanIncantation(incantation);
		String[] strings = incantation.split(" ");
		int validCount = 1;
		if (summonableList.containsKey(strings[1])) validCount++;
		for (int i = 1; i < strings.length; i++) {
			if (strings[i].equals("tooret") || strings[i].equals("spooh")) validCount++;
			else break;
		}
		return validCount;
	}

	@Override
	public void doIncantation(String incantation, EntityPlayer entityPlayer) {
		incantation = LanguageUtil.cleanIncantation(incantation);
		String[] words = incantation.split(" ");
		if (matchesSummonable(words[1])) {
			int failChance = 10;
			if (words[1].equals("dragon")) failChance = 990;
			if (this.random.nextInt(1001) > failChance) {
				try {
					Constructor constructor = summonableList.get(words[1]).getDeclaredConstructor(World.class);
					EntityLiving entityLiving = (EntityLiving) constructor.newInstance(entityPlayer.worldObj);
					MovingObjectPosition movingObjectPosition = getBlockLookAt(entityPlayer, 10);
					if (movingObjectPosition == null) entityLiving.setLocationAndAngles(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, 0, 0);
					else entityLiving.setLocationAndAngles(movingObjectPosition.blockX, movingObjectPosition.blockY + 1, movingObjectPosition.blockZ, 0, 0);
					//Apply descriptors
					if (words.length > 2) {
						for (String word:words) {
							if (word.equals("tooret")) entityLiving.setFire(5);
							if (word.equals("spooh")) entityLiving.setAIMoveSpeed(15f);
						}
					}
					entityPlayer.worldObj.spawnEntityInWorld(entityLiving);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText("§4You feel the scroll surge with power beyond you control!"));
				entityPlayer.attackEntityFrom(DamageSource.magic, 6);
			}
		}
	}

	@Override
	public void doFailedIncantation(String incantation, int validWordCount, EntityPlayer entityPlayer) {

	}

	public static MovingObjectPosition getBlockLookAt(EntityPlayer player, double maxBlockDistance) {
		Vec3 vec3 = player.worldObj.getWorldVec3Pool().getVecFromPool(player.posX, player.posY + (player.worldObj.isRemote ? 0.0D : (player.getEyeHeight() - 0.09D)), player.posZ);
		Vec3 vec31 = player.getLookVec();
		Vec3 vec32 = vec3.addVector(vec31.xCoord * maxBlockDistance, vec31.yCoord * maxBlockDistance, vec31.zCoord * maxBlockDistance);
		return player.worldObj.clip(vec3, vec32);
	}

	private boolean matchesSummonable(String word) {
		return summonableList.containsKey(word.toLowerCase());
	}
}
