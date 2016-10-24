public class LLSparseM implements SparseM {

	/*
		LLSparseM is a Matrix which is a container for three types of nodes
		Row Nodes - represent rows
		Column Nodes - represent columns
		Element Notes - represent the elements of the matrix
	*/
	private class ElementNode {

		/*
			ElementNode is the container in my SparseMatrix
			Implementation:
			A nonzero value entered by the user
			Integers representing what row/column it belongs to
			A reference to the next element in the same row/column
		*/
		private int element;
		private int ridx;
		private int cidx;
		private ElementNode nextElementInRow;
		private ElementNode nextElementInColumn;

		public ElementNode (int e, int ri, int ci, ElementNode ner, ElementNode nec) {
			this.element = e;
			this.ridx = ri;
			this.cidx = ci;
			this.nextElementInRow = ner;
			this.nextElementInColumn = nec;
		}

		public void setElement (int e) {
			this.element = e;
		}

		public void setRowIndex (int ri) {
			this.ridx = ri;
		}

		public void setColumnIndex (int ci) {
			this.cidx = ci;
		}

		public void setNextElementinRow (ElementNode ner) {
			this.nextElementInRow = ner;
		}

		public void setNextElementinColumn (ElementNode nec) {
			this.nextElementInColumn = nec;
		}

		public int getElement () {
			return element;
		}

		public int getRowIndex () {
			return ridx;
		}

		public int getColumnIndex () {
			return cidx;
		}

		public ElementNode getNextElementinRow () {
			return nextElementInRow;
		}

		public ElementNode getNextElementinColumn () {
			return nextElementInColumn;
		}
	}

	private class ColumnNode {

		/*
			ColumnNode represents a nonempty column
			It contains an int value specifying which column it is
			Contains an int referring to the number of ElementNodes in the column
			Contains a reference to the first element in the column
			Contains a reference to the next column
		*/
		private int index;
		private int numberOfElementsInColumn; 
		private ColumnNode nextColumn;
		private ElementNode firstElement;

		public ColumnNode (int i, ColumnNode nc, ElementNode ne) {
			this.index = i;
			this.nextColumn = nc;
			this.firstElement = ne;
			numberOfElementsInColumn = 0;
		}

		public void setColumnIndex (int i) {
			this.index = i;
		}

		public void setNextColumn (ColumnNode nc) {
			this.nextColumn = nc;
		}

		public void setFirstElement (ElementNode ne) {
			this.firstElement = ne;
		}

		public int getColumnIndex () {
			return index;
		}

		public ColumnNode getNextColumn () {
			return nextColumn;
		}

		public ElementNode getFirstElement () {
			return firstElement;
		}

		public int getNumberOfElementsInColumn() {
			return numberOfElementsInColumn;
		}

		public void incrementNumberOfElements() {
			numberOfElementsInColumn++;
		}

		public void decremenetNumberOfElements() {
			numberOfElementsInColumn--;
		}
	}

	private class RowNode {

		/*
			RowNode represents a nonempty row
			It contains an int value specifying which row it is
			Contains an int referring to the number of ElementNodes in the row
			Contains a reference to the first element of the row
			Contains a reference to next row
		*/
		private int index;
		private int numberOfElementsInRow;
		private RowNode nextRow;
		private ElementNode firstElement;

		public RowNode (int i, RowNode nr, ElementNode ne) {
			this.index = i;
			this.nextRow = nr;
			this.firstElement = ne;
			numberOfElementsInRow = 0;
		}

		public void setRowIndex (int i) {
			this.index = i;
		}

		public void setNextRow (RowNode nr) {
			this.nextRow = nr;
		}

		public void setFirstElement (ElementNode ne) {
			this.firstElement = ne;
		}

		public int getRowIndex () {
			return this.index;
		}

		public RowNode getNextRow () {
			return this.nextRow;
		}

		public ElementNode getFirstElement () {
			return this.firstElement;
		}

		public int getNumberOfElementsInRow() {
			return numberOfElementsInRow;
		}

		public void incrementNumberOfElements() {
			numberOfElementsInRow++;
		}

		public void decremenetNumberOfElements() {
			numberOfElementsInRow--;
		}
	}

	private RowNode rowHead = null; // This is the start of the row
	private ColumnNode columnHead = null; // This is the start of the column
	final private int totalNumberOfRows; //Counter for the limit of the number of rows  
	final private int totalNumberOfColumns; //Counter for the limit of the number of columns
	private int numberofElements = 0; //Counter for the current number of elements 
	private int numberOfRows = 0; //Counter for the current number of rows
	private int numberofColumns = 0; //Counter for the current number of columns
	
    public LLSparseM (int nr, int nc){
    	this.totalNumberOfRows = nr;
    	this.totalNumberOfColumns = nc;
        return;
    }

    public void incrementNumberOfElements() {
    	numberofElements++;
    }

    public void incrementNumberOfRows() {
    	numberOfRows++;
    }

    public void incrementNumberOfColumns() {
    	numberofColumns++;
    }
    /*
    	Check to see if the index of the row/column is out of bounds
    */
	public boolean outOfBounds(int ridx, int cidx) {
		if ((ridx < 0 || ridx >= totalNumberOfRows) || (cidx < 0 || cidx >= totalNumberOfColumns))
			return true;
		return false;
	}

	@Override
	public int nrows() {
		return totalNumberOfRows;
	}

	@Override
	public int ncols() {
		return totalNumberOfColumns;
	}
	
	/*
		Current number of rows
	*/
	public int cnrows () {
		return numberOfRows;
	}

	/*
		Current number of columnds
	*/
	public int cncols () {
		return numberofColumns;
	}

	@Override
	public int numElements () {
		return numberofElements;
	}

	/*
		Implementation:
		Start at rowHead and columnHead
		Traverse list of rows till you get to appropriate row
		Start at first element of that row
		Traverse through the elements of the row till you get to the right column
		Return value stored there
	*/
	@Override
	public int getElement (int ridx, int cidx) {
		if (outOfBounds(ridx, cidx) || (cnrows() == 0 || cncols() == 0))
			return 0;
		//Traverse row list till you get to the right row
		RowNode currentRow = rowHead;
		while (currentRow != null && currentRow.getRowIndex() < ridx) 
			currentRow = currentRow.getNextRow();
		if (currentRow == null || currentRow.getRowIndex() != ridx)
			return 0;
		ElementNode currentRowElement = currentRow.getFirstElement();
		//Traverse elements within the row
		while (currentRowElement != null && currentRowElement.getColumnIndex() < cidx)
			currentRowElement = currentRowElement.getNextElementinRow();
		if (currentRowElement == null || currentRowElement.getColumnIndex() != cidx)
			return 0;
		else if (currentRowElement.getColumnIndex() == cidx)
			return currentRowElement.getElement();
		return 0;
	}
	/*
		Implementation:
		Start at rowHead and columnHead
		Traverse list of rows till you get to appropriate row
		Traverse list of columns till you get to appropriate column
		Start at first element of the current row
		Traverse through list of elements in row and remove appropriate element
		Start at first element of the current column. 
		Traverse through list of elements in column and remove appropriate element
	*/
	@Override
	public void clearElement (int ridx, int cidx) {
		int alreadydecreased = 0;
		if (outOfBounds(ridx, cidx) || (cnrows() == 0 || cncols() == 0))
			return;
		//Traverse the list of RowNodes until you get to the row with the same ridx as the one requested
		RowNode previousRow = null;
		RowNode currentRow = rowHead;
		while (currentRow != null && currentRow.getRowIndex() < ridx) {
			previousRow = currentRow;
			currentRow = currentRow.getNextRow();
		}
		if (currentRow == null || currentRow.getRowIndex() != ridx)
			return;
		//Traverse the list of ColumnNodes until you get to the column with the same cidx as the one requested
		ColumnNode previousColumn = null;
		ColumnNode currentColumn = columnHead;
		while (currentColumn != null && currentColumn.getColumnIndex() < cidx) {
				previousColumn = currentColumn;
				currentColumn = currentColumn.getNextColumn();
		}
		if (currentColumn == null || currentColumn.getColumnIndex() != cidx)
			return;
		//Traverse through the elements of the row we found earlier till we reach the appropiate column, and then we take it out
		ElementNode previousElement = null;
		ElementNode currentElement = currentRow.getFirstElement();
		while (currentElement != null && currentElement.getColumnIndex() < cidx) {
			previousElement = currentElement;
			currentElement = currentElement.getNextElementinRow();
		}
		//Element does not exist
		if (currentElement == null || currentElement.getColumnIndex() != cidx) 
			return;
		//Row only has one element, and it is the element that needs to be cleared, so we take out the row instead
		else if (previousElement == null && currentElement.getNextElementinRow() == null) {
			//The row that needs to be removed is the rowHead
			if (currentRow == rowHead) {
				rowHead = rowHead.getNextRow();
				numberOfRows--;
				numberofElements--;
				alreadydecreased++;
			}
			//The row that needs to be removed is not the rowHead
			else {
				previousRow.setNextRow(currentRow.getNextRow());
				numberOfRows--;
				numberofElements--;
				alreadydecreased++;
			}
		}
		//Remove the first element of the row
		else if (previousElement == null && currentElement.getNextElementinRow() != null) {
			currentRow.setFirstElement(currentElement.getNextElementinRow());
			numberofElements--;
			alreadydecreased++;
			currentRow.decremenetNumberOfElements();
		}
		//Remove element within the row
		else { 
			previousElement.setNextElementinRow(currentElement.getNextElementinRow());
			numberofElements--; 
			alreadydecreased++;
			currentRow.decremenetNumberOfElements();
		}
		//Traverse through the elements of the column we found earlier till we reach the appropiate row, and then we take it out
		previousElement = null;
		currentElement = currentColumn.getFirstElement();
		while (currentElement != null && currentElement.getRowIndex() < ridx) {
			previousElement = currentElement;
			currentElement = currentElement.getNextElementinColumn();
		}
		//Element does not exist
		if (currentElement == null || currentElement.getRowIndex() != ridx)
			return;
		//Column only has one element, and that is the element to be removed so remove row instead
		else if (previousElement == null && currentElement.getNextElementinColumn() == null) {
			//Column to be removed is the columnHead
			if (currentColumn == columnHead) {
				columnHead = columnHead.getNextColumn();
				numberofColumns--;
				if (alreadydecreased == 0) 
					numberofElements--;
			}
			//Column to be removed is not the columnHead
			else {
				previousColumn.setNextColumn(currentColumn.getNextColumn());
				numberofColumns--;
				if (alreadydecreased == 0)
					numberofElements--;
			}
		}
		//Remove the first element of the row
		else if (previousElement == null && currentElement.getNextElementinColumn() != null) {
			currentColumn.setFirstElement(currentElement.getNextElementinColumn());
			if (alreadydecreased == 0)
				numberofElements--;
			currentColumn.decremenetNumberOfElements();
		}
		//Remove element within the row
		else {
			previousElement.setNextElementinColumn(currentElement.getNextElementinColumn());
			if (alreadydecreased == 0)
				numberofElements--;
			currentColumn.decremenetNumberOfElements();
		}
	}

	/*
		Implementation:
		Start at rowHead and columnHead
		Traverse through list of rows till you get to right row or create it
		Traverse through list of columns till you get to right column or create it
		Traverse through list of elements in row till you get to right element or create it
		Traverse through list of elements in column till you get to right element or create it
	*/
	@Override
	public void setElement (int ridx, int cidx, int val) {
		if (outOfBounds(ridx, cidx))
			return;
		if (val == 0) {
			clearElement(ridx, cidx);
			return;
		}
		//Matrix is empty. Create the row/column and insert the element
		if (cnrows() == 0 && cncols() == 0) {
			ElementNode newNode = new ElementNode(val, ridx, cidx, null, null);
			rowHead = new RowNode(ridx, rowHead, newNode);
			columnHead = new ColumnNode(cidx, columnHead, newNode);
			numberOfRows++;
			numberofColumns++;
			numberofElements++;
			rowHead.incrementNumberOfElements();
			columnHead.incrementNumberOfElements();
			return;
		}
		//Matrix is not empty
		else {
			//Traverse through list of rows
			RowNode previousRow = null;
			RowNode currentRow = rowHead;
			while (currentRow != null && currentRow.getRowIndex() < ridx) {
				previousRow = currentRow;
				currentRow = currentRow.getNextRow();
			}
			//The row we are looking for is not in the list, so we must create it
			if (currentRow == null || currentRow.getRowIndex() != ridx) {
				//New row to be created belongs to the spot before the column head
				if (previousRow == null) {
					RowNode newRow = new RowNode(ridx, currentRow, null);
					rowHead = newRow;
					currentRow = rowHead;
					numberOfRows++;
				}
				// New row to be created is placed within the list of rows
				else {
					RowNode newRow = new RowNode(ridx, currentRow, null);
					previousRow.setNextRow(newRow);
					currentRow = newRow;
					numberOfRows++;
				}
			}
			//Traverse list of columns
			ColumnNode previousColumn = null;
			ColumnNode currentColumn = columnHead;
			while (currentColumn != null && currentColumn.getColumnIndex() < cidx) {
				previousColumn = currentColumn;
				currentColumn = currentColumn.getNextColumn();
			}
			//The column we are looking for is not in the list, so we must create it
			if (currentColumn == null || currentColumn.getColumnIndex() != cidx) {
				//Column to be created belongs to the spot before the column head
				if (previousColumn == null) {
					ColumnNode newColumn = new ColumnNode(cidx, currentColumn, null);
					columnHead = newColumn;
					currentColumn = columnHead;
					numberofColumns++;
				}
				//Column to be created is placed within the list of columns
				else {
					ColumnNode newColumn = new ColumnNode(cidx, currentColumn, null);
					previousColumn.setNextColumn(newColumn);
					currentColumn = newColumn;
					numberofColumns++;
				}
			}
			//A new row and column were created. The right element is placed within them
			if (currentRow.getFirstElement() == null && currentColumn.getFirstElement() == null) {
				ElementNode newElement = new ElementNode(val, ridx, cidx, null, null);
				currentRow.setFirstElement(newElement);
				currentColumn.setFirstElement(newElement);
				numberofElements++;
				currentRow.incrementNumberOfElements();
				currentColumn.incrementNumberOfElements();
				return;
			}
			//A new row was created, and we are using a preexisting column. The right element is placed within them
			else if (currentRow.getFirstElement() == null && currentColumn.getFirstElement() != null) {
				ElementNode newElement = new ElementNode(val, ridx, cidx, null, null);
				currentRow.setFirstElement(newElement);
				currentRow.incrementNumberOfElements();
				//Traverse the list of elements within the column
				ElementNode previousElement = null;
				ElementNode currentElement = currentColumn.getFirstElement();
				while (currentElement != null && currentElement.getRowIndex() < ridx) {
					previousElement = currentElement;
					currentElement = currentElement.getNextElementinColumn();
				}
				//Element does not exist, so we must place it appropriately in here
				if (currentElement == null || currentElement.getRowIndex() != ridx) {
					//Element to be placed into the first position in the column
					if (previousElement == null) {
						currentColumn.setFirstElement(newElement);
						newElement.setNextElementinColumn(currentElement);
						numberofElements++;
						currentColumn.incrementNumberOfElements();
						return;
					}
					//Element to be placed within the list
					else {
						previousElement.setNextElementinColumn(newElement);
						newElement.setNextElementinColumn(currentElement);
						numberofElements++;
						currentColumn.incrementNumberOfElements();
						return;
					}
				}
				//Element exists, so we must update it value
				else {
					currentElement.setElement(val);
					return;
				}
			}
			//We are using a preexisting row and a new column. The right element is placed within them
			else if (currentRow.getFirstElement() != null && currentColumn.getFirstElement() == null) {
				ElementNode newElement = new ElementNode(val, ridx, cidx, null, null);
				currentColumn.setFirstElement(newElement);
				currentColumn.incrementNumberOfElements();
				//Traverse the list of elements within the row
				ElementNode previousElement = null;
				ElementNode currentElement = currentRow.getFirstElement();
				while (currentElement != null && currentElement.getColumnIndex() < cidx) {
					previousElement = currentElement;
					currentElement = currentElement.getNextElementinRow();
				}
				//Element does not exist, so we must place it appropriately in here
				if (currentElement == null || currentElement.getColumnIndex() != cidx) {
					//Element to be placed into the first position in the column
					if (previousElement == null) {
						currentRow.setFirstElement(newElement);
						newElement.setNextElementinRow(currentElement);
						numberofElements++;
						currentRow.incrementNumberOfElements();
						return;
					}
					//Element to be placed within the list
					else {
						previousElement.setNextElementinRow(newElement);
						newElement.setNextElementinRow(currentElement);
						numberofElements++;
						currentRow.incrementNumberOfElements();
						return;
					}
				}
				//Element exists, so we must update its value
				else {
					currentElement.setElement(val);
					return;
				}
			}
			//Both the row and column being used are preexisting, so we must go through their elements, and place the element appropriately
			else {
				ElementNode newElement = new ElementNode(val, ridx, cidx, null, null);
				ElementNode previousElement = null;
				ElementNode currentElement = currentColumn.getFirstElement();
				currentColumn.incrementNumberOfElements();
				currentRow.incrementNumberOfElements();
				numberofElements++;
				//Traverse through elements in the column
				while (currentElement != null && currentElement.getRowIndex() < ridx) {
					previousElement = currentElement;
					currentElement = currentElement.getNextElementinColumn();
				}
				//Element does not exist in column. Must be created and placed appropriately
				if (currentElement == null || currentElement.getRowIndex() != ridx) {
					//Element to be placed in the first position of the column
					if (previousElement == null) {
						currentColumn.setFirstElement(newElement);
						newElement.setNextElementinColumn(currentElement);
					}
					//Element to be placed within the column
					else {
						previousElement.setNextElementinColumn(newElement);
						newElement.setNextElementinColumn(currentElement);
					}
				}
				//Element exists. Value must be updated
				else {
					currentElement.setElement(val);
					numberofElements--;
				}
				//Traverse through elements in the row
				previousElement = null;
				currentElement = currentRow.getFirstElement();
				while (currentElement != null && currentElement.getColumnIndex() < cidx) {
					previousElement = currentElement;
					currentElement = currentElement.getNextElementinRow();
				}
				//Element does not exist in row. Must be created and placed appropriately
				if (currentElement == null || currentElement.getColumnIndex() != cidx) {
					//Element to be placed in the first position of the row
					if (previousElement == null) {
						currentRow.setFirstElement(newElement);
						newElement.setNextElementinRow(currentElement);
						return;
					}
					//Element to be placed within the row
					else { 
						previousElement.setNextElementinRow(newElement);
						newElement.setNextElementinRow(currentElement);
						return;
					}
				}
				//Element exists. Value must be updated
				else {
					currentElement.setElement(val);
					return;
				}
			}
		}
	}

	/*
		Implementation:
		Create an array whose size is the number of rows created
		Start at rowHead, place row index inside array, and continue till null
	*/
	@Override
	public int[] getRowIndices () {
		if (cnrows() == 0)
			return new int[0];
		int[] rowIndices = new int[cnrows()];
		RowNode currentRow = rowHead;
		int i = 0;
		while (currentRow != null) {
			rowIndices[i++] = currentRow.getRowIndex();
			currentRow = currentRow.getNextRow();
		}
		return rowIndices;
	}

	/*
		Implementation:
		Create an array whose size is the number of columns created
		Start at columnHead, place column index inside array, and continue till null
	*/
	@Override
	public int[] getColIndices () {
		if (cncols() == 0)
			return new int[0];

		int[] columnIndices = new int[cncols()];
		ColumnNode currentColumn = columnHead;
		int i = 0;
		while (currentColumn != null) {
			columnIndices[i++] = currentColumn.getColumnIndex();
			currentColumn = currentColumn.getNextColumn();
		}
		return columnIndices;
	}

	/*
		Implementation:
		Start at rowHead
		Traverse list of rows till you reach right row
		Create an array whose size is the number of elements within that row
		Traverse list of elements in that row, storing the column indices on the way to null
	*/
	@Override
	public int[] getOneRowColIndices (int ridx) {
		if ((ridx < 0 || ridx >= totalNumberOfRows) || (cnrows() == 0 || cncols() == 0))
			return null;
		RowNode currentRow = rowHead;
		while (currentRow != null && currentRow.getRowIndex() < ridx) 
			currentRow = currentRow.getNextRow();
		if (currentRow == null || currentRow.getRowIndex() != ridx)
			return null;
		ElementNode currentRowElement = currentRow.getFirstElement();
		int[] columnIndices = new int[currentRow.getNumberOfElementsInRow()];
		int i = 0;
		while (currentRowElement != null) {
			columnIndices[i++] = currentRowElement.getColumnIndex();
			currentRowElement = currentRowElement.getNextElementinRow();
		}
		return columnIndices;
	}

	/*
		Implementation:
		Start at rowHead
		Traverse list of rows till you reach right row
		Create an array whose size is the number of elements within that row
		Traverse list of elements in that row, storing the values of the elements on the way to null
	*/
	@Override
	public int[] getOneRowValues (int ridx) {
		if ((ridx < 0 || ridx >= totalNumberOfRows) || (cnrows() == 0 || cncols() == 0))
			return null;
		RowNode currentRow = rowHead;
		while (currentRow != null && currentRow.getRowIndex() < ridx) 
			currentRow = currentRow.getNextRow();
		if (currentRow == null || currentRow.getRowIndex() != ridx)
			return null;
		ElementNode currentRowElement = currentRow.getFirstElement();
		int[] columnValues = new int[currentRow.getNumberOfElementsInRow()];
		int i = 0;
		while (currentRowElement != null) {
			columnValues[i++] = currentRowElement.getElement();
			currentRowElement = currentRowElement.getNextElementinRow();
		}
		return columnValues;
	}

	/*
		Implementation:
		Start at columnHead
		Traverse list of columns till you reach right column
		Create an array whose size is the number of elements within that column
		Traverse list of elements in that column, storing the row indices on the way to null
	*/
	@Override
	public int[] getOneColRowIndices (int cidx) {
		if ((cidx < 0 || cidx >= totalNumberOfColumns) || (cnrows() == 0 || cncols() == 0))
			return null;
		ColumnNode currentColumn = columnHead;
		while (currentColumn != null && currentColumn.getColumnIndex() < cidx)
			currentColumn = currentColumn.getNextColumn();
		if (currentColumn == null || currentColumn.getColumnIndex() != cidx)
			return null;
		ElementNode currentColumnElement = currentColumn.getFirstElement();
		int[] rowIndices = new int[currentColumn.getNumberOfElementsInColumn()];
		int i = 0;
		while (currentColumnElement != null) {
			rowIndices[i++] = currentColumnElement.getRowIndex();
			currentColumnElement = currentColumnElement.getNextElementinColumn();
		}
		return rowIndices;
	}

	/*
		Implementation:
		Start at columnHead
		Traverse list of columns till you reach right column
		Create an array whose size is the number of elements within that column
		Traverse list of elements in that column, storing the values of the elements on the way to null
	*/
	@Override
	public int[] getOneColValues (int cidx) {
		if ((cidx < 0 || cidx >= totalNumberOfColumns) || (cnrows() == 0 || cncols() == 0))
			return null;
		ColumnNode currentColumn = columnHead;
		while (currentColumn != null && currentColumn.getColumnIndex() < cidx) 
			currentColumn = currentColumn.getNextColumn();
		if (currentColumn == null || currentColumn.getColumnIndex() != cidx)
			return null;
		ElementNode currentColumnElement = currentColumn.getFirstElement();
		int[] rowValues = new int[currentColumn.getNumberOfElementsInColumn()];
		int i = 0;
		while (currentColumnElement != null) {
			rowValues[i++] = currentColumnElement.getElement();
			currentColumnElement = currentColumnElement.getNextElementinColumn();
		}
		return rowValues;
	}

	/*
		Implementation:
		Start at rowHead for both Matrtices
		Depending on which row has the lower value, insert that row
		If rows are equal then add to matrix based on which has lower column value
		O(mn) implementation due to no unneccassary repeats, everything occurs on a structured basis
	*/
	public SparseM addition (SparseM otherM) {
		//If the two matrices arent of the same time, we cannot evaluate their sum
		if (this.nrows() != otherM.nrows() || this.ncols() != otherM.ncols())
			return null;
		//If the original matrix has no rows it means it is empty, so it is sufficient to return the other matrix
		if (this.cnrows() == 0)
			return otherM;
		//if the other matrix has no rows it means it is empty, so it is sufficient to return the original matrix
		if (((LLSparseM) otherM).cnrows() == 0)
			return this;
		//Get the first row in the original matrix
		RowNode originalMatrixRow = this.rowHead;
		//Get the first row in the other matrix
		RowNode otherMatrixRow = ((LLSparseM) otherM).rowHead;
		//Create the matrix which will be the sum of the two matrices
		LLSparseM finalMatrix = new LLSparseM(this.nrows(), this.ncols());
		//Store the first row of the final matrix. It is currently empty but good to know where to start
		RowNode currentFinalMatrixRow = finalMatrix.rowHead;
		//Usage of 2 ColumnNodes to track previous and current columns for easy access for placement or deletion
		ColumnNode previousFinalMatrixColumn = null;
		ColumnNode currentFinalMatrixColumn = finalMatrix.columnHead;
		//Will run as long as one of the matrices still has a row left
		while (originalMatrixRow != null || otherMatrixRow != null) {
			//This is the case where the original matrix has a row left, but the other matrix has reached completetion
			if (originalMatrixRow != null && otherMatrixRow == null) {
				RowNode newRow = new RowNode (originalMatrixRow.getRowIndex(), null, null);
				finalMatrix.incrementNumberOfRows();
				//The matrix is empty
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				//Link the new row to the matrix
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentElementInOriginal = originalMatrixRow.getFirstElement();
				//Iterate through list of elements in the original's row. Will terminate once row has run out of elements
				while (currentElementInOriginal != null) {
					/*
					  Row has just been created, so we must link the first element to the row.
					  Every element after the first is placed at the end of the row.
					*/
					if (currentFinalMatrixRow.getFirstElement() == null) {
						ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRow.setFirstElement(newElement);
						currentFinalMatrixRowElement = newElement;
						currentFinalMatrixRow.incrementNumberOfElements();
						//Locate appropriate column
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						//Either there is currently no column, or the neccessary column does not exist
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							//New column to be placed before the current head
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							//Appropriate column does not exist in the list
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						//Column is found
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							//Iterate through list of elements in the column
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					//The row already has elements inside it
					else if (currentFinalMatrixRow.getFirstElement() != null) {
						ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement.setNextElementinRow(newElement);
						currentFinalMatrixRow.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						//Find appropriate column
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						//Either there are no columns, or the neccessary column does not exist
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							//New column needs to be placed before the current head
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							//Appropriate column does not exist in the list
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						//Column is found
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							//Iterate through list of elements in the column
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					//Go to next element in the row
					currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
				}
				//Row has no elements left. Go to the next row. 
				originalMatrixRow = originalMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
			//The original matrix has no rows remaining, the other matrix still has rows to be looked through
			else if (originalMatrixRow == null && otherMatrixRow != null) {
				RowNode newRow = new RowNode (otherMatrixRow.getRowIndex(), null, null);
				finalMatrix.incrementNumberOfRows();
				//Currently the matrix is empty
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				//The matrix is not empty so add the new row to the matrix
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentElementInOther = otherMatrixRow.getFirstElement();
				//Iterate through list of elements in the other matrix
				while (currentElementInOther != null) {
					//A new row was created, and an element has yet to be inserted. 
					if (currentFinalMatrixRow.getFirstElement() == null) {
						ElementNode newElement = new ElementNode (currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
						currentFinalMatrixRow.setFirstElement(newElement);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						currentFinalMatrixRow.incrementNumberOfElements();
						//Get to proper column
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						//Either there exists no column, or the right column does not exist yet
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							//New column begins before the current head
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							//Column to be placed after the head
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						//Column is found
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							//Traverse through list of elements in the column
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					//The row has elements within it
					else if (currentFinalMatrixRow.getFirstElement() != null) {
						ElementNode newElement = new ElementNode (currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement.setNextElementinRow(newElement);
						currentFinalMatrixRow.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						//Get to proper column
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						//Either there exists no column, or the column wanted does not exist
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							//New column needs to be placed before the current head
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							//New column to be placed after the current head
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						//The column is found
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							//Traverse through list of elements in the column
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					//Element has been placed, go to next element in the list
					currentElementInOther = currentElementInOther.getNextElementinRow();
				}
				//We have gone through every element in the row. Move on to the next row
				otherMatrixRow = otherMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
			//Both rows exists. The original row has a lower row index than the other row, so it is placed in the matrix first
			else if (originalMatrixRow.getRowIndex() < otherMatrixRow.getRowIndex()) {
				RowNode newRow = new RowNode (originalMatrixRow.getRowIndex(), null, null);
				finalMatrix.incrementNumberOfRows();
				//Currently there are no rows
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				//Place new row at the end of the list of rows
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentElementInOriginal = originalMatrixRow.getFirstElement();
				//Travese list of elements in the current row
				while (currentElementInOriginal != null) {
					//New row was created and currently contains no elements
					if (currentFinalMatrixRow.getFirstElement() == null) {
						ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRow.setFirstElement(newElement);
						currentFinalMatrixRowElement = newElement;
						currentFinalMatrixRow.incrementNumberOfElements();
						//Get to the right column
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						//Either there exists no column, or the needed column does not exist in the list
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							//New column to be placed before the current head
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							//New column to be placed after the current head
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						//The column is found
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							//Traverse through list of elements in the column
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					//Row is not empty
					else if (currentFinalMatrixRow.getFirstElement() != null) {
						ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement.setNextElementinRow(newElement);
						currentFinalMatrixRow.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						//Locate the right column
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						//Either there exists no column, or the needed column does not exist
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							//The new column to be placed before the head of the list
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							//The new column to be placed after the head
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						//The column is found
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							//Traverse through list of elements in the column
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					//we have placed the current element. Move on to next element in the list
					currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
				}
				//We have gone through all the elements in the row. Move on to the next row
				originalMatrixRow = originalMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
			//Both rows exist. The row index for the other row has a lower value than original row, so it is placed first
			else if (otherMatrixRow.getRowIndex() < originalMatrixRow.getRowIndex()) {
				RowNode newRow = new RowNode (otherMatrixRow.getRowIndex(), null, null);
				finalMatrix.incrementNumberOfRows();
				//There are no rows
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				//New row is placed at the end of the list of rows
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentElementInOther = otherMatrixRow.getFirstElement();
				//Traverse through list of elements in the row
				while (currentElementInOther != null) {
					//New row was created, currently contains no elements
					if (currentFinalMatrixRow.getFirstElement() == null) {
						ElementNode newElement = new ElementNode (currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
						currentFinalMatrixRow.setFirstElement(newElement);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						currentFinalMatrixRow.incrementNumberOfElements();
						//Locate correct column
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						//Either there exists no column, or the correct column is not in the list
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							//The new column is to be placed before the head of the list of columns
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							//The new column is to be placed somewhere after the head
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						//The column is found
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							//Traverse through list of elements in the column
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					//The row is not empty
					else if (currentFinalMatrixRow.getFirstElement() != null) {
						ElementNode newElement = new ElementNode (currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement.setNextElementinRow(newElement);
						currentFinalMatrixRow.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						//Locate right column
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						//Either there exists no columns, or the right column is not in the list
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							//The new column is to be placed before the head
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							//The new column is to be placed somewhere after the head
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						//The column is found
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							//Traverse through list of elements in the column
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					//We have placed the element in the matrix. Move to the next element
					currentElementInOther = currentElementInOther.getNextElementinRow();
				}
				//We have gone through all elements in the row. Move to the next row
				otherMatrixRow = otherMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
			//Both rows exist. They have the same row index, so we must go through them both based on the value of the columns
			else if (originalMatrixRow.getRowIndex() == otherMatrixRow.getRowIndex()) {
				ElementNode currentElementInOther = otherMatrixRow.getFirstElement();
				ElementNode currentElementInOriginal = originalMatrixRow.getFirstElement();
				//Both rows contains only one element, and their sum is zero, so the row does not exist
				if (currentElementInOriginal.getColumnIndex() == currentElementInOther.getColumnIndex() && currentElementInOriginal.getElement() + currentElementInOther.getElement() == 0 && currentElementInOriginal.getNextElementinRow() == null && currentElementInOther.getNextElementinRow() == null) {
					originalMatrixRow = originalMatrixRow.getNextRow();
					otherMatrixRow = otherMatrixRow.getNextRow();
					continue;
				}
				RowNode newRow = new RowNode (otherMatrixRow.getRowIndex(), null, null);
				//Save the previous row of the Matrix. If at the end the row has no elements we go back to the previous row
				RowNode previousFinalRow = currentFinalMatrixRow;
				//There are no rows in the matrix
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				//The new row is placed at the end of the list of rows
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				//Traverse both rows till both are at the end
				while (currentElementInOriginal != null || currentElementInOther != null) {
					//The orginal row has no elements remaining, so we take elements from the other row
					if (currentElementInOriginal == null) {
						//New row has been created. Currently contains no elements
						if (currentFinalMatrixRow.getFirstElement() == null) {
							ElementNode newElement = new ElementNode (currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							currentFinalMatrixRowElement = newElement;
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRow.incrementNumberOfElements();
							//Get right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no column, or the right column is not in the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head of the list
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse list of elements within the column
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
						//The row is not empty
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							ElementNode newElement = new ElementNode (currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							//Locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no column, or the right column is not in the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse through list of elements in the column
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
					}
					//Original row has more elements while other row is at the end of its list
					else if (currentElementInOther == null) {
						//New row that contains no elements
						if (currentFinalMatrixRow.getFirstElement() == null) {
							ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							currentFinalMatrixRow.incrementNumberOfElements();
							//Locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no columns, or the right column is not within the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse through list of elements within the column
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
							}
						}
						//The row is not empty
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							//Locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no columns, or the right column is not within the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse through list of elements within the list
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
							}
						}
					}
					//The element in the other row has a lower column index so it is placed first in the matrix
					else if (currentElementInOriginal.getColumnIndex() > currentElementInOther.getColumnIndex()) {
						//New row that contains no elements
						if (currentFinalMatrixRow.getFirstElement() == null) {
							ElementNode newElement = new ElementNode (currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							currentFinalMatrixRowElement = newElement;
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRow.incrementNumberOfElements();
							//locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no column, or the right column is not in the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse through list of elements in the column
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
						//The row is not empty
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							ElementNode newElement = new ElementNode (currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							//Locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no column, or the right column is not in the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							//The columnis found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse through list of elements in the column
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
					}
					//Element in the orginal row has a lower column index, so it is placed in the matrix first
					else if (currentElementInOriginal.getColumnIndex() < currentElementInOther.getColumnIndex())  {
						//New row that contains no elements
						if (currentFinalMatrixRow.getFirstElement() == null) {
							ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							currentFinalMatrixRow.incrementNumberOfElements();
							//Locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no column, or the right column is not in the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse list of elements within the list
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
							}
						}
						//The row is not empty
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							//Locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no columns, or the right column is not within the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);finalMatrix.incrementNumberOfColumns();
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse list of elements within the column
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
							}
						}
					}
					//The elements in both rows have the same column value, so we add their values are store it in the matrix
					else if (currentElementInOriginal.getColumnIndex() == currentElementInOther.getColumnIndex()) {
						//New row that contains no elements
						if (currentFinalMatrixRow.getFirstElement() == null) {
							int sum = currentElementInOriginal.getElement() + currentElementInOther.getElement();
							//The matrix can only contain non zero elements
							if (sum == 0) {
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
								continue;
							}
							ElementNode newElement = new ElementNode (sum, currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							currentFinalMatrixRowElement = newElement;
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRow.incrementNumberOfElements();
							//Locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no columns, or the right column is not in the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//New column to be placed  before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									finalMatrix.incrementNumberOfColumns();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								//New column to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse through the elements of that column
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
						//The row contains elements
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							int sum = currentElementInOriginal.getElement() + currentElementInOther.getElement();
							//Matrix can only contain non zero elements
							if (sum == 0) {
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
								continue;
							}
							ElementNode newElement = new ElementNode (sum, currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							//Locate right column
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							//Either there exists no column, or the right column is not in the list
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								//The new column is to be placed before the head
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									finalMatrix.incrementNumberOfColumns();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								//The new column is to be placed after the head
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							//The column is found
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								//Traverse through list of elements within the column
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
					}
				}
				//We have gone through both rows. The final row contains no elements so we go back to previous row
				if (currentFinalMatrixRow.getFirstElement() == null) {
					if (previousFinalRow == null)
						currentFinalMatrixRow = null;
					else
						currentFinalMatrixRow = previousFinalRow;
				}
				else {
					finalMatrix.incrementNumberOfRows();
				}
				//Both rows have been traversed. Go to the next row for both
				originalMatrixRow = originalMatrixRow.getNextRow();
				otherMatrixRow = otherMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
		}
		return finalMatrix;
	}
	
	/*
		Exactly the same implementation as addition. Small differences are commented on
	*/
	@Override
	public SparseM substraction (SparseM otherM) {
		if (this.nrows() != otherM.nrows() || this.ncols() != otherM.ncols())
			return null;
		if (((LLSparseM) otherM).cnrows() == 0)
			return this;
		RowNode originalMatrixRow = this.rowHead;
		RowNode otherMatrixRow = ((LLSparseM) otherM).rowHead;
		LLSparseM finalMatrix = new LLSparseM(this.nrows(), this.ncols());
		RowNode currentFinalMatrixRow = finalMatrix.rowHead;
		ColumnNode previousFinalMatrixColumn = null;
		ColumnNode currentFinalMatrixColumn = finalMatrix.columnHead;
		while (originalMatrixRow != null || otherMatrixRow != null) {
			if (originalMatrixRow != null && otherMatrixRow == null) {
				RowNode newRow = new RowNode (originalMatrixRow.getRowIndex(), null, null);
				finalMatrix.incrementNumberOfRows();
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentElementInOriginal = originalMatrixRow.getFirstElement();
				while (currentElementInOriginal != null) {
					if (currentFinalMatrixRow.getFirstElement() == null) {
						ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRow.setFirstElement(newElement);
						currentFinalMatrixRowElement = newElement;//new line
						currentFinalMatrixRow.incrementNumberOfElements();
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							//
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					else if (currentFinalMatrixRow.getFirstElement() != null) {
						ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement.setNextElementinRow(newElement);
						currentFinalMatrixRow.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
				}
				originalMatrixRow = originalMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
			else if (originalMatrixRow == null && otherMatrixRow != null) {
				RowNode newRow = new RowNode (otherMatrixRow.getRowIndex(), null, null);
				finalMatrix.incrementNumberOfRows();
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentElementInOther = otherMatrixRow.getFirstElement();
				while (currentElementInOther != null) {
					if (currentFinalMatrixRow.getFirstElement() == null) {
						//It is 0 - due to it being the other row. Like saying 0 - 4 where 4 is the element of the other row element
						ElementNode newElement = new ElementNode (0 - currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
						currentFinalMatrixRow.setFirstElement(newElement);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						currentFinalMatrixRow.incrementNumberOfElements();
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					else if (currentFinalMatrixRow.getFirstElement() != null) {
						//It is 0 - due to it being the other row. Like saying 0 - 4 where 4 is the element of the other row element
						ElementNode newElement = new ElementNode (0 - currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement.setNextElementinRow(newElement);
						currentFinalMatrixRow.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					currentElementInOther = currentElementInOther.getNextElementinRow();
				}
				otherMatrixRow = otherMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
			else if (originalMatrixRow.getRowIndex() < otherMatrixRow.getRowIndex()) {
				RowNode newRow = new RowNode (originalMatrixRow.getRowIndex(), null, null);
				finalMatrix.incrementNumberOfRows();
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentElementInOriginal = originalMatrixRow.getFirstElement();
				while (currentElementInOriginal != null) {
					if (currentFinalMatrixRow.getFirstElement() == null) {
						ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRow.setFirstElement(newElement);
						currentFinalMatrixRowElement = newElement;
						currentFinalMatrixRow.incrementNumberOfElements();
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							//
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					else if (currentFinalMatrixRow.getFirstElement() != null) {
						ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement.setNextElementinRow(newElement);
						currentFinalMatrixRow.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
				}
				originalMatrixRow = originalMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
			else if (otherMatrixRow.getRowIndex() < originalMatrixRow.getRowIndex()) {
				RowNode newRow = new RowNode (otherMatrixRow.getRowIndex(), null, null);
				finalMatrix.incrementNumberOfRows();
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentElementInOther = otherMatrixRow.getFirstElement();
				while (currentElementInOther != null) {
					if (currentFinalMatrixRow.getFirstElement() == null) {
						//It is 0 - due to it being the other row. Like saying 0 - 4 where 4 is the element of the other row element
						ElementNode newElement = new ElementNode (0 - currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
						currentFinalMatrixRow.setFirstElement(newElement);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						currentFinalMatrixRow.incrementNumberOfElements();
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					else if (currentFinalMatrixRow.getFirstElement() != null) {
						//It is 0 - due to it being the other row. Like saying 0 - 4 where 4 is the element of the other row element
						ElementNode newElement = new ElementNode (0 - currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
						finalMatrix.incrementNumberOfElements();
						currentFinalMatrixRowElement.setNextElementinRow(newElement);
						currentFinalMatrixRow.incrementNumberOfElements();
						currentFinalMatrixRowElement = newElement;
						while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
							previousFinalMatrixColumn = currentFinalMatrixColumn;
							currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
						}
						if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
							if (previousFinalMatrixColumn == null) {
								finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								currentFinalMatrixColumn = finalMatrix.columnHead;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
							else {
								ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
								finalMatrix.incrementNumberOfColumns();
								previousFinalMatrixColumn.setNextColumn(newColumn);
								currentFinalMatrixColumn = newColumn;
								currentFinalMatrixColumn.incrementNumberOfElements();
							}
						}
						else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
							currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
							while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
								currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
							currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
							currentFinalMatrixColumn.incrementNumberOfElements();
						}
					}
					currentElementInOther = currentElementInOther.getNextElementinRow();
				}
				otherMatrixRow = otherMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
			else if (originalMatrixRow.getRowIndex() == otherMatrixRow.getRowIndex()) {
				ElementNode currentElementInOther = otherMatrixRow.getFirstElement();
				ElementNode currentElementInOriginal = originalMatrixRow.getFirstElement();
				if (currentElementInOriginal.getColumnIndex() == currentElementInOther.getColumnIndex() && currentElementInOriginal.getElement() - currentElementInOther.getElement() == 0 && currentElementInOriginal.getNextElementinRow() == null && currentElementInOther.getNextElementinRow() == null) {
					originalMatrixRow = originalMatrixRow.getNextRow();
					otherMatrixRow = otherMatrixRow.getNextRow();
					continue;
				}
				RowNode newRow = new RowNode (otherMatrixRow.getRowIndex(), null, null);
				RowNode previousFinalRow = currentFinalMatrixRow;
				if (currentFinalMatrixRow == null)
					finalMatrix.rowHead = newRow;
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				ElementNode currentFinalMatrixColumnElement = null;
				ElementNode currentFinalMatrixRowElement = null;
				while (currentElementInOriginal != null || currentElementInOther != null) {
					if (currentElementInOriginal == null) {
						if (currentFinalMatrixRow.getFirstElement() == null) {
							//It is 0 - due to it being the other row. Like saying 0 - 4 where 4 is the element of the other row element
							ElementNode newElement = new ElementNode (0 - currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							currentFinalMatrixRowElement = newElement;
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRow.incrementNumberOfElements();
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							//It is 0 - due to it being the other row. Like saying 0 - 4 where 4 is the element of the other row element
							ElementNode newElement = new ElementNode (0 - currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
					}
					else if (currentElementInOther == null) {
						if (currentFinalMatrixRow.getFirstElement() == null) {
							ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							currentFinalMatrixRow.incrementNumberOfElements();
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
							}
						}
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
							}
						}
					}
					else if (currentElementInOriginal.getColumnIndex() > currentElementInOther.getColumnIndex()) {
						if (currentFinalMatrixRow.getFirstElement() == null) {
							//It is 0 - due to it being the other row. Like saying 0 - 4 where 4 is the element of the other row element
							ElementNode newElement = new ElementNode (0 - currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							currentFinalMatrixRowElement = newElement;
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRow.incrementNumberOfElements();
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							//It is 0 - due to it being the other row. Like saying 0 - 4 where 4 is the element of the other row element
							ElementNode newElement = new ElementNode (0 - currentElementInOther.getElement(), currentElementInOther.getRowIndex(), currentElementInOther.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
					}
					else if (currentElementInOriginal.getColumnIndex() < currentElementInOther.getColumnIndex())  {
						if (currentFinalMatrixRow.getFirstElement() == null) {
							ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							currentFinalMatrixRow.incrementNumberOfElements();
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
							}
						}
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							ElementNode newElement = new ElementNode (currentElementInOriginal.getElement(), currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);finalMatrix.incrementNumberOfColumns();
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
							}
						}
					}
					else if (currentElementInOriginal.getColumnIndex() == currentElementInOther.getColumnIndex()) {
						if (currentFinalMatrixRow.getFirstElement() == null) {
							int difference = currentElementInOriginal.getElement() - currentElementInOther.getElement();
							if (difference == 0) {
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
								continue;
							}
							ElementNode newElement = new ElementNode (difference, currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							currentFinalMatrixRowElement = newElement;
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRow.incrementNumberOfElements();
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									finalMatrix.incrementNumberOfColumns();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							int difference = currentElementInOriginal.getElement() - currentElementInOther.getElement();
							if (difference == 0) {
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
								continue;
							}
							ElementNode newElement = new ElementNode (difference, currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									finalMatrix.incrementNumberOfColumns();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
					}
				}
				if (currentFinalMatrixRow.getFirstElement() == null) {
					if (previousFinalRow == null)
						currentFinalMatrixRow = null;
					else
						currentFinalMatrixRow = previousFinalRow;
				}
				else {
					finalMatrix.incrementNumberOfRows();
				}
				originalMatrixRow = originalMatrixRow.getNextRow();
				otherMatrixRow = otherMatrixRow.getNextRow();
				previousFinalMatrixColumn = null;
				currentFinalMatrixColumn = finalMatrix.columnHead;
			}
		}
		return finalMatrix;
	}

	/*
		Similar implementation to addition. 
		Easier to do since there is only case where the element is added
		And that is only when both the row and column of the element is the
		same in both matrices
	*/
	@Override
	public SparseM multiplication (SparseM otherM) {
		if (this.nrows() != otherM.nrows() || this.ncols() != otherM.ncols())
			return null;
		if (this.cnrows() == 0)
			return new LLSparseM(0, 0);
		if (((LLSparseM) otherM).cnrows() == 0)
			return new LLSparseM(0, 0);
		RowNode originalMatrixRow = this.rowHead;
		RowNode otherMatrixRow = ((LLSparseM) otherM).rowHead;
		LLSparseM finalMatrix = new LLSparseM(nrows(), ncols());
		RowNode currentFinalMatrixRow = finalMatrix.rowHead;
		ColumnNode previousFinalMatrixColumn = null;
		ColumnNode currentFinalMatrixColumn = finalMatrix.columnHead;
		while (originalMatrixRow != null || otherMatrixRow != null) {
			if (originalMatrixRow == null && otherMatrixRow != null) 
				break;
			if (originalMatrixRow != null && otherMatrixRow == null)
				break;
			if (originalMatrixRow.getRowIndex() < otherMatrixRow.getRowIndex()) {
				originalMatrixRow = originalMatrixRow.getNextRow();
				continue;
			}
			if (originalMatrixRow.getRowIndex() > otherMatrixRow.getRowIndex()) {
				otherMatrixRow = otherMatrixRow.getNextRow();
				continue;
			}
			if (originalMatrixRow.getRowIndex() == otherMatrixRow.getRowIndex()) {
				ElementNode currentElementInOriginal = originalMatrixRow.getFirstElement();
				ElementNode currentElementInOther = otherMatrixRow.getFirstElement();
				ElementNode currentFinalMatrixRowElement = null;
				ElementNode currentFinalMatrixColumnElement = null;
				RowNode newRow = new RowNode (originalMatrixRow.getRowIndex(), null, null);
				RowNode previousFinalRow = currentFinalMatrixRow;
				if (currentFinalMatrixRow == null) {
					finalMatrix.rowHead = newRow;
				}
				else
					currentFinalMatrixRow.setNextRow(newRow);
				currentFinalMatrixRow = newRow;
				while (currentElementInOriginal != null || currentElementInOther != null) {
					if (currentElementInOriginal == null && currentElementInOther != null)
						break;
					else if (currentElementInOriginal != null && currentElementInOther == null)
						break;
					else if (currentElementInOriginal.getColumnIndex() < currentElementInOther.getColumnIndex()) {
						currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
						continue;
					}
					else if (currentElementInOriginal.getColumnIndex() > currentElementInOther.getColumnIndex()) {
						currentElementInOther = currentElementInOther.getNextElementinRow();
						continue;
					}
					else if (currentElementInOriginal.getColumnIndex() == currentElementInOther.getColumnIndex()) {
						if (currentFinalMatrixRow.getFirstElement() == null) {
							int product = currentElementInOriginal.getElement() * currentElementInOther.getElement();
							if (product == 0) {
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
								continue;
							}
							ElementNode newElement = new ElementNode (product, currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							currentFinalMatrixRow.setFirstElement(newElement);
							currentFinalMatrixRowElement = newElement;
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRow.incrementNumberOfElements();
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									finalMatrix.incrementNumberOfColumns();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
						else if (currentFinalMatrixRow.getFirstElement() != null) {
							int product = currentElementInOriginal.getElement() * currentElementInOther.getElement();
							if (product == 0) {
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
								continue;
							}
							ElementNode newElement = new ElementNode (product, currentElementInOriginal.getRowIndex(), currentElementInOriginal.getColumnIndex(), null, null);
							finalMatrix.incrementNumberOfElements();
							currentFinalMatrixRowElement.setNextElementinRow(newElement);
							currentFinalMatrixRow.incrementNumberOfElements();
							currentFinalMatrixRowElement = newElement;
							while (currentFinalMatrixColumn != null && currentFinalMatrixColumn.getColumnIndex() < newElement.getColumnIndex()) {
								previousFinalMatrixColumn = currentFinalMatrixColumn;
								currentFinalMatrixColumn = currentFinalMatrixColumn.getNextColumn();
							}
							if (currentFinalMatrixColumn == null || currentFinalMatrixColumn.getColumnIndex() != newElement.getColumnIndex()) {
								if (previousFinalMatrixColumn == null) {
									finalMatrix.columnHead = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									currentFinalMatrixColumn = finalMatrix.columnHead;
									currentFinalMatrixColumn.incrementNumberOfElements();
									finalMatrix.incrementNumberOfColumns();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
								else {
									ColumnNode newColumn = new ColumnNode(newElement.getColumnIndex(), currentFinalMatrixColumn, newElement);
									finalMatrix.incrementNumberOfColumns();
									previousFinalMatrixColumn.setNextColumn(newColumn);
									currentFinalMatrixColumn = newColumn;
									currentFinalMatrixColumn.incrementNumberOfElements();
									currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
									currentElementInOther = currentElementInOther.getNextElementinRow();
								}
							}
							else if (currentFinalMatrixColumn.getColumnIndex() == newElement.getColumnIndex()) {
								currentFinalMatrixColumnElement = currentFinalMatrixColumn.getFirstElement();
								while (currentFinalMatrixColumnElement.getNextElementinColumn() != null)
									currentFinalMatrixColumnElement = currentFinalMatrixColumnElement.getNextElementinColumn();
								currentFinalMatrixColumnElement.setNextElementinColumn(newElement);
								currentFinalMatrixColumn.incrementNumberOfElements();
								currentElementInOriginal = currentElementInOriginal.getNextElementinRow();
								currentElementInOther = currentElementInOther.getNextElementinRow();
							}
						}
					}
				}
				if (currentFinalMatrixRow.getFirstElement() == null) {
					if (previousFinalRow == null)
						currentFinalMatrixRow = null;
					else
						currentFinalMatrixRow = previousFinalRow;
				}
				else {
					finalMatrix.incrementNumberOfRows();
				}
				originalMatrixRow = originalMatrixRow.getNextRow();
				otherMatrixRow = otherMatrixRow.getNextRow();
			}
		}
		return finalMatrix;		
	}
}