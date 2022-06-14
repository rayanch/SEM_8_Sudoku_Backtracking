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
        
        ProjectMain.logger.log("SequentialSudokuSolver.backtrackSolve", 
                "Possibilities (Index " + cellIndex.toString() + "): " 
                        + possibleList.getPossibleValue());
        
        for(var possibility : possibleList.getPossibleValue())
        {
            grid.setCellValue(emptyCell, possibility);
            
            ProjectMain.logger.log("", cellIndex.toString());
            ProjectMain.logger.log("", grid.textFormatGrid());
            
            
            if(backtrackSolve(grid, cellIndex + 1))
                return true;
        }
        
        return false;
    }
}
