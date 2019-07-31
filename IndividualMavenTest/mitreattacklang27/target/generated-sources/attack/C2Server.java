package attack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertTrue;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import core.Defense;
public class C2Server extends Asset {
   public InternalNetwork internalNetwork;
   public Computer computer;

   public DataExfiltrated dataExfiltrated;
   public NetworkDataTransferThresholdAlerts networkDataTransferThresholdAlerts;

   public C2Server(Boolean networkDataTransferThresholdAlertsState) {
      super();
      if (networkDataTransferThresholdAlerts != null) {
         AttackStep.allAttackSteps.remove(networkDataTransferThresholdAlerts.disable);
      }
      Defense.allDefenses.remove(networkDataTransferThresholdAlerts);
      networkDataTransferThresholdAlerts = new NetworkDataTransferThresholdAlerts(this.name, networkDataTransferThresholdAlertsState);
      AttackStep.allAttackSteps.remove(dataExfiltrated);
      dataExfiltrated = new DataExfiltrated(this.name);
      assetClassName = "C2Server";
   }

   public C2Server(String name, Boolean networkDataTransferThresholdAlertsState) {
      super(name);
      if (networkDataTransferThresholdAlerts != null) {
         AttackStep.allAttackSteps.remove(networkDataTransferThresholdAlerts.disable);
      }
      Defense.allDefenses.remove(networkDataTransferThresholdAlerts);
      networkDataTransferThresholdAlerts = new NetworkDataTransferThresholdAlerts(this.name, networkDataTransferThresholdAlertsState);
      AttackStep.allAttackSteps.remove(dataExfiltrated);
      dataExfiltrated = new DataExfiltrated(this.name);
      assetClassName = "C2Server";
   }

   public C2Server() {
      this(false);
      assetClassName = "C2Server";
   }

   public C2Server(String name) {
      this(name, false);
      assetClassName = "C2Server";
   }


   public class DataExfiltrated extends AttackStepMax {
   public DataExfiltrated(String name) {
      super(name);
      assetClassName = "C2Server";
   }
@Override
public void setExpectedParents() {
if (computer != null) {
addExpectedParent(computer.exfiltrationOverC2);
}
if (internalNetwork != null) {
addExpectedParent(internalNetwork.networkIntrusionDetection.disable);
}
addExpectedParent(networkDataTransferThresholdAlerts.disable);
}
      @Override
      public double localTtc() {
         return ttcHashMap.get("C2Server.dataExfiltrated");
      }

   }

   public class NetworkDataTransferThresholdAlerts extends Defense {
   public NetworkDataTransferThresholdAlerts(String name, Boolean enabled) {
      super(name);
      defaultValue = enabled;
      disable = new Disable(name);
   }

   public class Disable extends AttackStepMin {
         public Disable(String name) {
            super(name);
         }

         @Override
         public String fullName() {
            return "c2Server.networkDataTransferThresholdAlerts";
         }
@Override
public void updateChildren(java.util.Set<AttackStep> activeAttackSteps) {
dataExfiltrated.updateTtc(this, ttc, activeAttackSteps);
}
   }
}

      public void addInternalNetwork(InternalNetwork internalNetwork) {
         this.internalNetwork = internalNetwork;
         internalNetwork.c2Server = this;
      }

      public void addComputer(Computer computer) {
         this.computer = computer;
         computer.c2Server = this;
      }

   @Override
   public String getAssociatedAssetClassName(String roleName) {
      if (roleName.equals("internalNetwork")) {
         return internalNetwork.getClass().getName();
      }
      if (roleName.equals("computer")) {
         return computer.getClass().getName();
      }
      return null;
   }
   @Override
   public java.util.Set<Asset> getAssociatedAssets(String roleName) {
      java.util.Set<Asset> assets = new java.util.HashSet<>();
      if (roleName.equals("internalNetwork")  && internalNetwork != null) {
         assets.add(internalNetwork);
         return assets;
      }
      if (roleName.equals("computer")  && computer != null) {
         assets.add(computer);
         return assets;
      }
      assertTrue("The asset " + this.toString() + " does not feature the role name " + roleName + ".", false);
      return null;
   }
   @Override
   public java.util.Set<Asset> getAllAssociatedAssets() {
      java.util.Set<Asset> assets = new java.util.HashSet<>();
      if (internalNetwork != null) {
         assets.add(internalNetwork);
      }
      if (computer != null) {
         assets.add(computer);
      }
      return assets;
   }
}