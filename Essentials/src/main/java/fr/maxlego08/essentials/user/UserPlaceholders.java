package fr.maxlego08.essentials.user;

import fr.maxlego08.essentials.api.EssentialsPlugin;
import fr.maxlego08.essentials.api.economy.Economy;
import fr.maxlego08.essentials.api.economy.EconomyProvider;
import fr.maxlego08.essentials.api.placeholders.Placeholder;
import fr.maxlego08.essentials.api.placeholders.PlaceholderRegister;
import fr.maxlego08.essentials.api.storage.IStorage;
import fr.maxlego08.essentials.api.user.User;
import fr.maxlego08.essentials.zutils.utils.ZUtils;

import java.math.BigDecimal;
import java.util.Optional;

public class UserPlaceholders extends ZUtils implements PlaceholderRegister {

    @Override
    public void register(Placeholder placeholder, EssentialsPlugin plugin) {

        IStorage iStorage = plugin.getStorageManager().getStorage();
        EconomyProvider economyProvider = plugin.getEconomyProvider();

        // Target

        placeholder.register("user_target_player_name", player -> {
            User user = iStorage.getUser(player.getUniqueId());
            return user.getTargetUser() != null ? user.getTargetUser().getName() : "no";
        }, "Returns the name of the target player");

        placeholder.register("user_target_pay_amount", player -> {
            User user = iStorage.getUser(player.getUniqueId());
            Economy economy = user.getTargetEconomy();
            BigDecimal decimal = user.getTargetDecimal();
            return economy == null || decimal == null ? "0" : economyProvider.format(economy, decimal);
        }, "Returns the number formatted for the /pay command");

        // Balance

        placeholder.register("user_formatted_balance_", (player, args) -> {
            User user = iStorage.getUser(player.getUniqueId());
            Optional<Economy> optional = economyProvider.getEconomy(args);
            if (optional.isEmpty()) {
                return "Economy " + args + " was not found";
            }
            Economy economy = optional.get();
            BigDecimal decimal = user.getBalance(economy);
            return decimal == null ? "0" : color(economyProvider.format(economy, decimal));
        }, "Returns the formatted number for a given economy", "economy");

        placeholder.register("user_balance_", (player, args) -> {
            User user = iStorage.getUser(player.getUniqueId());
            Optional<Economy> optional = economyProvider.getEconomy(args);
            if (optional.isEmpty()) {
                return "Economy " + args + " was not found";
            }
            Economy economy = optional.get();
            return user.getBalance(economy).toString();
        }, "Returns the number for a given economy", "economy");

        // Home
        placeholder.register("home_count", (player) -> String.valueOf(Optional.ofNullable(iStorage.getUser(player.getUniqueId())).map(User::countHomes).orElse(0)), "Returns the number of homes");
        placeholder.register("home_max", (player) -> String.valueOf(plugin.getMaxHome(player)), "Returns the number of max homes");
    }
}
