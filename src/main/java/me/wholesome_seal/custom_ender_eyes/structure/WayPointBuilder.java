package me.wholesome_seal.custom_ender_eyes.structure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("WayPoint")
public class WayPointBuilder implements ConfigurationSerializable {
    public String name;
    public int coordsX;
    public int coordsY;
    public int coordsZ;
    
    public WayPointBuilder(String name, Location location) {
        if (location == null) {
            new WayPointBuilder("default-name", 0, 0, 0);
            return;
        }
        this.name = name;
        this.coordsX = (int) location.getX();
        this.coordsY = (int) location.getY();
        this.coordsZ = (int) location.getZ();
    }

    private WayPointBuilder(String name, int coordsX, int coordsY, int coordsZ) {
        this.name = name;
        this.coordsX = coordsX;
        this.coordsY = coordsY;
        this.coordsZ = coordsZ;
    }

    public Location getLocation(World world) {
        return new Location(world, coordsX, coordsY, coordsZ);
    }

    public static ArrayList<WayPointBuilder> getOverworld() {
        return getWayPoint("waypoint.overworld");
    }

    public static ArrayList<WayPointBuilder> getNether() {
        return getWayPoint("waypoint.nether");
    }

    public static ArrayList<WayPointBuilder> getEnd() {
        return getWayPoint("waypoint.end");
    }

    public static ArrayList<WayPointBuilder> getWayPoint(String path) {
        try {
            @SuppressWarnings("unchecked")
            List<WayPointBuilder> waypoints = (List<WayPointBuilder>) CustomStorage.config.get(path);
            return new ArrayList<WayPointBuilder>(waypoints);
        } catch (Exception exception) {
            System.out.println("[CustomEnderEyes] Error while retrieving " + path);
            return new ArrayList<WayPointBuilder>();
        }
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        result.put("name", this.name);
        result.put("x", this.coordsX);
        result.put("y", this.coordsY);
        result.put("z", this.coordsZ);

        return result;
    }

    public static WayPointBuilder deserialize(Map<String, Object> args) {
        String name = null;
        int coordsX = 0;
        int coordsY = 0;
        int coordsZ = 0;

        if (args.containsKey("name")) name = (String) args.get("name");
        if (args.containsKey("x")) coordsX = (int) args.get("x");
        if (args.containsKey("y")) coordsY = (int) args.get("y");
        if (args.containsKey("z")) coordsZ = (int) args.get("z");

        return new WayPointBuilder(name, coordsX, coordsY, coordsZ);
    }
}
