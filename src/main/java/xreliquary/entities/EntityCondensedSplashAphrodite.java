package xreliquary.entities;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityCondensedSplashAphrodite extends EntityThrownPotion {
	public EntityCondensedSplashAphrodite(World par1World) {
		super(par1World);
	}

	public EntityCondensedSplashAphrodite(World par1World, EntityPlayer par2EntityPlayer) {
		super(par1World, par2EntityPlayer);
	}

	@SideOnly(Side.CLIENT)
	public EntityCondensedSplashAphrodite(World par1World, double par2, double par4, double par6, int par8) {
		this(par1World, par2, par4, par6);
	}

	public EntityCondensedSplashAphrodite(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

    @Override
    boolean hasLivingEntityEffect() {
        return true;
    }

    @Override
    void doGroundSplashEffect() {
        //none
    }

    @Override
    void doLivingSplashEffect(EntityLiving el) {
        if (!(el instanceof EntityAnimal)) return;
        EntityAnimal e = (EntityAnimal) el;
        if (e == null || e.getGrowingAge() != 0 || !(this.getThrower() instanceof EntityPlayer)) return;
        e.func_146082_f((EntityPlayer)this.getThrower());
        for (int var3 = 0; var3 < 7; ++var3) {
            double var4 = rand.nextGaussian() * 0.02D;
            double var6 = rand.nextGaussian() * 0.02D;
            double var8 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle("heart", e.posX + rand.nextFloat() * e.width * 2.0F - e.width, e.posY + 0.5D + rand.nextFloat() * e.height, e.posZ + rand.nextFloat() * e.width * 2.0F - e.width, var4, var6, var8);
        }
    }

    @Override
    float getRed() {
        return 1F;
    }

    @Override
    float getGreen() {
        return 0;
    }

    @Override
    float getBlue() {
        return 0;
    }
}
