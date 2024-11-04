package fr.nkri.api.packets;

import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;

import java.io.IOException;

public abstract class WizPacket extends Packet {

    @Override
    public void a(final PacketDataSerializer packet) throws IOException {
        readPacket(packet);
    }

    @Override
    public void b(PacketDataSerializer packet) throws IOException {
        writePacket(packet);
    }

    public abstract void readPacket(final PacketDataSerializer packetDataSerializer) throws IOException;
    public abstract void writePacket(final PacketDataSerializer packetDataSerializer) throws IOException;
}
