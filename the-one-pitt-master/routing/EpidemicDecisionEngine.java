/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package routing;

import core.Connection;
import core.DTNHost;
import core.Message;
import core.Settings;
import core.SimClock;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author KOMPUTER
 */
public class EpidemicDecisionEngine implements RoutingDecisionEngine{
    protected List<GeoInfo> Dictionary;
    protected GeoInfo thisGeo;
    public EpidemicDecisionEngine(Settings s){
        
    }
    public EpidemicDecisionEngine(EpidemicDecisionEngine prot){
           Dictionary = new LinkedList<GeoInfo>();
        thisGeo = new GeoInfo();
    }
    @Override
    public void connectionUp(DTNHost thisHost, DTNHost peer) {
       //        thisGeo = new GeoInfo(thisHost.getAddress(), thisHost.getLocation(), thisHost.getPath().getCoords(), thisHost.getPath().getSpeed(), SimClock.getTime());
       
        this.thisGeo.setLoc(thisHost.getLocation());
        this.thisGeo.setDir(0);
        this.thisGeo.setSpeed(0);
        this.thisGeo.setTime(SimClock.getTime());
//        System.out.println("HALO");
        System.out.println(thisGeo.toString());
//        System.out.println(thisGeo.toString());
//        for (int i = 0; i < this.Dictionary.size(); i++) {
//            if (this.Dictionary.get(i).getId() == thisHost) {
//                this.Dictionary.remove(i);
//                this.Dictionary.add(thisGeo);
//            }
//        }
//        //cek geoinfo from peer
//        EpidemicDecisionEngine de = getOtherDecisionEngine(peer);
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
    }

    @Override
    public void connectionDown(DTNHost thisHost, DTNHost peer) {
       
    }

    @Override
    public void doExchangeForNewConnection(Connection con, DTNHost peer) {
       
    }

    @Override
    public boolean newMessage(Message m) {
       return true;
    }

    @Override
    public boolean isFinalDest(Message m, DTNHost aHost) {
        return m.getTo()==aHost;
    }

    @Override
    public boolean shouldSaveReceivedMessage(Message m, DTNHost thisHost) {
        return false;
    }

    @Override
    public boolean shouldSendMessageToHost(Message m, DTNHost otherHost) {
       return true;
    }

    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
      return m.getTo()==otherHost;
    }

    @Override
    public boolean shouldDeleteOldMessage(Message m, DTNHost hostReportingOld) {
        return true;
    }

    @Override
    public RoutingDecisionEngine replicate() {
        return new EpidemicDecisionEngine(this);
    }
    private EpidemicDecisionEngine getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherRouter = h.getRouter();
        assert otherRouter instanceof DecisionEngineRouter : "This router only works " + " with other routers of same type";
        return (EpidemicDecisionEngine) ((DecisionEngineRouter) otherRouter).getDecisionEngine();
    }
}
