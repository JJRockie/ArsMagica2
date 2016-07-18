package am2.affinity.abilities;

import am2.ArsMagica2;
import am2.api.affinity.AbstractAffinityAbility;
import am2.api.affinity.Affinity;
import am2.defs.BindingsDefs;
import am2.extensions.AffinityData;
import am2.extensions.EntityExtension;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

@SuppressWarnings("deprecation")
public class AbilityPhasing extends AbstractAffinityAbility {

	public AbilityPhasing() {
		super(new ResourceLocation("arsmagica2", "phasing"));
	}

	@Override
	protected float getMinimumDepth() {
		return 0.75f;
	}

	@Override
	protected Affinity getAffinity() {
		return Affinity.ENDER;
	}
	
	@Override
	public KeyBinding getKey() {
		return BindingsDefs.ENDER_TP;
	}
	
	@Override
	public boolean canApply(EntityPlayer player) {
		if (super.canApply(player)) {
			if (AffinityData.For(player).getCooldown("EnderTP") <= 0)
				return true;
			else {
				player.addChatMessage(new TextComponentString(I18n.translateToLocal("am2.chat.enderTPOnCooldown")));
				return false;
			}
		}
		return false;
	}
	
	@Override
	public void applyKeyPress(EntityPlayer player) {
		Vec3d playerPos = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		RayTraceResult result = player.worldObj.rayTraceBlocks(playerPos, playerPos.add(new Vec3d(player.getLookVec().xCoord * 32, player.getLookVec().yCoord * 32, player.getLookVec().zCoord * 32)));
		if (result == null)
			result = new RayTraceResult(playerPos.add(new Vec3d(player.getLookVec().xCoord * 32, player.getLookVec().yCoord * 32, player.getLookVec().zCoord * 32)), null);
		EnderTeleportEvent event = new EnderTeleportEvent(player, result.hitVec.xCoord, result.hitVec.yCoord, result.hitVec.zCoord, 0.0f);
		if (MinecraftForge.EVENT_BUS.post(event)) {
			if (!player.worldObj.isRemote)
				player.addChatMessage(new TextComponentString(I18n.translateToLocal("am2.chat.enderTPFailed")));
			return;
		}
		double posY = event.getTargetY();
		while (!player.worldObj.isAirBlock(new BlockPos(event.getTargetX(), posY, event.getTargetZ())) || !player.worldObj.isAirBlock(new BlockPos(event.getTargetX(), posY + 1, event.getTargetZ())))
			posY++;
		player.setPosition(event.getTargetX(), posY, event.getTargetZ());
		player.fallDistance = 0;
		EntityExtension.For(player).setFallProtection(20000);
		AffinityData.For(player).addCooldown("EnderTP", ArsMagica2.config.getEnderAffinityAbilityCooldown());
	}

}