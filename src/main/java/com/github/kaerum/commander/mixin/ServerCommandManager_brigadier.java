package com.github.kaerum.commander.mixin;

import com.github.kaerum.commander.TestCommand;
import com.github.kaerum.commander.TestCommand2;
import com.github.kaerum.commander.adaptations.ServerCommandSource;
import com.github.kaerum.commander.adaptations.brigadier.BrigadierAdapter;
import com.github.kaerum.commander.math.Vec2f;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerCommandManager.class)
public class ServerCommandManager_brigadier extends CommandHandler {

    private boolean hasRegistered = false;

    @Override
    public int executeCommand(ICommandSender commandSender, String command) {
//        if (hasRegistered == false) {
//            hasRegistered = true;
//            BrigadierAdapter.register(TestCommand2.build());
//            BrigadierAdapter.register(TestCommand.build());
//        }
        ChunkCoordinates pos = commandSender.getPlayerCoordinates();
        Vec3 position = Vec3.fakePool.getVecFromPool(pos.posX, pos.posY, pos.posZ);
        Vec2f rotation = new Vec2f(0, 0);
        ServerConfigurationManager serverConfigurationManager = MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer());
        if (serverConfigurationManager == null) {
            return 0;
        }
        EntityPlayer player = serverConfigurationManager.getPlayerForUsername(commandSender.getCommandSenderName());
        // TODO: get actual permission level
        int permissionLevel = 5;
        if (player != null) {
            rotation.x = player.rotationYaw;
            rotation.y = player.rotationPitch;
        }
        ServerCommandSource source = new ServerCommandSource(
                position,
                rotation,
                player,
                permissionLevel,
                MinecraftServer.getServer()
        );
        int brigadierResult = BrigadierAdapter.execute(source, command);
        if (brigadierResult == -1) {
            return super.executeCommand(commandSender, command);
        }
        return brigadierResult;
    }
}
