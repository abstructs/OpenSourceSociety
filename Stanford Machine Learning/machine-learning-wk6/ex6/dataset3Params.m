function [C, sigma] = dataset3Params(X, y, Xval, yval)
%DATASET3PARAMS returns your choice of C and sigma for Part 3 of the exercise
%where you select the optimal (C, sigma) learning parameters to use for SVM
%with RBF kernel
%   [C, sigma] = DATASET3PARAMS(X, y, Xval, yval) returns your choice of C and 
%   sigma. You should complete this function to return the optimal C and 
%   sigma based on a cross-validation set.
%

% You need to return the following variables correctly.
C = 1;
sigma = 0.3;

% ====================== YOUR CODE HERE ======================
% Instructions: Fill in this function to return the optimal C and sigma
%               learning parameters found using the cross validation set.
%               You can use svmPredict to predict the labels on the cross
%               validation set. For example, 
%                   predictions = svmPredict(model, Xval);
%               will return the predictions on the cross validation set.
%
%  Note: You can compute the prediction error using 
%        mean(double(predictions ~= yval))
%

c_vals = [0.01; 0.03; 0.1; 0.3; 1; 3; 10; 30];
sigma_vals = [0.01; 0.03; 0.1; 0.3; 1; 3; 10; 30];

results = ones(length(c_vals) * length(sigma_vals), 3);

iter = 1;
for i=1:length(c_vals)
    for j=1:length(sigma_vals)
        z1 = X(:,1);
        z2 = X(:,2);
        model = svmTrain(X, y, c_vals(i), @(z1, z2) gaussianKernel(z1, z2, sigma_vals(j)));
        predictions = svmPredict(model, Xval);
        
        results(iter,:) = [c_vals(i) sigma_vals(j) mean(double(predictions ~= yval))];
        iter += 1;
    endfor
endfor

[min_pred, row_index] = min(results(:, 3));
C = results(row_index, 1)
sigma = results(row_index, 2)

% =========================================================================

end