package com.example.randomloot;

/**
 * Live on/off switches. The server's loot code reads these, and the in-game menu changes them.
 * Index: 0 = block drops, 1 = mob drops, 2 = "random random" (chaos) mode, 3 = creative-inventory pool.
 */
public final class State {
    public static volatile boolean blocks = true;
    public static volatile boolean mobs = true;
    public static volatile boolean chaos = false;
    public static volatile boolean creative = false;

    private State() {
    }

    public static boolean get(int which) {
        return switch (which) {
            case 0 -> blocks;
            case 1 -> mobs;
            case 2 -> chaos;
            default -> creative;
        };
    }

    public static void set(int which, boolean value) {
        switch (which) {
            case 0 -> blocks = value;
            case 1 -> mobs = value;
            case 2 -> chaos = value;
            default -> creative = value;
        }
    }

    /** Called when a world/server starts: take the saved values from the config file. */
    public static void loadFromConfig() {
        try {
            blocks = Config.RANDOMIZE_BLOCKS.get();
            mobs = Config.RANDOMIZE_MOBS.get();
            chaos = Config.CHAOS_MODE.get();
            creative = Config.CREATIVE_POOL.get();
        } catch (Throwable t) {
            RandomLootMod.LOGGER.warn("[RandomLoot] Could not read config, using defaults.", t);
        }
    }

    /** Saves the current switches back to the config file so they survive a restart. */
    public static void saveToConfig() {
        try {
            Config.RANDOMIZE_BLOCKS.set(blocks);
            Config.RANDOMIZE_MOBS.set(mobs);
            Config.CHAOS_MODE.set(chaos);
            Config.CREATIVE_POOL.set(creative);
            Config.SPEC.save();
        } catch (Throwable t) {
            RandomLootMod.LOGGER.warn("[RandomLoot] Could not save config.", t);
        }
    }
}
