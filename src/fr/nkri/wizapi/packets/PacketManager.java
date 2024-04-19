package fr.nkri.wizapi.packets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.nkri.wizapi.logs.Logs;
import fr.nkri.wizapi.logs.enums.LogsType;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.util.com.google.common.collect.BiMap;

public class PacketManager {

    public List<PacketModel> packetList = new ArrayList<PacketModel>();

    //Envoyer un packet à un joueur
    public static void sendPacket(final Packet packet, final Player player) {
        final PlayerConnection connection = (((CraftPlayer)player).getHandle()).playerConnection;
        connection.sendPacket(packet);
    }

    //Enregistrer un packet(param: le packet, id)
    public void registerPacket(final Packet packet, final int packetID) {
        final PacketModel packetModel = new PacketModel(packet, packetID);
        packetList.add(packetModel);
    }

    //Enregistrer les packets
    @SuppressWarnings("unchecked")
    public void registerPackets() {
        try {
            for(PacketModel packet : packetList) {
                final Packet packetMinecraft = packet.getPacket();
                final int packetID = packet.getId();

                Class<EnumProtocol> clazz = EnumProtocol.class;
                Field fieldFirst;
                BiMap<Integer, Class<?>> packetsMap;

                fieldFirst = packetMinecraft.getClass().getName().contains("ClientPacket") ? clazz.getDeclaredField("h") : clazz.getDeclaredField("i");
                fieldFirst.setAccessible(true);
                packetsMap = (BiMap<Integer, Class<?>>) fieldFirst.get(EnumProtocol.PLAY);
                packetsMap.put(Integer.valueOf(packetID), packetMinecraft.getClass());

                Field fieldSecond = EnumProtocol.class.getDeclaredField("f");
                fieldSecond.setAccessible(true);
                Map<Class<?>, EnumProtocol> map = (Map<Class<?>, EnumProtocol>) fieldSecond.get(EnumProtocol.PLAY);
                map.put(packetMinecraft.getClass(), EnumProtocol.PLAY);

                Logs.sendLog("(WizPacket)", "Register packet " + packet.getId(), LogsType.INFO);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}