package org.mal_lang.subattacklang.test;

import core.Attacker;
import core.AttackStep;
import core.Asset;
import core.Defense;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class TestPhishing{

    @Test
    public void testSpearphishingAttachment() {

        //create assets

        Attacker attacker = new Attacker();
        User user1 = new User("user1");
        Account userAcc = new Account("account1");
        Computer computer1 = new Computer("computer1");
        OS os1 = new OS("os1");

        //create associations
        user1.addAccount(userAcc);
        computer1.addOs(os1);
        os1.addAccount(userAcc);

        //create attacksteps
        attacker.addAttackPoint(user1.phishing);
        attacker.attack();

        //create assertions
        os1.userRights.assertCompromisedInstantaneously();
    }

  @AfterEach
  public void deleteModel() {
      // Clean up before each test
      Asset.allAssets.clear();
      AttackStep.allAttackSteps.clear();
      Defense.allDefenses.clear();
  }

}
