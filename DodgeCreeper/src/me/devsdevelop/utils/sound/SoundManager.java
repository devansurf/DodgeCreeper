package me.devsdevelop.utils.sound;


import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SoundManager {
    /**
     * Sends a sound to the player
     * @param soundType the type of sound
     */
    public static void sendSound(Player player, Location location, SoundType soundType)
    {

            player.playSound(location, getSound(soundType), SoundCategory.MASTER, getVolume(soundType), getPitch(soundType));
    }

    public static void sendCategorizedSound(Player player, Location location, SoundType soundType, SoundCategory soundCategory)
    {
            player.playSound(location, getSound(soundType), soundCategory, getVolume(soundType), getPitch(soundType));
    }

    public static void sendCategorizedSound(Player player, Location location, SoundType soundType, SoundCategory soundCategory, float pitchModifier)
    {
        float totalPitch = Math.min(2.0F, (getPitch(soundType) + pitchModifier));

            player.playSound(location, getSound(soundType), soundCategory, getVolume(soundType), totalPitch);
    }

    public static void worldSendSound(World world, Location location, SoundType soundType)
    {
            world.playSound(location, getSound(soundType), getVolume(soundType), getPitch(soundType));
    }

    public static void worldSendSoundMaxPitch(World world, Location location, SoundType soundType) {
            world.playSound(location, getSound(soundType), getVolume(soundType), 2.0F);
    }

    /**
     * All volume is multiplied by the master volume to get its final value
     * @param soundType target soundtype
     * @return the volume for this soundtype
     */
    private static float getVolume(SoundType soundType)
    {
        return 0.2f;
    }

    private static float getPitch(SoundType soundType)
    {
        if(soundType == SoundType.FIZZ)
            return getFizzPitch();
        else 
            return getPopPitch();
    }

    private static Sound getSound(SoundType soundType)
    {
        switch(soundType)
        {
            case ANVIL:
                return Sound.BLOCK_ANVIL_PLACE;
            case ITEM_BREAK:
                return Sound.ENTITY_ITEM_BREAK;
            case POP:
                return Sound.ENTITY_ITEM_PICKUP;
            case CHIMAERA_WING:
                return Sound.ENTITY_BAT_TAKEOFF;
            case LEVEL_UP:
                return Sound.ENTITY_PLAYER_LEVELUP;
            case FIZZ:
                return Sound.BLOCK_FIRE_EXTINGUISH;
            case TOOL_READY:
                return Sound.ITEM_ARMOR_EQUIP_GOLD;
            case ROLL_ACTIVATED:
                return Sound.ENTITY_LLAMA_SWAG;
            case SKILL_UNLOCKED:
                return Sound.UI_TOAST_CHALLENGE_COMPLETE;
            case ABILITY_ACTIVATED_BERSERK:
                return Sound.BLOCK_CONDUIT_AMBIENT;
            case ABILITY_ACTIVATED_GENERIC:
                return Sound.ITEM_TRIDENT_RIPTIDE_3;
            case DEFLECT_ARROWS:
                return Sound.ENTITY_ENDER_EYE_DEATH;
            case TIRED:
                return Sound.BLOCK_CONDUIT_AMBIENT;
            case BLEED:
                return Sound.ENTITY_ENDER_EYE_DEATH;
            case GLASS:
                return Sound.BLOCK_GLASS_BREAK;
            case ITEM_CONSUMED:
                return Sound.ITEM_BOTTLE_EMPTY;
            default:
                return null;
        }
    }

    public static float getFizzPitch() {
        return 2.6F + 1F * 0.8F;
    }

    public static float getPopPitch() {
        return (1F * 0.7F + 1.0F) * 2.0F;
    }

    public static float getKrakenPitch() {
        return (1F) * 0.2F + 1.0F;
    }
}
