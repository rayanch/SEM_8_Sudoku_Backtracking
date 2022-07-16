package com.ulfg.sem8.project.sudoku;

public class SequentialSudokuSolver extends SudokuSolver 
{

    public SequentialSudokuSolver(SudokuGrid grid) {
        super(grid);
    }
    
    @Override
    public boolean solveGrid(boolean check)
    {
        if(check && !getGrid().isGridValid())
            return false;
        return backtrackSolve(getGrid(), 0);
    }

    // i: the index in the empty cells list
    // When grid is solved set sudokuGrid to it
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
        
        SudokuGrid.CellIndex emptyCell = emptyCells.get(cellIndex);
        SudokuGrid.CellPossibleValues possibleList = grid.getCellPossibleValues(emptyCell);
        
        ProjectMain.logger.log("SequentialSudokuSolver.backtrackSolve", 
                "Possibilities (Index " + cellIndex.toString() + "): " 
                        + possibleList.getPossibleValue());
        
        for(Character possibility : possibleList.getPossibleValue())
        {
            grid.setCellValue(emptyCell, possibility);
            
            ProjectMain.logger.log("", cellIndex.toString());
            //ProjectMain.logger.log("", grid.textFormatGrid());
            
            
            if(backtrackSolve(grid, cellIndex + 1))
                return true;
        }
        
        return false;
    }
}
