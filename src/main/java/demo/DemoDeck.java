/** Demonstration of the Personalized Deck generator.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     2nd Edition
   Author: 
     Henrik Bærbak Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

package demo;

import hotstone.framework.card.Card;
import hotstone.standard.StandardCard;
import thirdparty.*;

import java.util.ArrayList;

public class DemoDeck {

  public static PersonalDeckReader main(String args) {
    PersonalDeckReader newCards;
    if (args == "Animals"){
      newCards = new PersonalDeckReader("animaldeck.json");
    }
    else if (args == "Norse Gods") {
      newCards = new PersonalDeckReader("allOtherFileNamesAreTheSame.json");
    }
    else {
      newCards = new PersonalDeckReader("allOtherFileNamesAreTheSame.json");
      return newCards;
    }
    return newCards;
  }
}
