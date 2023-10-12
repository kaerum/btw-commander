package com.github.kaerum.commander.adaptations.brigadier.argumenttypes;

import com.github.kaerum.commander.adaptations.ServerCommandSource;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.src.Vec3;

import java.util.Arrays;
import java.util.Collection;

public class Vec3ArgumentType implements ArgumentType<Vec3> {

    public static Vec3ArgumentType vec3() {
        return new Vec3ArgumentType();
    }

    private static final Collection<String> EXAMPLES = Arrays.asList(
            "x y z",
            "0 0 0",
            "1.2 .2 -.2"
    );

    public static Vec3 getVec3(CommandContext<ServerCommandSource> context, String name) {
        return context.getArgument(name, Vec3.class);
    }

    @Override
    public Vec3 parse(StringReader reader) throws CommandSyntaxException {
        double x = reader.readDouble();
        double y = reader.readDouble();
        double z = reader.readDouble();
        return Vec3.fakePool.getVecFromPool(x, y ,z);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
