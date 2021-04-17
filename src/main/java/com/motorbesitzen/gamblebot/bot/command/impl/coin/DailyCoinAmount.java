package com.motorbesitzen.gamblebot.bot.command.impl.coin;

import com.motorbesitzen.gamblebot.bot.command.CommandImpl;
import com.motorbesitzen.gamblebot.data.dao.DiscordGuild;
import com.motorbesitzen.gamblebot.data.repo.DiscordGuildRepo;
import com.motorbesitzen.gamblebot.util.ParseUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("setdaily")
public class DailyCoinAmount extends CommandImpl {

	private final DiscordGuildRepo guildRepo;

	@Autowired
	private DailyCoinAmount(final DiscordGuildRepo guildRepo) {
		this.guildRepo = guildRepo;
	}

	@Override
	public String getName() {
		return "setdaily";
	}

	@Override
	public String getUsage() {
		return getName() + " coins";
	}

	@Override
	public String getDescription() {
		return "Sets the amount of coins a user can get each day.";
	}

	@Override
	public boolean isAdminCommand() {
		return true;
	}

	@Override
	public void execute(final GuildMessageReceivedEvent event) {
		final long guildId = event.getGuild().getIdLong();
		final Optional<DiscordGuild> dcGuildOpt = guildRepo.findById(guildId);
		final DiscordGuild dcGuild = dcGuildOpt.orElseGet(() -> DiscordGuild.withGuildId(guildId));
		final Message message = event.getMessage();
		final String content = message.getContentRaw();
		final String[] tokens = content.split(" ");
		final long coinAmount = ParseUtil.safelyParseStringToLong(tokens[tokens.length - 1]);
		if(coinAmount < 0 || coinAmount > Integer.MAX_VALUE) {
			sendErrorMessage(event.getChannel(), "Please set a valid amount of daily coins (0 - " + Integer.MAX_VALUE + ")!");
			return;
		}

		dcGuild.setDailyCoins(coinAmount);
		guildRepo.save(dcGuild);
		answer(event.getChannel(), "Set the daily coins amount to **" + coinAmount + "** coins.");
	}
}