package fr.nkri.api.packets.servers;

import fr.nkri.api.packets.WizPacket;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketListener;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.UUID;

public abstract class ServerPacket extends WizPacket {

    @Override
    public void readPacket(final PacketDataSerializer reader) throws IOException {}

    @Override
    public void handle(final PacketListener handler) {}

    //Envoyer un double
    public void writeDouble(final PacketDataSerializer packetDataSerializer, final Double value) {
        packetDataSerializer.writeDouble(value);
    }

    //envoyer une chaine string
    public void writeString(final PacketDataSerializer packetDataSerializer, final String label) throws IOException {packetDataSerializer.a(label);}

    //envoyer un int
    public void writeInt(final PacketDataSerializer packetDataSerializer, final int value) throws IOException{
        packetDataSerializer.writeInt(value);
    }

    public void writeUUID(final PacketDataSerializer packetDataSerializer, final UUID uuid) { packetDataSerializer.writeUUID(uuid); }

    public void writeItemStack(final PacketDataSerializer packetDataSerializer, final ItemStack stack) {
        packetDataSerializer.a(CraftItemStack.asNMSCopy(stack));
    }

    public void writeBoolean(final PacketDataSerializer packetDataSerializer, final boolean value) {
        packetDataSerializer.writeBoolean(value);
    }

    public void writeLong(final PacketDataSerializer packetDataSerializer, final long value) throws IOException{
        packetDataSerializer.writeLong(value);
    }

    public void writeFloat(final PacketDataSerializer packetDataSerializer, final float value) throws IOException{
        packetDataSerializer.writeFloat(value);
    }
}