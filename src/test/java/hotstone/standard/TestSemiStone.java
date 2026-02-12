/*
 * Copyright (C) 2022. Henrik Bærbak Christensen, Aarhus University.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package hotstone.standard;

/**
 * Skeleton class for AlphaStone test cases

 *    This source code is from the book
 *      "Flexible, Reliable Software:
 *        Using Patterns and Agile Development"
 *      2nd Edition
 *    Author:
 *      Henrik Bærbak Christensen
 *      Department of Computer Science
 *      Aarhus University
 */

import hotstone.framework.*;
import hotstone.framework.hero.Hero;
import hotstone.standard.heroStrategy.HeroStrategyFourRandom;
import hotstone.standard.random.RandomSelectorDouble;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestSemiStone {
    @Test
    public void shouldGiveAllFourHeroesInHeroStrategy() {
        // Given four HeroStrategyFourRandom objects with RandomSelectorDouble objects with seeds 0-3.
        HeroStrategyFourRandom heroStrategy1 = new HeroStrategyFourRandom(new RandomSelectorDouble(0));
        HeroStrategyFourRandom heroStrategy2 = new HeroStrategyFourRandom(new RandomSelectorDouble(1));
        HeroStrategyFourRandom heroStrategy3 = new HeroStrategyFourRandom(new RandomSelectorDouble(2));
        HeroStrategyFourRandom heroStrategy4 = new HeroStrategyFourRandom(new RandomSelectorDouble(3));

        // When the heroes are 'randomly' selected for both FINDUS
        Hero hero1 = heroStrategy1.computeHero(Player.FINDUS);
        Hero hero2 = heroStrategy2.computeHero(Player.FINDUS);
        Hero hero3 = heroStrategy3.computeHero(Player.FINDUS);
        Hero hero4 = heroStrategy4.computeHero(Player.FINDUS);

        // and for PEDDERSEN
        Hero hero5 = heroStrategy1.computeHero(Player.PEDDERSEN);
        Hero hero6 = heroStrategy2.computeHero(Player.PEDDERSEN);
        Hero hero7 = heroStrategy3.computeHero(Player.PEDDERSEN);
        Hero hero8 = heroStrategy4.computeHero(Player.PEDDERSEN);

        // Then FINDUS should get the correct heroes
        assertThat(hero1.getType(), is(GameConstants.THAI_CHEF_HERO_TYPE));
        assertThat(hero2.getType(), is(GameConstants.DANISH_CHEF_HERO_TYPE));
        assertThat(hero3.getType(), is(GameConstants.FRENCH_CHEF_HERO_TYPE));
        assertThat(hero4.getType(), is(GameConstants.ITALIAN_CHEF_HERO_TYPE));

        // and the same for PEDDERSEN
        assertThat(hero5.getType(), is(GameConstants.THAI_CHEF_HERO_TYPE));
        assertThat(hero6.getType(), is(GameConstants.DANISH_CHEF_HERO_TYPE));
        assertThat(hero7.getType(), is(GameConstants.FRENCH_CHEF_HERO_TYPE));
        assertThat(hero8.getType(), is(GameConstants.ITALIAN_CHEF_HERO_TYPE));
    }
}