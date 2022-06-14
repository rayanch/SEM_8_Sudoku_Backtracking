package com.ulfg.sem8.project.sudoku;

public class ProjectMain {

    public static void main(String[] args) {
        /*MainFrame mf = new MainFrame("Test");
        mf.setVisible(true);*/
        
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
        
        String hard =   "3, 0, 6, 5, 0, 8, 4, 0, 0," + 
                        "5, 2, 0, 0, 0, 0, 0, 0, 0," +
                        "0, 8, 7, 0, 0, 0, 0, 3, 1," +
                        "0, 0, 3, 0, 1, 0, 0, 8, 0," +
                        "9, 0, 0, 8, 6, 3, 0, 0, 5," +
                        "0, 5, 0, 0, 9, 0, 6, 0, 0," +
                        "1, 3, 0, 0, 0, 0, 2, 5, 0," +
                        "0, 0, 0, 0, 0, 0, 0, 7, 4," +
                        "0, 0, 5, 2, 0, 6, 3, 0, 0";
        
        String hard_half_solved =   
                        "3, 1, 6, 5, 7, 8, 4, 9, 2," + 
                        "5, 2, 9, 1, 3, 4, 7, 6, 8," +
                        "4, 8, 7, 6, 2, 9, 5, 3, 1," +
                        "2, 6, 3, 4, 1, 5, 9, 8, 7," +
                        "9, 7, 4, 8, 6, 3, 1, 2, 5," +
                        "8, 5, 1, 7, 9, 2, 6, 4, 3," +
                        "1, 3, 8, 9, 4, 7, 2, 5, 6," +
                        "6, 9, 2, 3, 5, 1, 0, 7, 4," +
                        "7, 0, 5, 2, 0, 6, 3, 0, 0";
        
        String easy =   "8, 7, 6, 9, 0, 0, 0, 0, 0," + 
                        "0, 1, 0, 0, 0, 6, 0, 0, 0," +
                        "0, 4, 0, 3, 0, 5, 8, 0, 0," +
                        "4, 0, 0, 0, 0, 0, 2, 1, 0," +
                        "0, 9, 0, 5, 0, 0, 0, 0, 0," +
                        "0, 5, 0, 0, 4, 0, 3, 0, 6," +
                        "0, 2, 9, 0, 0, 0, 0, 0, 8," +
                        "0, 0, 4, 6, 9, 0, 1, 7, 3," +
                        "0, 0, 0, 0, 0, 1, 0, 0, 4";
        
        SudokuGrid sg = new SudokuGrid(9);
        sg.fromString(hard_half_solved);
        SudokuSolver solver = new SequentialSudokuSolver(sg);
        solver.solveGrid();
        System.out.println(solver.getGrid().textFormatGrid());
        //System.out.println(sg.textFormatGrid());
    }
}
