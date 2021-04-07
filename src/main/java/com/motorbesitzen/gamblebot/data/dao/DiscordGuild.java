package com.motorbesitzen.gamblebot.data.dao;

import com.motorbesitzen.gamblebot.util.ParseUtil;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DiscordGuild {

	@Id
	@Min(10000000000000000L)
	private long guildId;

	@Min(0)
	private long logChannelId;

	@OneToOne
	private GambleSettings gambleSettings;

	@OneToMany(mappedBy = "guild", cascade = CascadeType.ALL)
	private Set<DiscordMember> members;

	protected DiscordGuild() {
	}

	private DiscordGuild(@Min(10000000000000000L) final long guildId) {
		this.guildId = guildId;
		this.members = new HashSet<>();
	}

	private DiscordGuild(@Min(10000000000000000L) final long guildId, @Min(0) final long logChannelId) {
		this.guildId = guildId;
		this.logChannelId = logChannelId;
		this.members = new HashSet<>();
	}

	public static DiscordGuild createDefault(final long guildId, final long logChannelId) {
		return new DiscordGuild(guildId, logChannelId);
	}

	public static DiscordGuild withGuildId(final long guildId) {
		return new DiscordGuild(guildId);
	}

	public long getGuildId() {
		return guildId;
	}

	public long getLogChannelId() {
		return logChannelId;
	}

	public void setLogChannelId(long logChannelId) {
		this.logChannelId = logChannelId;
	}

	public GambleSettings getGambleSettings() {
		return gambleSettings;
	}

	public void setGambleSettings(GambleSettings gambleSettings) {
		this.gambleSettings = gambleSettings;
	}

	public boolean hasRunningGamble() {
		if (gambleSettings == null) {
			return false;
		}

		final long startMs = gambleSettings.getStartTimestampMs();
		final long endMs = startMs + gambleSettings.getDurationMs();
		return System.currentTimeMillis() < endMs;
	}

	public String getTimeToEndText() {
		if (gambleSettings == null) {
			return "This guild did not start a giveaway yet.";
		}

		final long startMs = gambleSettings.getStartTimestampMs();
		final long endMs = startMs + gambleSettings.getDurationMs();
		final long toEndMs = endMs - System.currentTimeMillis();
		if (toEndMs <= 0) {
			return "Gamble already ended.";
		}

		return ParseUtil.parseMillisecondsToText(toEndMs);
	}
}
