package com.github.kaerum.commander.adaptations.brigadier.argumenttypes;

import com.github.kaerum.commander.adaptations.ServerCommandSource;
import com.github.kaerum.commander.math.Vec2f;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class RotationArgumentType implements ArgumentType<Vec2f> {

    public static RotationArgumentType rotation() {
        return new RotationArgumentType();
    }

    public static Vec2f getRotation(CommandContext<ServerCommandSource> context, String name) {
        return context.getArgument(name, Vec2f.class);
    }

    @Override
    public Vec2f parse(StringReader reader) throws CommandSyntaxException {
        float x = reader.readFloat();
        float y = reader.readFloat();
        return new Vec2f(x, y);
    }
}
