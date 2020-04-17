package org.mal_lang.subattacklang.test;

import core.Attacker;
import core.AttackStep;
import core.Asset;
import core.Defense;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class TestPhishing2{

    @Test
    public void testSpearphishingAttachment2() {

        //create assets

        Attacker attacker = new Attacker(true);

        User user1 = new User("user1");
        User user2 = new User("user2");

        Computer computer1 = new Computer("computer1");
        Computer computer2 = new Computer("computer2");

        OS os1 = new OS("os1");
        OS os2 = new OS("os2");

        //create associations
        user1.addComputer(computer1);
        computer1.addOs(os1);

        user2.addComputer(computer2);
        computer2.addOs(os2);

        //create attacksteps
        attacker.addAttackPoint(user1.phishing);
        attacker.attack();

        //create assertions
        os2.userRights.assertUncompromised();
    }

  @AfterEach
  public void deleteModel() {
      // Clean up before each test
      Asset.allAssets.clear();
      AttackStep.allAttackSteps.clear();
      Defense.allDefenses.clear();
  }

}
