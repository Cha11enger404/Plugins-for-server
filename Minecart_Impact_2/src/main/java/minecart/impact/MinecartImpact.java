package minecart.impact;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecartImpact extends JavaPlugin {

    public static MinecartImpact PLUGIN;

    @Override
    public void onLoad() {
        PLUGIN = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new VehicleMoveListener(), this);

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().bStats(true).checkForUpdates(false);
        PacketEvents.getAPI().load();

        PacketEvents.getAPI().getEventManager().registerListener(new PacketListener());
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

}
