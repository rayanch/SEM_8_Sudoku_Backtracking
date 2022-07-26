package com.ulfg.sem8.project.sudoku;

import java.util.List;

public abstract class SudokuSolver 
{
    private SudokuGrid sudokuGrid; 
    protected List<SudokuGrid.CellIndex> emptyCells;
    
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
    
    protected void setGrid(SudokuGrid grid)
    {
        sudokuGrid = grid;
    }
    
    public SudokuGrid getGrid()
    {
        return sudokuGrid;
    }
}
