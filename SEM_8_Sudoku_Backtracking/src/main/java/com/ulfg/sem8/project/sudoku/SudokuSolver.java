package com.ulfg.sem8.project.sudoku;

import java.util.List;

/*
 *  Plan: 
 *      + We have a list of empty cells
 *      + For backtracking, we have start with the 
 *      + 
 */

public abstract class SudokuSolver 
{
    private SudokuGrid sudokuGrid; 
    protected final List<SudokuGrid.CellIndex> emptyCells;
    
    protected SudokuSolver(SudokuGrid grid)
    {
        sudokuGrid = grid;
        
        if(sudokuGrid != null)
            emptyCells = sudokuGrid.getEmptyCells();
        else
            emptyCells = null;
    }
    
    public boolean solveGrid()
    {
        return solveGrid(false);
    }
    
    public abstract boolean solveGrid(boolean checkGrid);
    
    // i: the index in the empty cells list
    // When grid is solved set sudokuGrid to it
    protected abstract boolean backtrackSolve(SudokuGrid grid, Integer cellIndex); 
    
    protected void setGrid(SudokuGrid grid)
    {
        sudokuGrid = grid;
    }
    
    public SudokuGrid getGrid()
    {
        return sudokuGrid;
    }
}
