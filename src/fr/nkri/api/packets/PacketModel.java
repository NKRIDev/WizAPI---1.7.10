package fr.nkri.api.packets;

import net.minecraft.server.v1_7_R4.Packet;

public class PacketModel {

    private final Packet packet;
    private final int id;

    public PacketModel(final Packet packet, final int id){
        this.packet = packet;
        this.id = id;
    }

    public Packet getPacket() {
        return packet;
    }

    public int getId() {
        return id;
    }
}
