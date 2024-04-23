package fr.nkri.wizapi.cooldowns;

import fr.nkri.wizapi.utils.times.DurationFormatter;
import org.bukkit.inventory.ItemStack;

public class Cooldown {

    private double delay;
    private ItemStack stack;
    private String cmd;

    public Cooldown(final double delay, final ItemStack stack){
        this.delay = delay;
        this.stack = stack;
    }

    public Cooldown(final double delay, final String cmd){
        this.delay = delay;
        this.cmd = cmd;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}