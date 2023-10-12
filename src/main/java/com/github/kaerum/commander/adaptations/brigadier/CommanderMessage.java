package com.github.kaerum.commander.adaptations.brigadier;

import com.mojang.brigadier.Message;

public class CommanderMessage implements Message {

    private String message;

    public CommanderMessage(String message) {
        this.message = message;
    }

    @Override
    public String getString() {
        return this.message;
    }
}
