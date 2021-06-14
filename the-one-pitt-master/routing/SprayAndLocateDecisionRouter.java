/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routing;

import core.Connection;
import core.Coord;
import core.DTNHost;
import core.Message;
import core.Settings;
import core.SimClock;
import core.SimScenario;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author KOMPUTER
 */
public class SprayAndLocateDecisionRouter implements RoutingDecisionEngine {

    public static final String NROF_COPIES = "nrofCopies";
    public static final String BINARY_MODE = "binaryMode";
    public static final String SPRAYANDWAIT_NS = "SprayAndLocateDecisionRouter";
    public static final String MSG_COUNT_PROPERTY = SPRAYANDWAIT_NS + "."
            + "copies";

    protected int initialNrofCopies;
    protected boolean isBinary;
    protected Map<DTNHost, GeoInfo> dictionary = new HashMap<DTNHost, GeoInfo>();
   

    public SprayAndLocateDecisionRouter(Settings s) {
        s = new Settings(SPRAYANDWAIT_NS);
        initialNrofCopies = s.getInt(NROF_COPIES);

        if (isBinary) {
            isBinary = s.getBoolean(BINARY_MODE);
        }
    }

    public SprayAndLocateDecisionRouter(SprayAndLocateDecisionRouter prototype) {
        this.initialNrofCopies = prototype.initialNrofCopies;
        this.isBinary = prototype.isBinary;


    }

    @Override
    public void connectionUp(DTNHost thisHost, DTNHost peer) {
      
         GeoInfo thisGeo  = new GeoInfo();
        thisGeo.setLoc(thisHost.getLocation());
        thisGeo.setDir(0);
        thisGeo.setSpeed(0);
        thisGeo.setTime(SimClock.getTime());
        System.out.println(thisGeo.toString());
//        System.out.println(thisGeo.toString());
       for (Map.Entry<DTNHost, GeoInfo> entry : thisHost) {
       
       }
//            if (this.Dictionary.get(i).getId() == thisHost) {
//                this.Dictionary.remove(i);
//                this.Dictionary.add(thisGeo);
//            }
//        }
//        //cek geoinfo from peer
//        SprayAndLocateDecisionRouter de = getOtherDecisionEngine(peer);
//        List<GeoInfo> peerDictionary = de.Dictionary;
//        for (int i = 0; i < peerDictionary.size(); i++) {
//            for (int j = 0; j < this.Dictionary.size(); j++) {
//                if (peerDictionary.get(i).getId().equals(this.Dictionary.get(j).getId())) {
//                    if ((Double) peerDictionary.get(i).getTime() > (Double) this.Dictionary.get(j).getTime()) {
//                        this.Dictionary.remove(j);
//                        this.Dictionary.add(peerDictionary.get(i));
//                    }
//                } else {
//                    this.Dictionary.add(peerDictionary.get(i));
//                }
//
//            }
//        }
//        System.out.println("HALO");
//        this.Dictionary.add(new GeoInfo(thisHost.getAddress(), thisHost.getLocation(), thisHost.getPath().getCoords(), thisHost.getPath().getSpeed(), SimClock.getTime()));
    }

    @Override
    public void connectionDown(DTNHost thisHost, DTNHost peer) {

    }

    @Override
    public void doExchangeForNewConnection(Connection con, DTNHost peer) {
        List<DTNHost> nodeID =SimScenario.getInstance().getHosts();
        if (dictionary.isEmpty()){
            for(DTNHost ahost : nodeID){
                GeoInfo <Coord,Double,Double,Double> value = new GeoInfo(new Coord(0, 0),0,0,0);
                dictionary.put(ahost, value);
            }
        }

    }

    @Override
    public boolean newMessage(Message m) {
        m.addProperty(MSG_COUNT_PROPERTY, initialNrofCopies);
        return true;
//        return false;
    }

    @Override
    public boolean isFinalDest(Message m, DTNHost aHost) {
        Integer nrofCopies = (Integer) m.getProperty(MSG_COUNT_PROPERTY);

        assert nrofCopies != null : "Not a SnW message: " + m;

        if (isBinary) {
            /* in binary S'n'W the receiving node gets ceil(n/2) copies */
            nrofCopies = (int) Math.ceil(nrofCopies / 2.0);
        } else {
            /* in standard S'n'W the receiving node gets only single copy */
            nrofCopies = 1;
        }

        m.updateProperty(MSG_COUNT_PROPERTY, nrofCopies);
        return m.getTo() == aHost;
    }

    @Override
    public boolean shouldSaveReceivedMessage(Message m, DTNHost thisHost) {
        return m.getTo() != thisHost;
    }

    @Override
    public boolean shouldSendMessageToHost(Message m, DTNHost otherHost) {
        if (m.getTo() == otherHost) {
            return true;
        }
        Integer nrofCopies = (Integer) m.getProperty(MSG_COUNT_PROPERTY);
        if (nrofCopies > 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
        Integer nrofCopies = (Integer) m.getProperty(MSG_COUNT_PROPERTY);

        if (isBinary) {
            nrofCopies /= 2;
        } else {
            nrofCopies--;
        }
        m.updateProperty(MSG_COUNT_PROPERTY, nrofCopies);
        return true;
    }

    @Override
    public boolean shouldDeleteOldMessage(Message m, DTNHost hostReportingOld) {
        return true;
    }

    @Override
    public RoutingDecisionEngine replicate() {
        return new SprayAndLocateDecisionRouter(this);
    }

    private SprayAndLocateDecisionRouter getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherRouter = h.getRouter();
        assert otherRouter instanceof DecisionEngineRouter : "This router only works " + " with other routers of same type";
        return (SprayAndLocateDecisionRouter) ((DecisionEngineRouter) otherRouter).getDecisionEngine();
    }
}
