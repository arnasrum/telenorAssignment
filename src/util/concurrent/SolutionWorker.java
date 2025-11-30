package src.util.concurrent;

import src.Solution;

public class SolutionWorker implements Runnable {

    int start, end;
    Solution solution;

    public SolutionWorker(Solution solution, int start, int end) {
        this.start = start;
        this.end = end;
        this.solution = solution;
    }

    @Override
    public void run() {
        solution.calculate(start, end);
    }


}