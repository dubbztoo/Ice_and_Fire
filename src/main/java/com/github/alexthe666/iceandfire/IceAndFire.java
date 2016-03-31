package com.github.alexthe666.iceandfire;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.github.alexthe666.iceandfire.client.GuiHandler;
import com.github.alexthe666.iceandfire.core.ModBlocks;
import com.github.alexthe666.iceandfire.core.ModEntities;
import com.github.alexthe666.iceandfire.core.ModItems;
import com.github.alexthe666.iceandfire.core.ModKeys;
import com.github.alexthe666.iceandfire.core.ModRecipes;
import com.github.alexthe666.iceandfire.event.EventLiving;
import com.github.alexthe666.iceandfire.event.StructureGenerator;
import com.github.alexthe666.iceandfire.message.MessageDragonUpdate;
import com.github.alexthe666.iceandfire.message.MessageModKeys;
import com.github.alexthe666.iceandfire.misc.CreativeTab;

@Mod(modid = IceAndFire.MODID, version = IceAndFire.VERSION)
public class IceAndFire
{

	public static final String MODID = "iceandfire";
	public static final String VERSION = "0.1.4";
	@Instance(value = MODID)
	public static IceAndFire instance;
	public static SimpleNetworkWrapper channel;
	@SidedProxy(clientSide = "com.github.alexthe666.iceandfire.ClientProxy", serverSide = "com.github.alexthe666.iceandfire.CommonProxy")
	public static CommonProxy proxy;
	public static CreativeTabs tab;
    public static DamageSource dragon = (new DamageSource("dragon")).setFireDamage();
    public static DamageSource dragonFire = (new DamageSource("dragonFire")).setFireDamage();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		channel = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		channel.registerMessage(MessageModKeys.class, MessageModKeys.class, 0, Side.SERVER);
		channel.registerMessage(MessageDragonUpdate.class, MessageDragonUpdate.class, 1, Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(new EventLiving());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		tab = new CreativeTab(MODID);
		ModBlocks.init();
		ModItems.init();
		ModRecipes.init();
		ModEntities.init();
		ModKeys.init();
		proxy.render();
		GameRegistry.registerWorldGenerator(new StructureGenerator(), 0);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

	}
}
