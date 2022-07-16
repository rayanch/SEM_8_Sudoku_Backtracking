package com.ulfg.sem8.project.sudoku;

import java.util.List;
import java.util.concurrent.Semaphore;

public class ConcurrentSudokuSolver extends SudokuSolver 
{
    private final Semaphore threadCreationSemaphore;
    private final Semaphore runSemaphore;
    private final Semaphore retSemaphore;
    private final Integer maxThreads;
    private boolean stillRunning, retVal;
    
    public ConcurrentSudokuSolver(SudokuGrid grid, Integer maxThread) 
    {
        super(grid);
        this.maxThreads = maxThread;
        if(maxThreads > 0)
            threadCreationSemaphore = new Semaphore(maxThread);
        else
            threadCreationSemaphore = null;
        
        runSemaphore = new Semaphore(1);
        retSemaphore = new Semaphore(1);
        
        retVal = false;
        setStillRunning(true);
    }
    
    @Override
    public boolean solveGrid(boolean checkGrid) 
    {
        if(checkGrid && !getGrid().isGridValid())
            return false;
        
        synchronized(retSemaphore)
        {
            try {
                retSemaphore.acquire();
            } catch (InterruptedException ex) {
                ProjectMain.logger.log("ConcurrentSudokuSolver.solveGrid", ex.toString());
            }
        } 
        
        OnGridSolutionBranchDone listener = new OnGridSolutionBranchDone() {
            @Override
            public synchronized void gridBranchDone() 
            {
                threadCreationSemaphore.release();
            }

            @Override
            public synchronized void gridSolved(SudokuGrid grid) 
            {
                setStillRunning(false);
                gridBranchDone();
                setGrid(grid);
                retVal = true;
                retSemaphore.release();
            }
        };    
        
        new SudokuBranchThread(getGrid(), 0, listener).start();
        
        synchronized(retSemaphore)
        {
            try {
            retSemaphore.acquire();
            } catch (InterruptedException ex) {
                ProjectMain.logger.log("ConcurrentSudokuSolver.solveGrid", ex.toString());
            }
        }     
        
        return retVal;
    }
    
    private boolean isStillRunning()
    {
        try {
            runSemaphore.acquire();
            boolean ret = stillRunning;
            runSemaphore.release();
            return ret;
        } catch (InterruptedException ex) {
            ProjectMain.logger.log("ConcurrentSudokuSolver.isStillRunning", 
                                        "Couldn't lock/unlock runSemaphore");
            return false;
        }
    }
    
    private void setStillRunning(boolean value)
    {
        try {
            runSemaphore.acquire();
            stillRunning = value;
            runSemaphore.release();
        } catch (InterruptedException ex) {
            ProjectMain.logger.log("ConcurrentSudokuSolver.setStillRunning", 
                                        "Couldn't lock/unlock runSemaphore");
        }
    }
    
    public interface OnGridSolutionBranchDone
    {
        void gridBranchDone();
        void gridSolved(SudokuGrid grid);
    }
    
    public class SudokuBranchThread extends Thread 
    {
        private final SudokuGrid grid;
        private final Integer cellIndex;
        private final OnGridSolutionBranchDone onGridSolved;

        public SudokuBranchThread(SudokuGrid grid, 
                Integer cellIndex, OnGridSolutionBranchDone listener)
        {
            this.grid = grid;
            this.cellIndex = cellIndex;
            this.onGridSolved = listener;
        }

        @Override
        public void run() 
        {
            if(!isStillRunning() 
                    || SudokuSolver.emptyCells == null 
                    || cellIndex > SudokuSolver.emptyCells.size())
            {
                onGridSolved.gridBranchDone();
                return;
            }

            if(cellIndex == SudokuSolver.emptyCells.size())
            {
                onGridSolved.gridSolved(this.grid);
                return;
            }

            /*
            Steps:
              
            */

           
            SudokuGrid.CellIndex emptyCell = SudokuSolver.emptyCells.get(cellIndex);

            List<Character> possibleList 
                    = grid.getCellPossibleValues(emptyCell).getPossibleValue();

            ProjectMain.logger.log(Thread.currentThread().getName() 
                    + " SudokuBranchThread.run", 
                    "Possibilities (Index " + cellIndex.toString() + "): " 
                            + possibleList);
            
            for(Character possibility : possibleList)
            {
                // Setup threads
                SudokuGrid gridCopy;

                synchronized (grid)
                {
                    gridCopy = (SudokuGrid) grid.clone();                
                }

                gridCopy.setCellValue(emptyCell, possibility);

                ProjectMain.logger.log("", cellIndex.toString());

                try {
                    threadCreationSemaphore.acquire();
                    SudokuBranchThread thread = 
                            new SudokuBranchThread(
                                    grid, cellIndex + 1, onGridSolved
                            );
                    thread.start();
                } catch (InterruptedException ex) {
                    ProjectMain.logger.log("SudokuBranchThread.run", 
                            "Couldn't create thread!");
                }
            }
            
            onGridSolved.gridBranchDone();
        }
    }

}
