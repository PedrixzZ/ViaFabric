package com.viaversion.fabric.mc119.commands;

import com.viaversion.fabric.common.util.RemappingUtil;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import com.viaversion.viaversion.api.command.ViaCommandSender;

import java.util.UUID;

public class NMSCommandSender implements ViaCommandSender {
    private final CommandSource source;

    public NMSCommandSender(CommandSource source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(String s) {
        // https://gaming.stackexchange.com/questions/138602/what-does-op-permission-level-do
        return source.hasPermissionLevel(3);
    }

    @Override
    public void sendMessage(String s) {
        if (source instanceof ServerCommandSource) {
            ((ServerCommandSource) source).sendFeedback(Text.Serializer.fromJson(RemappingUtil.legacyToJson(s)), false);
        } else if (source instanceof FabricClientCommandSource) {
            ((FabricClientCommandSource) source).sendFeedback(Text.Serializer.fromJson(RemappingUtil.legacyToJson(s)));
        }
    }

    @Override
    public UUID getUUID() {
        if (source instanceof ServerCommandSource) {
            Entity entity = ((ServerCommandSource) source).getEntity();
            if (entity != null) return entity.getUuid();
        } else if (source instanceof FabricClientCommandSource) {
            return ((FabricClientCommandSource) source).getPlayer().getUuid();
        }
        return UUID.fromString(getName());
    }

    @Override
    public String getName() {
        if (source instanceof ServerCommandSource) {
            return ((ServerCommandSource) source).getName();
        } else if (source instanceof FabricClientCommandSource) {
            return ((FabricClientCommandSource) source).getPlayer().getEntityName();
        }
        return "?";
    }
}