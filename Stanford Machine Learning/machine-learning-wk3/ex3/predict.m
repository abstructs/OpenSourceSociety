function p = predict(Theta1, Theta2, X)
%PREDICT Predict the label of an input given a trained neural network
%   p = PREDICT(Theta1, Theta2, X) outputs the predicted label of X given the
%   trained weights of a neural network (Theta1, Theta2)

% Useful values
m = size(X, 1);
num_labels = size(Theta2, 1);

% You need to return the following variables correctly 
p = zeros(size(X, 1), 1);

% ====================== YOUR CODE HERE ======================
% Instructions: Complete the following code to make predictions using
%               your learned neural network. You should set p to a 
%               vector containing labels between 1 to num_labels.
%
% Hint: The max function might come in useful. In particular, the max
%       function can also return the index of the max element, for more
%       information see 'help max'. If your examples are in rows, then, you
%       can use max(A, [], 2) to obtain the max for each row.
%

% get the ones column to add to layer one's first columns
% note: the results in X are stored in rows
layer_1_ones = ones(size(X)(1))(:,1);
layer_1_activation = sigmoid([layer_1_ones X] * Theta1');
% the results in layer_one_activation are stored in rows
layer_2_ones = ones(size(layer_1_activation)(1))(:,1);
layer_2_activation = sigmoid([layer_2_ones layer_1_activation] * Theta2');
%get the index of the highest value in each row
[hyp ind] = max(layer_2_activation, [], 2);

p = ind;



% =========================================================================


end
