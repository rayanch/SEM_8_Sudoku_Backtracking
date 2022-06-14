package com.ulfg.sem8.project.sudoku;

import com.ulfg.sem8.project.sudoku.UI.MainFrame;

public class ProjectMain {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        /*MainFrame mf = new MainFrame("Test");
        mf.setVisible(true);*/
        String str =    "8, 0, 0, 0, 0, 0, 0, 0, 0 ," +
                        " 0, 0, 3, 6, 0, 0, 0, 0, 0," +
                        "0, 7, 0, 0, 9, 0, 2, 0, 0," +
                        "0, 5, 0, 0, 0, 7, 0, 0, 0," +
                        "0, 0, 0, 0, 4, 5, 7, 0, 0," +
                        "0, 0, 0, 1, 0, 0, 0, 3, 0," +
                        "0, 0, 1, 0, 0, 0, 0, 6, 8," +
                        "0, 0, 8, 5, 0, 0, 0, 1, 0," +
                        "0, 9, 0, 0, 0, 0, 4, 0, 0 ";
        
        SudokuGrid sg = new SudokuGrid(9);
        sg.fromString(str);
        var possibilities = sg.getCellPossibleValues(new SudokuGrid.CellIndex(2, 3, 9));
        System.out.println(possibilities.getPossibleValue());
    }
}
