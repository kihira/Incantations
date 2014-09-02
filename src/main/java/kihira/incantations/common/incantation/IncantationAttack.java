package kihira.incantations.common.incantation;

import kihira.incantations.common.DamageSourceCustom;
import kihira.incantations.util.LanguageUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IncantationAttack extends Incantation {

	private final HashMap<String, Class> targetableList = new HashMap<String, Class>();
	private final ArrayList<String> attackList = new ArrayList<String>();

	public IncantationAttack() {
		super("mortka");
		targetableList.put("endroquer", EntityZombie.class);
		targetableList.put("kaskal", EntitySkeleton.class);
		targetableList.put("xyzaah'il", EntityCreeper.class);
		targetableList.put("dragon", EntityDragon.class);
		targetableList.put("saklida'es", EntitySlime.class);
		targetableList.put("cuckoo", EntityChicken.class);
		targetableList.put("sode'looni", EntitySheep.class);
		targetableList.put("meh'lir", EntityCow.class);
		targetableList.put("ich'uun", EntityPig.class);
		targetableList.put("elemor", EntityBlaze.class);
		targetableList.put("rukuhan", EntitySpider.class);
		targetableList.put("posk'ilar", EntityEnderman.class);
		targetableList.put("elem'nostrak", EntityGhast.class);
		targetableList.put("tuhunkt'mart", EntityIronGolem.class);
		targetableList.put("saklida'toor", EntityMagmaCube.class);
		targetableList.put("ich'endroquer", EntityPigZombie.class);

		attackList.add("toor"); //Burn
		attackList.add("mortor"); //Smite
		attackList.add("rah'linaas"); //Pulverize
		attackList.add("tkash"); //Slice
	}

	@Override
	public int isValidIncantation(String incantation, EntityPlayer entityPlayer) {
		incantation = LanguageUtil.cleanIncantation(incantation);
		String[] strings = incantation.split(" ");
		int validCount = 1;
		//if (entityPlayer.worldObj.getPlayerEntityByName(strings[1]) != null) validCount++;
		//TODO Does minecraft allow for multiple usernames that are the same but different case?
		boolean flag = false;
		for (int i = 0; i < entityPlayer.worldObj.playerEntities.size(); ++i) {
			if (strings[1].equalsIgnoreCase((((EntityPlayer) entityPlayer.worldObj.playerEntities.get(i)).getCommandSenderName()))) {
				validCount++;
				flag = true;
				break;
			}
		}
		if ((!flag) && (targetableList.containsKey(strings[1]))) validCount++;
		else if (flag) return validCount;
		for (int i = 2; i < strings.length; i++) {
			if (attackList.contains(strings[i])) validCount++;
			else break;
		}
		return validCount;
	}

	@Override
	public void doIncantation(String incantation, EntityPlayer entityPlayer) {
		incantation = LanguageUtil.cleanIncantation(incantation);
		String[] strings = incantation.split(" ");
		EntityLivingBase target = null;
		if (!targetableList.containsKey(strings[1])) {
			//Might be player target
			//target = entityPlayer.worldObj.getPlayerEntityByName(strings[1]);
			for (int i = 0; i < entityPlayer.worldObj.playerEntities.size(); ++i) {
				if (strings[1].equalsIgnoreCase((((EntityPlayer) entityPlayer.worldObj.playerEntities.get(i)).getCommandSenderName()))) {
					target = (EntityLivingBase) entityPlayer.worldObj.playerEntities.get(i);
					break;
				}
			}
		}
		else {
			List possibleTargets = entityPlayer.worldObj.getEntitiesWithinAABB(targetableList.get(strings[1]), AxisAlignedBB.getBoundingBox(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, entityPlayer.posX + 1, entityPlayer.posY + 1, entityPlayer.posZ + 1).expand(20, 20 ,20));
			if (possibleTargets.size() > 0) {
			target = (EntityLivingBase) possibleTargets.get(random.nextInt(possibleTargets.size()));
			}
		}
		if (target != null) {
			for (int i = 2; i < strings.length; i++) {
				if (strings[i].equals("toor")) target.setFire(5);
				if (strings[i].equals("mortor") && ((target instanceof EntityZombie) || (target instanceof EntitySkeleton))) target.attackEntityFrom(DamageSource.magic, 20);
				if (strings[i].equals("rah'linaas")) target.attackEntityFrom(new DamageSourceCustom("pulverize").setMagicDamage(), 10);
				if (strings[i].equals("tkash")) target.attackEntityFrom(new DamageSourceCustom("slice", true).setMagicDamage(), 3);
			}
		}
		else entityPlayer.addChatComponentMessage(new ChatComponentText("\u00a74Your scroll fizzles away as it finds nothing to attack"));
	}

	@Override
	public void doFailedIncantation(String incantation, int validWordCount, EntityPlayer entityPlayer) {
		entityPlayer.attackEntityFrom(DamageSource.magic, 10);
	}
}
