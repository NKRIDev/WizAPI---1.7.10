package fr.nkri.api.utils.json.adapter;

import java.lang.reflect.Type;

import fr.nkri.api.utils.items.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParseException;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        ItemBuilder itemBuilder = new ItemBuilder(src);
        return itemBuilder.toJSONObjectCustom();
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String serializedItem = jsonObject.get("serialized").getAsString();
        ItemBuilder itemBuilder = new ItemBuilder(serializedItem);
        return itemBuilder.toItemStack();
    }
}