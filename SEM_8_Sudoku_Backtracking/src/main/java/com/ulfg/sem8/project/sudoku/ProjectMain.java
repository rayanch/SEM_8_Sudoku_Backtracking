package com.ulfg.sem8.project.sudoku;

import com.ulfg.sem8.project.util.Logger;
import java.util.HashMap;
import java.util.Map;

public class ProjectMain {

    public static Logger logger;
    public static Map<String, String> grids;
    
    public static void main(String[] args) {
        
        initGrids();
        logger = new Logger();
        logger.mute(); // Comment this to show backtracking steps
        
        SudokuGrid sg = new SudokuGrid(9);
        sg.fromString(grids.get("empty"));
        SudokuSolver solver = new SequentialSudokuSolver(sg);
        if(solver.solveGrid(false))
            System.out.println(solver.getGrid().textFormatGrid());
        else
            System.out.println("The grid is not valid!");
    }
    
    private static void initGrids()
    {
        /*
        Solution hard:
            3 1 6  5 7 8  4 9 2 
            5 2 9  1 3 4  7 6 8 
            4 8 7  6 2 9  5 3 1
        
            2 6 3  4 1 5  9 8 7 
            9 7 4  8 6 3  1 2 5 
            8 5 1  7 9 2  6 4 3 
        
            1 3 8  9 4 7  2 5 6 
            6 9 2  3 5 1  8 7 4 
            7 4 5  2 8 6  3 1 9 
        */
        
        /*
        Solution empty:
            4 8 3  7 2 1  5 9 6
            9 5 6  8 3 4  7 2 1
            1 2 7  9 5 6  3 4 8
        
            6 7 8  4 9 2  1 3 5
            3 4 5  1 8 7  2 6 9
            2 1 9  3 6 5  4 8 7
        
            5 3 4  6 1 8  9 7 2
            8 9 1  2 7 3  6 5 4
            7 6 2  5 4 9  8 1 3
        */
        
        /*
        Solution empty_2:
            2 6 4  7 1 5  8 3 9
            1 3 7  8 9 2  6 4 5
            5 9 8  4 3 6  2 7 1
        
            4 2 3  1 7 8  5 9 6
            8 1 6  5 4 9  7 2 3
            7 5 9  6 2 3  4 1 8

            3 7 5  2 8 1  9 6 4
            9 8 2  3 6 4  1 5 7
            6 4 1  9 5 7  3 8 2
        */
        
        grids = new HashMap<>();
        grids.put("hard",   "3, 0, 6, 5, 0, 8, 4, 0, 0," + 
                            "5, 2, 0, 0, 0, 0, 0, 0, 0," +
                            "0, 8, 7, 0, 0, 0, 0, 3, 1," +
                            "0, 0, 3, 0, 1, 0, 0, 8, 0," +
                            "9, 0, 0, 8, 6, 3, 0, 0, 5," +
                            "0, 5, 0, 0, 9, 0, 6, 0, 0," +
                            "1, 3, 0, 0, 0, 0, 2, 5, 0," +
                            "0, 0, 0, 0, 0, 0, 0, 7, 4," +
                            "0, 0, 5, 2, 0, 6, 3, 0, 0"); // 1.386s
        
        grids.put("expert", "0, 0, 0, 0, 7, 0, 1, 0, 0," +
                            "0, 0, 0, 5, 6, 0, 0, 0, 0," +
                            "0, 8, 0, 0, 2, 0, 0, 3, 0," +
                            "0, 0, 0, 0, 0, 0, 4, 9, 0," +
                            "0, 4, 0, 2, 5, 0, 0, 0, 8," +
                            "5, 0, 0, 9, 0, 0, 0, 0, 6," +
                            "4, 0, 6, 0, 0, 0, 0, 0, 0," +
                            "2, 0, 0, 0, 0, 0, 0, 0, 0," +
                            "7, 0, 0, 1, 9, 0, 8, 0, 0"); // 1.31s
        
        grids.put("empty", "0, 0, 0, 0, 0, 1, 0, 0, 0," +
                            "0, 0, 0, 0, 0, 0, 0, 0, 0," +
                            "0, 0, 0, 0, 0, 6, 0, 0, 0," +
                            "0, 0, 0, 4, 0, 0, 0, 0, 0," +
                            "0, 0, 0, 0, 8, 0, 0, 0, 0," +
                            "2, 0, 9, 0, 0, 0, 0, 0, 7," +
                            "0, 0, 0, 0, 0, 0, 0, 0, 0," +
                            "0, 0, 0, 0, 0, 3, 0, 0, 0," +
                            "0, 0, 0, 0, 0, 0, 0, 0, 0"); // 1.4s
        
        grids.put("empty2", "0, 0, 0, 7, 0, 0, 0, 0, 0," +
                            "1, 0, 0, 0, 0, 0, 0, 0, 0," +
                            "0, 0, 0, 4, 3, 0, 2, 0, 0," +
                            "0, 0, 0, 0, 0, 0, 0, 0, 6," +
                            "0, 0, 0, 5, 0, 9, 0, 0, 0," +
                            "0, 0, 0, 0, 0, 0, 4, 1, 8," +
                            "0, 0, 0, 0, 8, 1, 0, 0, 0," +
                            "0, 0, 2, 0, 0, 0, 0, 5, 0," +
                            "0, 4, 0, 0, 0, 0, 3, 0, 0"); // 1min
        
        grids.put("hard2",  "8, 0, 0, 0, 0, 0, 0, 0, 0," +
                            "0, 0, 3, 6, 0, 0, 0, 0, 0," +
                            "0, 7, 0, 0, 9, 0, 2, 0, 0," +
                            "0, 5, 0, 0, 0, 7, 0, 0, 0," +
                            "0, 0, 0, 0, 4, 5, 7, 0, 0," +
                            "0, 0, 0, 1, 0, 0, 0, 3, 0," +
                            "0, 0, 1, 0, 0, 0, 0, 6, 8," +
                            "0, 0, 8, 5, 0, 0, 0, 1, 0," +
                            "0, 9, 0, 0, 0, 0, 4, 0, 0"); // 1.74s
    }
}
