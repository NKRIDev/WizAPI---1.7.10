package fr.nkri.api.utils.modules;

import fr.nkri.api.WizAPI;
import fr.nkri.api.cmds.ICommand;
import fr.nkri.api.packets.WizPacket;
import org.bukkit.event.Listener;

public abstract class Module {

    private final WizAPI main;
    private boolean isEnable;
    private final String name;

    /**
     * @param main plugin main class
     * @param name module name
     */
    public Module(final WizAPI main, final String name) {
        this.main = main;
        this.name = name;
        this.isEnable = true;
    }

    //register an cmd
    public void registerCommand(final ICommand iCommand){
        main.registerCommand(iCommand);
    }

    //register a listener
    public void registerListener(final Listener listener){
        main.registerListeners(listener);
    }

    //register packets
    public void registerPacket(final WizPacket packet, final int packetId){
        main.registerPacket(packet, packetId);
    }

    //save data
    public abstract void load();
    public abstract void save();
    //save data

    //Getter, Setter
    public String getName() {
        return name;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
