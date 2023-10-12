package com.github.kaerum.commander;

import com.github.kaerum.commander.adaptations.ServerCommandSource;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import static com.github.kaerum.commander.adaptations.brigadier.BrigadierAdapter.literal;

public class TestCommand2 {
    public static LiteralArgumentBuilder<ServerCommandSource> build() {
        return literal("empty")
                .executes(c -> 1);
    }
}
