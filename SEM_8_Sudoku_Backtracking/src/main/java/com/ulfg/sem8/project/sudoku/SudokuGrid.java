package com.ulfg.sem8.project.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SudokuGrid implements Cloneable{

    private final List<Character> grid;
    public static Character[] SYMBOLS = null; 
    private static Integer SIZE = null;
    private static Integer BLOC_SIZE = null;
            
    public SudokuGrid(Integer N)
    {
        grid = new ArrayList<>();
        if(SIZE == null)
            SIZE = N;
        if(BLOC_SIZE == null)
            BLOC_SIZE = (int) Math.sqrt(SIZE);
        if(SYMBOLS == null)
        {
            SYMBOLS = new Character[SIZE];
            generateSymbols();
        }
    }
    
    public SudokuGrid(Integer N, String gridStr)
    {
        grid = new ArrayList<>();
        if(SIZE == null)
            SIZE = N;
        if(BLOC_SIZE == null)
            BLOC_SIZE = (int) Math.sqrt(SIZE);
        if(SYMBOLS == null)
        {
            SYMBOLS = new Character[SIZE];
            generateSymbols();
        }
        fromString(gridStr);
    }
    
    // Copy constructor
    private SudokuGrid(SudokuGrid sGrid)
    {        
        grid = new ArrayList<>();
     
        if(sGrid == null)
            return;
        
        if(sGrid.grid != null)
            for(Character c : sGrid.grid)
                grid.add(c);
    }
    
    private void generateSymbols()
    {
        for(int i = 0; i < SIZE; i++)
            SYMBOLS[i] = (char)((i < 9 ? '1' : ('A' - 9)) + i);
    }
    
    public List<CellIndex> getEmptyCells()
    {
        List<CellIndex> list = new ArrayList<>();
        CellIndex index;
        
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                index = new CellIndex(i, j, SIZE);
                if(grid.get(index.getCombinedIndex()) == '0')
                    list.add(index);
            }
        }
        
        return list;
    }
    
    public SudokuCell getCellValue(CellIndex index)
    {
        SudokuCell ret = null;
        
        if(grid != null && !grid.isEmpty())
            ret = new SudokuCell(index, 
                    grid.get(index.getCombinedIndex()));
            
        return ret;
    }
    
    public void setCellValue(CellIndex index, Character value)
    {
        if(grid != null && index != null && value != 0)
            grid.set(index.getCombinedIndex(), value);
    }
    
    public boolean fromString(String grid)
    {
        grid = grid.replaceAll("\\s+", "");
        
        for(String c : grid.split(","))
        {
            if(c.length() != 1 || 
                    (!c.equals("0") && !isValidSymbol((Character)c.charAt(0))))
                return false;
            
            this.grid.add(c.charAt(0));
        }
        
        return true;
    }
    
    private boolean isValidSymbol(Character sym)
    {
        for(int i = 0; i < SIZE; i++)
            if(SYMBOLS[i].equals(sym))
                return true;
        
        return false;
    }
    
    private boolean isNumberInRow(Integer row, Character value)
    {
        for(int i=0; i < SIZE; i++){  
            if(grid.get(row * SIZE + i).equals(value)){
                return true;
            }
        }
        return false;
    }
 
    private boolean isNumberInColumn(Integer column, Character value)
    {
        for(int i=0; i < SIZE; i++){  
            if(grid.get(i * SIZE + column).equals(value)){
                return true;
            }
        }
        return false;

    }
 
    private boolean isNumberInBlock(CellIndex index, Character value) 
    {
        int localBlockRow = index.getRow() - index.getRow() % BLOC_SIZE;
        int localBlockColumn = index.getColumn() - index.getColumn() % BLOC_SIZE;

        for (int i=localBlockRow;i<localBlockRow+BLOC_SIZE;i++) {
            for (int j=localBlockColumn;j<localBlockColumn+BLOC_SIZE;j++) {
                if (grid.get(i * SIZE + j).equals(value)) {
                  return true;
                }
            }
        }
        return false;
    }
  
    public boolean isValidPlacement(CellIndex index, Character value) 
    {
        return !isNumberInRow(index.getRow(), value) &&
            !isNumberInColumn(index.getColumn(), value) &&
            !isNumberInBlock(index, value);
    }
    
    private Set getMissingFromRow(Integer row)
    {
        List<Character> list = new LinkedList<>(Arrays.asList(SYMBOLS));
        
        for(int i = 0; i < list.size(); i++)
            if(isNumberInRow(row, list.get(i)))
                list.remove(i--);
        
        return new HashSet(list);
    }
    
    private Set getMissingFromColumn(Integer column)
    {
        List<Character> list = new LinkedList<>(Arrays.asList(SYMBOLS));
        
        for(int i = 0; i < list.size(); i++)
            if(isNumberInColumn(column, list.get(i)))
                list.remove(i--);
        
        return new HashSet(list);
    }
    
    private Set getMissingFromBlock(CellIndex index)
    {
        List<Character> list = new LinkedList<>(Arrays.asList(SYMBOLS));
        
        for(int i = 0; i < list.size(); i++)
            if(isNumberInBlock(index, list.get(i)))
                list.remove(i--);
        
        return new HashSet(list);
    }

    @Override
    protected Object clone()
    {
        try {
            super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(SudokuGrid.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new SudokuGrid(this);
    }
    
    public boolean isGridValid()
    {
        CellIndex index;
        for (int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                index = new CellIndex(i, j, SIZE);
                Character value = getCellValue(index).value;
                if(value == '0')
                    continue;
                setCellValue(index, '0');
                if(!isValidPlacement(index, value))
                {
                    setCellValue(index, value);
                    return false;
                }
                setCellValue(index, value);
            }
        }
        return true;
    }
    
    public String textFormatGrid()
    {
        if(grid == null || grid.isEmpty())
            return "";
        
        StringBuilder strBuilder = new StringBuilder();
        
        for(int i = 0; i < SIZE * SIZE; i++)
        {
            strBuilder.append(grid.get(i) != '0' ? grid.get(i) : 'X');
            if(i == 0)
            {
                strBuilder.append(" ");
                continue;
            }  
            
            if((i+1) % SIZE == 0)
            {
                strBuilder.append("\n");
                if((i+1) % (SIZE * BLOC_SIZE) == 0)
                    strBuilder.append("\n");     
            }
            else
            {
                if((i+1) % BLOC_SIZE == 0)
                    strBuilder.append("\t");
                else
                    strBuilder.append(" ");
            }
        }
        
        return strBuilder.toString();
    }
    
    public CellPossibleValues getCellPossibleValues(CellIndex index)
    {
        CellPossibleValues ret = null;
        
        if(grid != null && !grid.isEmpty())
        {
            Set result = getMissingFromRow(index.getRow());
            result.retainAll(getMissingFromColumn(index.getColumn()));
            result.retainAll(getMissingFromBlock(index));

            ret = new CellPossibleValues(index, new ArrayList<>(result));
        }
        return ret;
        
    }
    
    public class CellIndex
    {
        private final Integer column, row, SIZE;

        public CellIndex(Integer row, Integer column, Integer size) {
            this.column = column;
            this.row = row;
            this.SIZE = size;
        }

        public Integer getColumn() {
            return column;
        }

        public Integer getRow() {
            return row;
        }
        
        public Integer getCombinedIndex()
        {
            return row * SIZE + column;
        }
    }
    
    public class SudokuCell
    {
        private final CellIndex index;
        private final Character value;

        public SudokuCell(CellIndex index, Character value) {
            this.index = index;
            this.value = value;
        }

        public CellIndex getIndex() {
            return index;
        }

        public Character getValue() {
            return value;
        }
    }
    
    public class CellPossibleValues
    {
        private final CellIndex index;
        private final List<Character> possibleValues;

        public CellPossibleValues(CellIndex index, List<Character> values) {
            this.index = index;
            possibleValues = values;
        }

        public CellIndex getIndex() {
            return index;
        }

        public List<Character> getPossibleValue() {
            return possibleValues;
        }
    }
}