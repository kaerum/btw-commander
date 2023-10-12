package com.github.kaerum.commander.adaptations.brigadier;

import com.github.kaerum.commander.adaptations.ServerCommandSource;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.src.CommandException;
import net.minecraft.src.CommandNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;
public class BrigadierAdapter {

    private static final int EXECUTION_ERROR = 0;
    private static final int EXECUTION_UNKNOWN_COMMAND = -1;
    public static final Logger LOGGER = Logger.getLogger("BTW-Commander");
    private static final CommandDispatcher<ServerCommandSource> dispatcher = new CommandDispatcher();
    public static void register(LiteralArgumentBuilder<ServerCommandSource> builder) {
        LOGGER.info("Registered " + builder.getLiteral());
        dispatcher.register(builder);
    }
    public static int execute(ServerCommandSource source, String command) {
        StringReader stringReader = new StringReader(command);
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        try {
            ParseResults<ServerCommandSource> parseResults = dispatcher.parse(stringReader, source);
            return dispatcher.execute(parseResults);
        } catch (CommandNotFoundException commandNotFoundException) {
            return EXECUTION_UNKNOWN_COMMAND;
        } catch (CommandException commandException) {
            source.sendError(commandException.getMessage());
        } catch (CommandSyntaxException commandSyntaxException) {
            if (commandSyntaxException.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand()) {
                return EXECUTION_UNKNOWN_COMMAND;
            }
            source.sendError(commandSyntaxException.getMessage());
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Exception while invoking command");
            exception.printStackTrace();
        }
        return EXECUTION_ERROR;
    }

    public static LiteralArgumentBuilder<ServerCommandSource> literal(String literal) {
        return LiteralArgumentBuilder.literal(literal);
    }

    public static <T> RequiredArgumentBuilder<ServerCommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
