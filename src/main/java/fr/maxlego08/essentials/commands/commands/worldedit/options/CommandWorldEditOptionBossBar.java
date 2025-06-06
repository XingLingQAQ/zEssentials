package fr.maxlego08.essentials.commands.commands.worldedit.options;

import fr.maxlego08.essentials.api.EssentialsPlugin;
import fr.maxlego08.essentials.api.commands.CommandResultType;
import fr.maxlego08.essentials.api.commands.Permission;
import fr.maxlego08.essentials.api.messages.Message;
import fr.maxlego08.essentials.module.modules.worldedit.WorldeditModule;
import fr.maxlego08.essentials.zutils.utils.commands.VCommand;

public class CommandWorldEditOptionBossBar extends VCommand {

    public CommandWorldEditOptionBossBar(EssentialsPlugin plugin) {
        super(plugin);
        this.setModule(WorldeditModule.class);
        this.setPermission(Permission.ESSENTIALS_WORLDEDIT_OPTION_BOSSBAR);
        this.setDescription(Message.DESCRIPTION_WORLDEDIT_OPTION_BOSSBAR);
        this.addSubCommand("bossbar");
        this.onlyPlayers();
    }

    @Override
    protected CommandResultType perform(EssentialsPlugin plugin) {

        plugin.getWorldeditManager().toggleOptionBossBar(this.user);

        return CommandResultType.SUCCESS;
    }

}
