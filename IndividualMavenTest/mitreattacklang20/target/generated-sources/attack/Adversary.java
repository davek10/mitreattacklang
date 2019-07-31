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
public class Adversary extends Asset {
   public java.util.Set<Employee> employee = new HashSet<>();
   public java.util.Set<Browser> browser = new HashSet<>();

   public SpearphishingLinkAttack spearphishingLinkAttack;

   public Adversary() {
      super();
      AttackStep.allAttackSteps.remove(spearphishingLinkAttack);
      spearphishingLinkAttack = new SpearphishingLinkAttack(this.name);
      assetClassName = "Adversary";
   }

   public Adversary(String name) {
      super(name);
      AttackStep.allAttackSteps.remove(spearphishingLinkAttack);
      spearphishingLinkAttack = new SpearphishingLinkAttack(this.name);
      assetClassName = "Adversary";
   }


   public class SpearphishingLinkAttack extends AttackStepMin {
   public SpearphishingLinkAttack(String name) {
      super(name);
      assetClassName = "Adversary";
   }
@Override
public void updateChildren(java.util.Set<AttackStep> activeAttackSteps) {
for (Employee employee_KyghC : employee) {
employee_KyghC.spearphishingLinkClicked.updateTtc(this, ttc, activeAttackSteps);
}
for (Browser browser_IfPGh : browser) {
browser_IfPGh.spearphishingLink.updateTtc(this, ttc, activeAttackSteps);
}
}
      @Override
      public double localTtc() {
         return ttcHashMap.get("Adversary.spearphishingLinkAttack");
      }

   }

      public void addEmployee(Employee employee) {
         this.employee.add(employee);
         employee.adversary = this;
      }

      public void addBrowser(Browser browser) {
         this.browser.add(browser);
         browser.adversary = this;
      }

   @Override
   public String getAssociatedAssetClassName(String roleName) {
      if (roleName.equals("employee")) {
         for (Object o: employee) {
            return o.getClass().getName();
         }
      }
      if (roleName.equals("browser")) {
         for (Object o: browser) {
            return o.getClass().getName();
         }
      }
      return null;
   }
   @Override
   public java.util.Set<Asset> getAssociatedAssets(String roleName) {
      java.util.Set<Asset> assets = new java.util.HashSet<>();
      if (roleName.equals("employee")  && employee != null) {
         assets.addAll(employee);
         return assets;
      }
      if (roleName.equals("browser")  && browser != null) {
         assets.addAll(browser);
         return assets;
      }
      assertTrue("The asset " + this.toString() + " does not feature the role name " + roleName + ".", false);
      return null;
   }
   @Override
   public java.util.Set<Asset> getAllAssociatedAssets() {
      java.util.Set<Asset> assets = new java.util.HashSet<>();
      assets.addAll(employee);
      assets.addAll(browser);
      return assets;
   }
}