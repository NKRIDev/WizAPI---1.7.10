package fr.nkri.wizapi;

import fr.nkri.wizapi.cmds.CommandFramework;
import fr.nkri.wizapi.guis.WizInvManager;
import fr.nkri.wizapi.logs.Logs;
import fr.nkri.wizapi.logs.enums.LogsType;
import fr.nkri.wizapi.packets.PacketManager;
import fr.nkri.wizapi.packets.WizPacket;
import fr.nkri.wizapi.utils.json.adapter.ItemStackAdapter;
import fr.nkri.wizapi.utils.json.adapter.LocationAdapter;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;

/*
Developed by NKRI: 24/11/2023
 */

public class WizAPI extends JavaPlugin {

    private static  WizAPI INSTANCE;
    private CommandFramework commandFramework;
    private Gson gson;
    private PacketManager packetManager;

    public WizAPI(){}

    @Override
    public void onEnable() {
        INSTANCE = this;
        Logs.sendLog("WizAPI", "Starting... Please wait for the initialization process to complete.", LogsType.INFO);
        this.commandFramework = new CommandFramework(this);
        this.gson = getGsonBuilder().create();
        this.packetManager = new PacketManager();
        WizInvManager.register(this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        super.onEnable();
    }

    public void registerPacket(final WizPacket wizPacket, final int id){
        packetManager.registerPacket(wizPacket, id);
    }

    public void registerPackets(){
        packetManager.registerPackets();
    }

    @Override
    public void onDisable() {
        Logs.sendLog("WizAPI", "Good Bye ! Thanks for using WizAPI, plugin being unloaded.", LogsType.INFO);
        super.onDisable();
    }

    public void registerCommand(final Object object){
        this.commandFramework.registerCommands(object);
    }

    public void registerListeners(final Listener listener){
        final PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(listener, this);
    }

    //Json
    public GsonBuilder getGsonBuilder(){
        return new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Location.class, new LocationAdapter())
                .registerTypeAdapter(ItemStack.class, new ItemStackAdapter())
                .serializeNulls().disableHtmlEscaping();
    }

    public String serialize(final Object obj){
        return this.gson.toJson(obj);
    }

    public <T> T deserialize(final String json, final Class<T> type){
        return this.gson.fromJson(json, type);
    }

    public Gson getGson() {
        return gson;
    }

    public static WizAPI getInstance() {
        return INSTANCE;
    }
}