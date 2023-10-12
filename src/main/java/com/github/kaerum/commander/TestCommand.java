package com.github.kaerum.commander;

import com.github.kaerum.commander.adaptations.ServerCommandSource;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import static com.github.kaerum.commander.adaptations.brigadier.BrigadierAdapter.argument;
import static com.github.kaerum.commander.adaptations.brigadier.BrigadierAdapter.literal;

public class TestCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> build() {
        return literal("test")
                .then(
                        argument("argument2", LongArgumentType.longArg(10, 20)).executes(c -> 1)
                )
                .executes(c -> 1);
    }
}
