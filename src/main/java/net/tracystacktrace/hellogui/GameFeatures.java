package net.tracystacktrace.hellogui;

import net.minecraft.client.Minecraft;

public final class GameFeatures {

    public static boolean isSingleplayer() {
        return Minecraft.getInstance().theWorld != null &&
                !Minecraft.getInstance().theWorld.isRemote;
    }

    public static boolean isScreenEmpty() {
        return Minecraft.getInstance().currentScreen == null;
    }

}
