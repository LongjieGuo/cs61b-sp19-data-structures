package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    List<Vertex> solution;
    DoubleMapPQ<Vertex> pq;
    SolverOutcome outcome;
    HashMap<Vertex, Double> distTo;
    HashMap<Vertex, Vertex> edgeTo;
    double solutionWeight;
    int numStatesExplored;
    Stopwatch sw;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        solution = new ArrayList<>();
        pq = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        numStatesExplored = 0;
        distTo.put(start, 0.0);
        initializeDistTo(input, start);
        pq.add(start, 0 + input.estimatedDistanceToGoal(start, end));
        sw = new Stopwatch();
        while (true) {
            Vertex v = pq.removeSmallest();
            if (v.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                break;
            }
            numStatesExplored += 1;
            double t = sw.elapsedTime() / 1000;
            if (t > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
            for (WeightedEdge<Vertex> e : input.neighbors(v)) {
                Vertex p = e.from();
                Vertex q = e.to();
                double w = e.weight();
                if (distTo.get(p) + w < distTo.get(q)) {
                    distTo.replace(q, distTo.get(p) + w);
                    edgeTo.put(q, p);
                    if (pq.contains(q)) {
                        pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    } else {
                        pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
            if (pq.size() == 0) {
                outcome = SolverOutcome.UNSOLVABLE;
                break;
            }
        }
        if (outcome.equals(SolverOutcome.SOLVED)) {
            Vertex v = end;
            while (v != start) {
                solution.add(v);
                v = edgeTo.get(v);
            }
            solution.add(start);
            Collections.reverse(solution);
            solutionWeight = distTo.get(end);
        }
    }

    private void initializeDistTo(AStarGraph<Vertex> input, Vertex start) {
        for (WeightedEdge<Vertex> e : input.neighbors(start)) {
            if (!distTo.containsKey(e.to())) {
                distTo.put(e.to(), Double.MAX_VALUE);
                initializeDistTo(input, e.to());
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        if (outcome.equals(SolverOutcome.SOLVED)) {
            return solutionWeight;
        }
        return 0.0;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return sw.elapsedTime() / 1000;
    }
}
