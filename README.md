# WizAPI
This is the Minecraft API (version: 1.8.8)
If you use it, and need help ask NKRI.

## What is WizAPI?

WizAPI is a **minecraft api** mainly intended for *version 1.8.8*. This API contains already developed systems such as: titles, cooldowns, database management (MySQL). This little API simplifies the creation of plugins, you will no longer need to copy/paste your cooldown systems for example !

## How it works ?
Do you want to integrate WizAPI into your project? That's great ! You will see, it's very simple:

1. Download the .jar archive by clicking on the green *“<> Code”* button.
2. Add the *"WizAPI.jar"* file to your project as a library.
3. Also make sure to include the *"WizAPI.jar"* file in the plugins folder of your

## Documentation

**→ Cooldown:**
To put a cooldown on a player, you must enter the player, the duration of the cooldown in seconds and an object (this can be a String, an ItemStack, etc.). Don't forget to call the instance of the CooldownManager class. Here is an example where we put a 100 second cooldown on players who eat a golden apple.

    public class CooldownListeners implements Listener {
    
        private final CooldownManager cooldownManager = CooldownManager.getInstance();
    
        @EventHandler
        public void onItemEat(final PlayerItemConsumeEvent e){
            final Player player = e.getPlayer();
            final ItemStack stack = e.getItem();
    
            if(stack == null){
                return;
            }
    
            if(stack.getType() == Material.GOLDEN_APPLE){
                if(!cooldownManager.isCooldown(player, stack)){
                    e.setCancelled(true);
                    player.sendMessage("§cYou have to wait before you can eat an apple!");
                    return;
                }
    
                cooldownManager.setCooldown(player, 100, stack);
                player.sendMessage("§cYou have just entered cooldown with the golden apple.");
            }
        }
    }
    
**→ Others:**
*I'm still doing the documentation.*

## Other
In case of problem or information, here are my contact details:

• Discord: https://discord.gg/fjhQ9nfpFw

• Email: nkri.dev@gmail.com
