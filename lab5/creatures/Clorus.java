package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;
import huglife.HugLifeUtils;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        r = 34;
        b = 231;
        g = 0;

        return color(r, g, b);
    }

    public void attack(Creature c) {
        this.energy += c.energy();
    }

    public void move() {
        this.energy -= 0.03;
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public void stay() {
        this.energy += 0.01;
    }

    public Clorus replicate() {
        Clorus offspring = new Clorus(this.energy * 0.5);
        this.energy *= 0.5;
        return offspring;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plips = new ArrayDeque<>();
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            if (entry.getValue().name() == "empty") {
                emptyNeighbors.addLast(entry.getKey());
            } else if (entry.getValue().name() == "plip") {
                plips.addLast(entry.getKey());
            }
        }
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (plips.size() > 0) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plips));
        }

        // Rule 3
        if (this.energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }
}
