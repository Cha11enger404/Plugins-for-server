package minecart.impact;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private static Map<UUID, Float> PLAYER_MAP = new HashMap<>();

    public static void removePlayer(UUID uuid) {
        PLAYER_MAP.remove(uuid);
    }

    public static float getMovement(UUID uuid) {
        return PLAYER_MAP.get(uuid);
    }

    public static void setMovement(UUID uuid, float amount) {
        PLAYER_MAP.put(uuid, amount);
    }
}
