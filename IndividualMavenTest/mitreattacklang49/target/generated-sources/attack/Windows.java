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
public class Windows extends Asset {
   public java.util.Set<User> user = new HashSet<>();
   public InternalNetwork internalNetwork;

   public NetworkShareDiscovery networkShareDiscovery;
   public Whitelisting whitelisting;

   public Windows(Boolean whitelistingState) {
      super();
      if (whitelisting != null) {
         AttackStep.allAttackSteps.remove(whitelisting.disable);
      }
      Defense.allDefenses.remove(whitelisting);
      whitelisting = new Whitelisting(this.name, whitelistingState);
      AttackStep.allAttackSteps.remove(networkShareDiscovery);
      networkShareDiscovery = new NetworkShareDiscovery(this.name);
      assetClassName = "Windows";
   }

   public Windows(String name, Boolean whitelistingState) {
      super(name);
      if (whitelisting != null) {
         AttackStep.allAttackSteps.remove(whitelisting.disable);
      }
      Defense.allDefenses.remove(whitelisting);
      whitelisting = new Whitelisting(this.name, whitelistingState);
      AttackStep.allAttackSteps.remove(networkShareDiscovery);
      networkShareDiscovery = new NetworkShareDiscovery(this.name);
      assetClassName = "Windows";
   }

   public Windows() {
      this(false);
      assetClassName = "Windows";
   }

   public Windows(String name) {
      this(name, false);
      assetClassName = "Windows";
   }


   public class NetworkShareDiscovery extends AttackStepMax {
   public NetworkShareDiscovery(String name) {
      super(name);
      assetClassName = "Windows";
   }
@Override
public void setExpectedParents() {
for (User user_gBLfU : user) {
addExpectedParent(user_gBLfU.userRights);
}
addExpectedParent(whitelisting.disable);
}
@Override
public void updateChildren(java.util.Set<AttackStep> activeAttackSteps) {
if (internalNetwork != null) {
internalNetwork.networkShareDiscovery.updateTtc(this, ttc, activeAttackSteps);
}
}
      @Override
      public double localTtc() {
         return ttcHashMap.get("Windows.networkShareDiscovery");
      }

   }

   public class Whitelisting extends Defense {
   public Whitelisting(String name, Boolean enabled) {
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
            return "windows.whitelisting";
         }
@Override
public void updateChildren(java.util.Set<AttackStep> activeAttackSteps) {
networkShareDiscovery.updateTtc(this, ttc, activeAttackSteps);
}
   }
}

      public void addUser(User user) {
         this.user.add(user);
         user.windows = this;
      }

      public void addInternalNetwork(InternalNetwork internalNetwork) {
         this.internalNetwork = internalNetwork;
         internalNetwork.windows.add(this);
      }

   @Override
   public String getAssociatedAssetClassName(String roleName) {
      if (roleName.equals("user")) {
         for (Object o: user) {
            return o.getClass().getName();
         }
      }
      if (roleName.equals("internalNetwork")) {
         return internalNetwork.getClass().getName();
      }
      return null;
   }
   @Override
   public java.util.Set<Asset> getAssociatedAssets(String roleName) {
      java.util.Set<Asset> assets = new java.util.HashSet<>();
      if (roleName.equals("user")  && user != null) {
         assets.addAll(user);
         return assets;
      }
      if (roleName.equals("internalNetwork")  && internalNetwork != null) {
         assets.add(internalNetwork);
         return assets;
      }
      assertTrue("The asset " + this.toString() + " does not feature the role name " + roleName + ".", false);
      return null;
   }
   @Override
   public java.util.Set<Asset> getAllAssociatedAssets() {
      java.util.Set<Asset> assets = new java.util.HashSet<>();
      assets.addAll(user);
      if (internalNetwork != null) {
         assets.add(internalNetwork);
      }
      return assets;
   }
}
