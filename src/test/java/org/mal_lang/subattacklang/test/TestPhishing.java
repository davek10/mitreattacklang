package org.mal_lang.subattacklang.test;

import core.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class TestPhishing{

    @Test
    public void testPhishing1() {
        //create assets
        Attacker attacker = new Attacker();
        User user = new User("user1");
        Computer computer = new Computer();
        OS os = new OS("os");

        //create associations
        user.addComputer(computer);
        computer.addOs(os);

        //create attacksteps
        attacker.addAttackPoint(user.phishing);
        attacker.attack();

        //create assertions
        os.userRights.assertCompromisedInstantaneouslyFrom(user.phishing);
    }

    @AfterEach
    public void deleteModel() {
        // Clean up before each test
        Asset.allAssets.clear();
        AttackStep.allAttackSteps.clear();
        Defense.allDefenses.clear();
    }

}
