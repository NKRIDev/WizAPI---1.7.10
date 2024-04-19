package fr.nkri.wizapi.cooldowns;

import org.bukkit.entity.Player;

import java.util.*;

public class CooldownManager {

    private static volatile CooldownManager INSTANCE;
    private HashMap<UUID, List<Cooldown>> cooldownMap;
    public CooldownManager() {
        INSTANCE = this;
        this.cooldownMap = new HashMap<>();
    }

    //Supprime tout les cooldowns des joueurs
    public void clearCooldown(){
        this.cooldownMap.clear();
    }

    //Ajoute un cooldown à un joueur (paramètre : joueur, temps du cooldown en seconds, l'itemstack ou la commande: ItemStack, String etc...).
    public void setCooldown(Player player, int seconds, final Object obj) {
        final double delay = System.currentTimeMillis() + (long) seconds * 1000L;
        final Cooldown cooldown = new Cooldown(delay, obj);
        final List<Cooldown> cooldownList = this.cooldownMap.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>());

        for(Cooldown existingCooldown : cooldownList) {
            if(existingCooldown.getObj().equals(obj)) {
                existingCooldown.setTime(delay);
                return;
            }
        }

        cooldownList.add(cooldown);
        this.cooldownMap.put(player.getUniqueId(), cooldownList);
    }

    //False = le joueur n'a pas de cooldown
    public boolean isCooldown(final Player player, final Object obj) {
        final Cooldown cooldown = getCooldown(player, obj);
        return cooldown != null && getCooldownPlayer(player) != null && cooldown.getTime() > System.currentTimeMillis();
    }

    //Retire un cooldown
    public void removeCooldwon(final Player player, final Object obj){
        final Cooldown cooldown = getCooldown(player, obj);

        if(cooldown == null){
            return;
        }
        this.cooldownMap.get(player.getUniqueId()).remove(cooldown);
    }

    //Récupère l'obj cooldown du joueur
    public Cooldown getCooldown(final Player player, final Object obj){
        if(getCooldownPlayer(player) == null){
            return null;
        }

        for(Cooldown cooldown : getCooldownPlayer(player)){
            if(cooldown.getObj().equals(obj)){
                return cooldown;
            }
        }

        return null;
    }

    public List<Cooldown> getCooldownPlayer(final Player player){
        final UUID uuid = player.getUniqueId();
        if(cooldownMap.get(uuid) != null){
            return cooldownMap.get(uuid);
        }

        return null;
    }

    public static CooldownManager getInstance(){
        if(INSTANCE == null){
            synchronized (CooldownManager.class) {

                if(INSTANCE == null){
                    INSTANCE = new CooldownManager();
                }
            }
        }

        return INSTANCE;
    }
}
