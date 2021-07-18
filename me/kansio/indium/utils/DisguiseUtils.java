package me.kansio.indium.utils;

/* Created by Preceding on 7/14/2021 | 2:24 PM */
/* This file is apart of Matrix Studios, LTD. - You are prohibited from redistributing this file. */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.log.LogUtils;
import javafx.scene.control.Skin;
import me.kansio.indium.IndiumPlugin;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import net.minecraft.util.org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class DisguiseUtils {

    /*/public static void disguise(Player player, String name, boolean clear){
        Disguise checkDisguise = IndiumPlugin.getInstance().getDisguiseManager().getDisguiseMap().get(name);
        Disguise playerDisguise = IndiumPlugin.getInstance().getDisguiseManager().getDisguiseMap().get(player.getName());
        if(playerDisguise != null){
            player.sendMessage("§cYou are already disguised.");
            return;
        }
        if(checkDisguise != null || Bukkit.getPlayer(name) != null){
            player.sendMessage("§cA player by that name is already on the server or someone is already disguised with that name.");
            return;
        }
        new Thread(() -> {
            Bukkit.getServer().getScheduler().runTask(IndiumPlugin.getInstance(), () -> {
                for(Player target : Bukkit.getServer().getOnlinePlayers()){
                    target.hidePlayer(player);
                }
            });
            String realName = player.getName();
            CraftPlayer craftPlayer = ((CraftPlayer) player);
            EntityPlayer entityPlayer = craftPlayer.getHandle();
            GameProfile gameProfile = craftPlayer.getProfile();
            GameProfile realGameProfile = craftPlayer.getProfile();
            String caseDisguiseName;
            String texture;
            String signature;
            try{
                JsonParser jsonParser = new JsonParser();
                String response = getResponse("https://api.minetools.eu/uuid/" + name);
                JsonObject parsed = (JsonObject) jsonParser.parse(response);
                String uuid = parsed.get("id").getAsString();
                caseDisguiseName = parsed.get("name").getAsString();
                response = getResponse("https://api.minetools.eu/profile/" + uuid);
                parsed = (JsonObject) jsonParser.parse(response);
                JsonObject raw = (JsonObject) parsed.get("raw");
                JsonObject properties = (JsonObject) ((JsonArray) raw.get("properties")).get(0);
                texture = properties.get("value").getAsString();
                signature = properties.get("signature").getAsString();
            } catch (Exception e) {
                player.sendMessage("§cNo player with the name \"" + name + "\" exists.");
                Bukkit.getServer().getScheduler().runTask(IndiumPlugin.getInstance(), () -> {
                    for(Player target : Bukkit.getServer().getOnlinePlayers()){
                        target.showPlayer(player);
                    }
                });
                return;
            }

            try {
                Field f = gameProfile.getClass().getDeclaredField("name");
                f.setAccessible(true);
                f.set(gameProfile, caseDisguiseName);
                f.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            player.setDisplayName(name);
            player.setPlayerListName(name);

            gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", new Property("textures", texture, signature));

            PacketPlayOutPlayerInfo removePlayerPacket = PacketPlayOutPlayerInfo.removePlayer(entityPlayer);
            PacketPlayOutPlayerInfo addPlayerPacket = PacketPlayOutPlayerInfo.addPlayer(entityPlayer);
            PacketPlayOutPosition positionPacket = new PacketPlayOutPosition(entityPlayer.locX,
                    entityPlayer.locY,
                    entityPlayer.locZ,
                    entityPlayer.yaw,
                    entityPlayer.pitch, true);
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(entityPlayer.dimension,
                    entityPlayer.world.difficulty,
                    entityPlayer.world.worldData.getType(),
                    entityPlayer.playerInteractManager.getGameMode());
            Arrays.asList(removePlayerPacket, addPlayerPacket, respawnPacket, positionPacket).forEach(packet -> sendPacket(player, packet));
            Bukkit.getServer().getScheduler().runTask(IndiumPlugin.getInstance(), () -> {
                for(Player target : Bukkit.getServer().getOnlinePlayers()){
                    target.showPlayer(player);
                }
            });


            //Skin skin = new Skin(texture, signature);
            //Disguise disguise = new Disguise(caseDisguiseName, skin, realName, realGameProfile);
            IndiumPlugin.getInstance().getDisguiseManager().getDisguiseMap().put(caseDisguiseName, disguise);
            player.sendMessage("§aYou are now disguised as §e" + caseDisguiseName + "§a.");
        }).start();
    }

    public static void undisguise(Player player){
        Disguise disguise = IndiumPlugin.getInstance().getDisguiseManager().getDisguiseMap().get(player.getName());
        if(disguise == null) {
            //LogUtils.log(LogType.DISGUISE, "Unable to retrieve disguise of " + player.getName() + ".");
            return;
        }
        new Thread(() -> {
            Bukkit.getServer().getScheduler().runTask(IndiumPlugin.getInstance(), () -> {
                for(Player target : Bukkit.getServer().getOnlinePlayers()){
                    target.hidePlayer(player);
                }
            });
            CraftPlayer craftPlayer = ((CraftPlayer) player);
            GameProfile gameProfile = craftPlayer.getProfile();
            EntityPlayer entityPlayer = craftPlayer.getHandle();
            String texture;
            String signature;
            try{
                JsonParser jsonParser = new JsonParser();
                String response = getResponse("https://api.minetools.eu/uuid/" + disguise.getRealName());
                JsonObject parsed = (JsonObject) jsonParser.parse(response);
                String uuid = parsed.get("id").getAsString();
                response = getResponse("https://api.minetools.eu/profile/" + uuid);
                parsed = (JsonObject) jsonParser.parse(response);
                JsonObject raw = (JsonObject) parsed.get("raw");
                JsonObject properties = (JsonObject) ((JsonArray) raw.get("properties")).get(0);
                texture = properties.get("value").getAsString();
                signature = properties.get("signature").getAsString();
            } catch (Exception e) {
                player.kickPlayer(CC.translate("§cUnable to retrieve your real player data, please reconnect."));
                //LogUtils.log(LogType.DISGUISE, "Unable to retrieve real player data for " + player.getName() + ".");
                return;
            }
            try{
                Field nameField = gameProfile.getClass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(gameProfile, disguise.getRealName());
                nameField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
            player.setPlayerListName(disguise.getRealName());
            player.setDisplayName(disguise.getRealName());

            PacketPlayOutPlayerInfo removePlayerPacket = PacketPlayOutPlayerInfo.removePlayer(entityPlayer);
            PacketPlayOutPlayerInfo addPlayerPacket = PacketPlayOutPlayerInfo.addPlayer(entityPlayer);
            PacketPlayOutPosition positionPacket = new PacketPlayOutPosition(entityPlayer.locX,
                    entityPlayer.locY,
                    entityPlayer.locZ,
                    entityPlayer.yaw,
                    entityPlayer.pitch, true);
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(entityPlayer.dimension,
                    entityPlayer.world.difficulty,
                    entityPlayer.world.worldData.getType(),
                    entityPlayer.playerInteractManager.getGameMode());
            Arrays.asList(removePlayerPacket, addPlayerPacket, respawnPacket, positionPacket).forEach(packet -> sendPacket(player, packet));

            Bukkit.getServer().getScheduler().runTask(IndiumPlugin.getInstance(), () -> {
                for(Player target : Bukkit.getServer().getOnlinePlayers()){
                    target.showPlayer(player);
                }
            });
            IndiumPlugin.getInstance().getDisguiseManager().getDisguiseMap().remove(disguise.getName());
            player.sendMessage(CC.translate("§aYou are no longer disguised."));
        }).start();

    }

    public static void sendPacket(Player player, Packet packet){
        CraftPlayer cp = (CraftPlayer) player;
        cp.getHandle().playerConnection.sendPacket(packet);
    }

    public static String getResponse(String _url){
        try {
            URL url = new URL(_url);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            return IOUtils.toString(in, encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }/*/

}

