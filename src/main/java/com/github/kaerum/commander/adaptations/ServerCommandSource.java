package com.github.kaerum.commander.adaptations;

import com.github.kaerum.commander.adaptations.brigadier.CommanderMessage;
import com.github.kaerum.commander.adaptations.brigadier.InvalidCommandEntity;
import com.github.kaerum.commander.math.Vec2f;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumChatFormatting;
import net.minecraft.src.Vec3;
import org.jetbrains.annotations.Nullable;

public class ServerCommandSource implements CommandSource {
    @Nullable
    private final Entity entity;
    private final Vec3 position;
    private final Vec2f rotation;
    private final int permissionLevel;
    private final MinecraftServer server;

    public ServerCommandSource(Vec3 pos, Vec2f rotation, @Nullable Entity entity, int permissionLevel, MinecraftServer server) {
        this.position = pos;
        this.rotation = rotation;
        this.permissionLevel = permissionLevel;
        this.server = server;
        this.entity = entity;
    }

    public void sendFeedback(String message, boolean _broadCastToOps) {
        if (entity instanceof EntityPlayer) {
            ((EntityPlayer)entity).sendChatToPlayer(message);
        }
    }

    public void sendError(String message) {
        if (entity instanceof EntityPlayer) {
            ((EntityPlayer)entity).sendChatToPlayer( EnumChatFormatting.RED + message);
        }
    }

    public EntityPlayer getPlayer() throws CommandSyntaxException {
        if (entity instanceof EntityPlayer) {
            return (EntityPlayer)entity;
        }
        throw new CommandSyntaxException(new InvalidCommandEntity(), new CommanderMessage("Invoking entity is not a player"));
    }

    public Vec2f getRotation() {
        return this.rotation;
    }

    public Vec3 getPosition() {
        return this.position;
    }

    public boolean hasPermissionLevel(int level) {
        return this.permissionLevel >= level;
    }

    public MinecraftServer getServer() {
        return this.server;
    }
}
