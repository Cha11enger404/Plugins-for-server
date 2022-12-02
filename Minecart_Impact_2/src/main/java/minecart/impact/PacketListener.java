package minecart.impact;

import com.github.retrooper.packetevents.event.*;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSteerVehicle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.util.Vector;

import java.util.Objects;

public class PacketListener extends SimplePacketListenerAbstract {

    @Override
    public void onUserDisconnect(UserDisconnectEvent event) {
        PlayerManager.removePlayer(event.getUser().getUUID());
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (!event.getPacketType().equals(PacketType.Play.Client.STEER_VEHICLE)) return;

        WrapperPlayClientSteerVehicle wrapper = new WrapperPlayClientSteerVehicle(event);
        PlayerManager.setMovement(event.getUser().getUUID(), wrapper.getForward());

        if (wrapper.getForward() <= 0) return;
        Minecart minecart = (Minecart) Objects.requireNonNull(Bukkit.getPlayer(event.getUser().getUUID())).getVehicle();
        if (minecart == null) return;

        boolean moving = false;
        for (double i : new double[]{minecart.getVelocity().getX(), minecart.getVelocity().getY(), minecart.getVelocity().getZ()}) {
            if (i != 0) moving = true;
        }

        if (!moving) minecart.setVelocity(new Vector(0.1, 0.1, 0.1));
    }
}
