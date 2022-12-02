package minecart.impact;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public class VehicleMoveListener implements Listener {

    private static final double SPEED = 10;
    private static final double DIVISOR = 180 / Math.PI;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityMount(EntityMountEvent event) {
        if (!(event.getMount() instanceof Minecart)) return;
        if (!(event.getEntity() instanceof Player player)) return;
        event.getMount().setGravity(false);
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDismount(EntityDismountEvent event) {
        event.getDismounted().setGravity(true);
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleMove(VehicleMoveEvent event) {
        if (!(event.getVehicle() instanceof Minecart minecart)) return;
        if (minecart.getPassengers().isEmpty()) return;
        if (minecart.getPassengers().get(0).getType() != EntityType.PLAYER) return;

        Player passenger = (Player) minecart.getPassengers().get(0);

        double scaleX = Math.cos(Math.abs(passenger.getLocation().getYaw() + 90) / DIVISOR);
        double scaleY = -Math.sin(passenger.getLocation().getPitch() / DIVISOR);
        double scaleZ = Math.cos(Math.abs(passenger.getLocation().getYaw()) / DIVISOR);

        /*
        System.out.println(Math.abs(passenger.getLocation().getYaw()));
        System.out.println(SPEED * scaleX);
        System.out.println(scaleX);
         */

        float forwards = PlayerManager.getMovement(minecart.getPassengers().get(0).getUniqueId());
        if (forwards > 0) minecart.setVelocity(new Vector(SPEED * scaleX, 0.25 * scaleY, SPEED * scaleZ));
        else minecart.setVelocity(new Vector(0, 0, 0));

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (minecart.getWorld() != player.getWorld()) continue;
            if (player.getLocation().distance(minecart.getLocation()) > 1) continue;
            if (minecart.getPassengers().contains(player)) continue;

            player.damage(10);
        }
    }
}
