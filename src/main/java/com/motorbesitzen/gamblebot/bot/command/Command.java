package com.motorbesitzen.gamblebot.bot.command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * The interface for any Command the bot can handle.
 */
public interface Command {

	/**
	 * Get the name of the command. The name should be in lower case and should be equal to the service name.
	 *
	 * @return The name of the command.
	 */
	String getName();

	/**
	 * Displays the syntax for the command by defining the name and any additionally needed parameters.
	 *
	 * @return a representation on how to use the command
	 */
	String getUsage();

	/**
	 * Describes what the command does and includes any information that may be needed.
	 *
	 * @return a short text that describes the command and its functionality.
	 */
	String getDescription();

	/**
	 * Defines if the command can only be used by an admin of the guild.
	 *
	 * @return {@code true} if only an admin can use the command.
	 */
	boolean isAdminCommand();

	/**
	 * Defines if the command can get used globally or only in a certain channel if the guild uses that option.
	 *
	 * @return {@code true} if the command can be used everywhere the bot has access to.
	 */
	boolean isGlobalCommand();

	/**
	 * A method that performs the necessary actions for the given command.
	 *
	 * @param event The <a href="https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/events/message/guild/GuildMessageReceivedEvent.html">Discord event</a>
	 *              when a message (possible command) is received.
	 */
	void execute(final GuildMessageReceivedEvent event);
}
