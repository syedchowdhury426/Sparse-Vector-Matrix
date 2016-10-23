# Sparse-Vector-Matrix
This is a project to create a Sparse Vector and a Sparse Matrix. 
A Sparse vector is a one dimensional collection of non zero elements. 
A Sparse matrix is a two dimensional collection of non zero elements.

Why use this instead of an array?<br />
If most of the elements of an array are zeroes, your array is sparse. By transforming it into either a vector or a matrix, you have much faster access to the non zero elements.

What can I use this project to do?<br />
You can create sparse vectors and sparse matrices. You can do arithmetic on multiple vectors or matrices (Addition, Subtraction, Multiplucation) 


How to use this project?<br />
Simply create a text file in the form of:<br />
VECTOR 20<br />
SET 10 1<br />
CLEAR 10<br />
END<br />
Where 20 is the size limit of the vector, 10 is the index in the vector, and 1 is the value. 

Or<br />
MATRIX 10 20<br />
SET 4 5 6<br />
CLEAR 4 5<br />
END<br />
Where 10 is the size limit on rows, 20 is the size limit on columns. 4 is the index of the row, 5 is the index of the column, and 6 is the value.<br />

Format for running the project:<br />
java LLMainClass VEC Data/VecExample1-Input1.txt A Data/VecExample1-Input2.txt<br />
java LLMainClass MAT Data/MatExample1-Input1.txt M Data/MatExample1-Input2.txt<br />
Doing arithmetic is not neccessary you can disregard the 2nd half of the execution line if you want to just view the final object. 
For arithmetic make sure the size limit of both objects are the same. 

Enjoy!
