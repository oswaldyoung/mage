/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.client.util;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.view.DeckView;
import mage.view.SimpleCardView;
import org.apache.log4j.Logger;

/**
 * Utility class for decks.
 *
 * @author nantuko
 */
public final class DeckUtil {

    private static final Logger log = Logger.getLogger(DeckUtil.class);

    private DeckUtil() {
    }

    public static Deck construct(DeckView view) {
        Deck deck = new Deck();
        for (SimpleCardView cardView : view.getCards().values()) {
            CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
            Card card = cardInfo != null ? cardInfo.getMockCard() : null;
            if (card != null) {
                deck.getCards().add(card);
            } else {
                log.fatal("(Deck constructing) Couldn't find card: set=" + cardView.getExpansionSetCode() + ", cid=" + Integer.valueOf(cardView.getCardNumber()));
            }
        }
        for (SimpleCardView cardView : view.getSideboard().values()) {
            CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
            Card card = cardInfo != null ? cardInfo.getMockCard() : null;
            if (card != null) {
                deck.getSideboard().add(card);
            } else {
                log.fatal("(Deck constructing) Couldn't find card: set=" + cardView.getExpansionSetCode() + ", cid=" + Integer.valueOf(cardView.getCardNumber()));
            }
        }
        return deck;
    }
}
