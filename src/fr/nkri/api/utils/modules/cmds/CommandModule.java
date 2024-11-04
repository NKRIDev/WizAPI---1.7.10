package fr.nkri.api.utils.modules.cmds;

import fr.nkri.api.cmds.CommandArguments;
import fr.nkri.api.cmds.ICommand;
import fr.nkri.api.cmds.interfaces.Command;
import fr.nkri.api.utils.WizUtils;
import fr.nkri.api.utils.modules.Module;
import fr.nkri.api.utils.modules.ModuleManager;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandModule extends ICommand {

    private final ModuleManager manager;

    /**
     * Module management by a user
     *
     * @param manager Module Management Class
     */
    public CommandModule(final ModuleManager manager) {
        this.manager = manager;
    }

    // -> /module <toggle> <name>
    // -> /module list
    @Command(name = "module", permissionNode = "module.admin", isConsole = true)
    public boolean onCommand(CommandArguments args) {
        final CommandSender sender = args.getSender();

        if(args.length() == 0){
            showHelp(sender);
            return false;
        }

        final String action = args.getArgs(0);
        switch (action.toLowerCase()){
            case "toggle":
                final String moduleName = args.getArgs(1);
                final Module module = this.manager.getModuleByName(moduleName);

                if(module == null){
                    sender.sendMessage("§7§l(§9§lModules§7§l)§r§c Le module '%name%' n'existe pas.".replace("%name%", moduleName));
                    return false;
                }

                final boolean toggle = !module.isEnable();
                final String state = toggle ? "§a§lActivé" : "§c§lDésactivé";
                module.setEnable(toggle);
                sender.sendMessage("§7§l(§9§lModules§7§l)§r§e État: %state%§r§e pour le module §6'%name%'"
                        .replace("%state%", state)
                        .replace("%name%", moduleName));
                break;
            case "list":
                final List<Module> modules = this.manager.getModules();
                final List<String> lores = new ArrayList<>();

                for(Module moduleItem : modules){
                    final String toggleString = moduleItem.isEnable() ? "§a§lActivé" : "§c§lDésactivé";

                    lores.add("§8§l- §r§6%name% §7: §r%state%"
                    .replace("%name%", moduleItem.getName())
                            .replace("%state%", toggleString));
                }

                sender.sendMessage(WizUtils.LINE);
                sender.sendMessage("§7§l §9§lListe des modules:");
                sender.sendMessage("");
                for(String msg : lores){
                    sender.sendMessage(msg);
                }
                sender.sendMessage("");
                sender.sendMessage(WizUtils.LINE);
                break;
            default:
                showHelp(sender);
                break;
        }
        return false;
    }

    //Help me for sender
    private void showHelp(final CommandSender sender){
        sender.sendMessage(WizUtils.LINE);
        sender.sendMessage("§7§l» §9§lModules:");
        sender.sendMessage("");
        sender.sendMessage("§6/module <toggle> <name> §7: §echange l'état d'un module.");
        sender.sendMessage("§6/module list §7: §erenvoie la liste des modules.");
        sender.sendMessage("");
        sender.sendMessage(WizUtils.LINE);
    }
}
