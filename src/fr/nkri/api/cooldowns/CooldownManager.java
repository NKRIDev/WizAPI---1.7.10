package fr.nkri.api.cooldowns;

import fr.nkri.api.utils.times.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CooldownManager {

    private static CooldownManager INSTANCE;
    private HashMap<String, List<fr.nkri.api.cooldowns.Cooldown>> cooldownMap = new HashMap<>();

    //Applique un cooldown à un joueur
    public void setCooldown(final Player player, final int seconds, final ItemStack stack) {
        final double delay = (System.currentTimeMillis() + (seconds * 1000));
        final fr.nkri.api.cooldowns.Cooldown cooldown = new fr.nkri.api.cooldowns.Cooldown(delay, stack);

        //PacketManager.sendPacket(new ServerPacketCooldown(seconds, stack), player);

        if(getCooldwonPlayer(player) == null){
            final List<fr.nkri.api.cooldowns.Cooldown> cooldownList = new ArrayList<>();
            cooldownList.add(cooldown);

            cooldownMap.put(player.getUniqueId().toString(), cooldownList);
            return;
        }

        final List<fr.nkri.api.cooldowns.Cooldown> playerCooldowns = getCooldwonPlayer(player);
        for(Iterator<fr.nkri.api.cooldowns.Cooldown> iterator = playerCooldowns.iterator(); iterator.hasNext();){
            final fr.nkri.api.cooldowns.Cooldown existingCooldown = iterator.next();

            if(existingCooldown.getStack().equals(stack)){
                iterator.remove();
                break;
            }
        }

        playerCooldowns.add(cooldown);
    }

    public void setCooldown(final Player player, final int seconds, final String cmd) {
        final double delay = (System.currentTimeMillis() + (seconds * 1000));
        final fr.nkri.api.cooldowns.Cooldown cooldown = new fr.nkri.api.cooldowns.Cooldown(delay, cmd);

        if(getCooldwonPlayer(player) == null){
            final List<fr.nkri.api.cooldowns.Cooldown> cooldownList = new ArrayList<>();
            cooldownList.add(cooldown);

            cooldownMap.put(player.getUniqueId().toString(), cooldownList);
            return;
        }

        final List<fr.nkri.api.cooldowns.Cooldown> playerCooldowns = getCooldwonPlayer(player);
        for(Iterator<fr.nkri.api.cooldowns.Cooldown> iterator = playerCooldowns.iterator(); iterator.hasNext();){
            final fr.nkri.api.cooldowns.Cooldown existingCooldown = iterator.next();

            if(existingCooldown.getCmd().equals(cmd)){
                iterator.remove();
                break;
            }
        }

        playerCooldowns.add(cooldown);
    }

    //Récupère le cooldown en détails
    public String getCooldownString(final Player player, final ItemStack stack) {

        final fr.nkri.api.cooldowns.Cooldown cooldown = getCooldown(player, stack);
        if(cooldown == null){
            return "";
        }

        final long currentTime = System.currentTimeMillis();
        double remainingSeconds = (cooldown.getDelay() - currentTime) / 1000.0D;

        if (remainingSeconds <= 0.0D) {
            return "0 secondes";
        }

        final int days = (int)(remainingSeconds / TimeUtils.JOUR.getToSecond());
        remainingSeconds -= (days * TimeUtils.JOUR.getToSecond());

        final int hours = (int)(remainingSeconds / TimeUtils.HEURE.getToSecond());
        remainingSeconds -= (hours * TimeUtils.HEURE.getToSecond());

        final int minutes = (int)(remainingSeconds / TimeUtils.MINUTE.getToSecond());
        remainingSeconds -= (minutes * TimeUtils.MINUTE.getToSecond());

        final int seconds = (int)remainingSeconds;
        StringBuilder cooldownString = new StringBuilder();

        if (days > 0) {
            cooldownString.append(days).append(" ").append(TimeUtils.JOUR.getName()).append(", ");
        }

        if (hours > 0) {
            cooldownString.append(hours).append(" ").append(TimeUtils.HEURE.getName()).append(", ");
        }

        if (minutes > 0) {
            cooldownString.append(minutes).append(" ").append(TimeUtils.MINUTE.getName()).append(", ");
        }

        cooldownString.append(seconds).append(" ").append(TimeUtils.SECONDE.getName());
        return cooldownString.toString();
    }

    public String getCooldownString(final Player player, final String str) {

        final fr.nkri.api.cooldowns.Cooldown cooldown = getCooldown(player, str);
        if(cooldown == null){
            return "";
        }

        final long currentTime = System.currentTimeMillis();
        double remainingSeconds = (cooldown.getDelay() - currentTime) / 1000.0D;

        if (remainingSeconds <= 0.0D) {
            return "0 secondes";
        }

        final int days = (int)(remainingSeconds / TimeUtils.JOUR.getToSecond());
        remainingSeconds -= (days * TimeUtils.JOUR.getToSecond());

        final int hours = (int)(remainingSeconds / TimeUtils.HEURE.getToSecond());
        remainingSeconds -= (hours * TimeUtils.HEURE.getToSecond());

        final int minutes = (int)(remainingSeconds / TimeUtils.MINUTE.getToSecond());
        remainingSeconds -= (minutes * TimeUtils.MINUTE.getToSecond());

        final int seconds = (int)remainingSeconds;
        StringBuilder cooldownString = new StringBuilder();

        if (days > 0) {
            cooldownString.append(days).append(" ").append(TimeUtils.JOUR.getName()).append(", ");
        }

        if (hours > 0) {
            cooldownString.append(hours).append(" ").append(TimeUtils.HEURE.getName()).append(", ");
        }

        if (minutes > 0) {
            cooldownString.append(minutes).append(" ").append(TimeUtils.MINUTE.getName()).append(", ");
        }

        cooldownString.append(seconds).append(" ").append(TimeUtils.SECONDE.getName());
        return cooldownString.toString();
    }


    //Vérifie si le joueur à un cooldown
    public boolean checkCooldown(Player player, ItemStack stack) {
        fr.nkri.api.cooldowns.Cooldown cooldown = this.getCooldown(player, stack);
        if (cooldown == null) {
            return true;
        } else {
            long currentTime = System.currentTimeMillis();
            return cooldown.getDelay() <= currentTime;
        }
    }

    public boolean checkCooldown(Player player, String str) {
        fr.nkri.api.cooldowns.Cooldown cooldown = this.getCooldown(player, str);
        if (cooldown == null) {
            return true;
        } else {
            long currentTime = System.currentTimeMillis();
            return cooldown.getDelay() <= currentTime;
        }
    }

    public fr.nkri.api.cooldowns.Cooldown getCooldown(final Player player, final ItemStack stack){
        if(getCooldwonPlayer(player) == null){
            return null;
        }

        for(fr.nkri.api.cooldowns.Cooldown cooldown : getCooldwonPlayer(player)){
            if(cooldown.getStack().getType().equals(stack.getType())){
                return cooldown;
            }
        }

        return null;
    }

    public fr.nkri.api.cooldowns.Cooldown getCooldown(final Player player, final String str){
        if(getCooldwonPlayer(player) == null){
            return null;
        }

        for(fr.nkri.api.cooldowns.Cooldown cooldown : getCooldwonPlayer(player)){
            if(cooldown.getCmd().equals(str)){
                return cooldown;
            }
        }

        return null;
    }

    public List<fr.nkri.api.cooldowns.Cooldown> getCooldwonPlayer(final Player player){
        final String uuid = player.getUniqueId().toString();

        if(cooldownMap.get(uuid) != null){
            return cooldownMap.get(uuid);
        }

        return null;
    }

    public HashMap<String, List<Cooldown>> getCooldownMap() {
        return cooldownMap;
    }

    public static CooldownManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CooldownManager();
        }
        return INSTANCE;
    }
}
