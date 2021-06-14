/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routing;

/**
 *
 * @author KOMPUTER
 */
public class GeoInfo<Location, Direction, Speed, InfoTime> {

   
    private Location loc;
    private Direction dir;
    private Speed speed;
    private InfoTime time;

    /**
     * Creates a new tuple.
     *
     *
     * @param id
     * @param loc
     * @param dir
     * @param speed
     * @param time
     */
    public GeoInfo() {
 
    }
    public GeoInfo( Location loc, Direction dir, Speed speed, InfoTime time) {
        
        this.loc = loc;
        this.dir = dir;
        this.speed = speed;
        this.time = time;
//        this.value = value;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public InfoTime getTime() {
        return time;
    }

    public void setTime(InfoTime time) {
        this.time = time;
    }

    /**
     * Returns the key
     *
     * @return the key
     */
 

    /**
     * Returns a string representation of the tuple
     *
     * @return a string representation of the tuple
     */
    public String toString() {
        return  loc.toString() + ":" + dir.toString() + ":" + speed.toString() + ":" + time.toString();
    }
}
