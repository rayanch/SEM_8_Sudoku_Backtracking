package com.ulfg.sem8.project.sudoku;

public class SequentialSudokuSolver extends SudokuSolver 
{

    public SequentialSudokuSolver(SudokuGrid grid) {
        super(grid);
    }

    @Override
    public boolean solveGrid() 
    {
        return backtrackSolve(getGrid(), 0);
    }

    @Override
    protected boolean backtrackSolve(SudokuGrid grid, Integer cellIndex) 
    {
        if(emptyCells == null || cellIndex > emptyCells.size())
            return false;
     
        if(cellIndex == emptyCells.size())
        {            
            this.setGrid(grid);
            return true;
        }
        
        grid = (SudokuGrid) grid.clone();
        
        var emptyCell = emptyCells.get(cellIndex);
        var possibleList = grid.getCellPossibleValues(emptyCell);
        
        System.out.println("Possibilities (Index " + cellIndex.toString() + "): " + possibleList.getPossibleValue());
        
        for(var possibility : possibleList.getPossibleValue())
        {
            grid.setCellValue(emptyCell, possibility);
            
            System.out.println(cellIndex);
            System.out.println(grid.textFormatGrid());
            
            
            if(backtrackSolve(grid, cellIndex + 1))
            {
                return true;
            }
        }
        
        return false;
    }
}
