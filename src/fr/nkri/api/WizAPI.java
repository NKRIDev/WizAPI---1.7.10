package fr.nkri.api;

import fr.nkri.api.cmds.CommandFramework;
import fr.nkri.api.guis.WizInvManager;
import fr.nkri.api.logs.Logs;
import fr.nkri.api.logs.enums.LogsType;
import fr.nkri.api.packets.PacketManager;
import fr.nkri.api.packets.WizPacket;
import fr.nkri.api.utils.json.adapter.ItemStackAdapter;
import fr.nkri.api.utils.json.adapter.LocationAdapter;
import fr.nkri.api.utils.modules.Module;
import fr.nkri.api.utils.modules.ModuleManager;
import fr.nkri.api.utils.modules.cmds.CommandModule;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;

import java.util.List;

/*
Developed by NKRI: 24/11/2023
Update: 04/11/24 (add module system)
 */

public class WizAPI extends JavaPlugin {

    private static WizAPI INSTANCE;
    private CommandFramework commandFramework;
    private Gson gson;
    private PacketManager packetManager;

    //module management
    private ModuleManager moduleManager;

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

        if(this.moduleManager != null){
            this.moduleManager.saveAll();
        }

        super.onDisable();
    }

    public void registerCommand(final Object object){
        this.commandFramework.registerCommands(object);
    }

    public void registerListeners(final Listener listener){
        final PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(listener, this);
    }

    //module
    public void registerModules(final List<Module> modules){
        this.moduleManager = new ModuleManager(this);

        for(Module module : modules){
            this.moduleManager.registerModule(module);
        }

        this.moduleManager.loadAll();
        registerCommand(new CommandModule(moduleManager));
    }
    //module

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