package creatures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(c.name(), "clorus");
        assertEquals(new Color(34, 0, 231), c.color());
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus(2);
        Plip p = new Plip(2);
        c.attack(p);
        assertEquals(4, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        Clorus cBaby = c.replicate();
        assertEquals(c.energy(), cBaby.energy(), 0.01);
        assertNotEquals(c, cBaby);
    }

    @Test
    public void testChoose() {

        // If there are no empty squares, the Clorus will STAY.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // If any Plips are seen, the Clorus will ATTACK one of them randomly.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> onePlip = new HashMap<Direction, Occupant>();
        onePlip.put(Direction.TOP, new Plip());
        onePlip.put(Direction.BOTTOM, new Empty());
        onePlip.put(Direction.LEFT, new Impassible());
        onePlip.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(onePlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);


        // If the Clorus has energy greater than or equal to one, it will REPLICATE to a random empty square.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> oneEmpty = new HashMap<Direction, Occupant>();
        oneEmpty.put(Direction.TOP, new Empty());
        oneEmpty.put(Direction.BOTTOM, new Impassible());
        oneEmpty.put(Direction.LEFT, new Impassible());
        oneEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(oneEmpty);
        Action unexpected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(unexpected, actual);


        // Otherwise, the Clorus will MOVE to a random empty square.
        c = new Clorus(0.9);
        oneEmpty = new HashMap<Direction, Occupant>();
        oneEmpty.put(Direction.TOP, new Empty());
        oneEmpty.put(Direction.BOTTOM, new Impassible());
        oneEmpty.put(Direction.LEFT, new Impassible());
        oneEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(oneEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected, actual);

    }
}
