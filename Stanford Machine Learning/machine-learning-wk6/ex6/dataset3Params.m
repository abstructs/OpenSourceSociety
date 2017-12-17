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

c_vals =  [0.3; 1; 3; 10; 30];
sigma_vals =  [0.3; 1; 3; 10; 30];

results = ones(length(c_vals) * length(sigma_vals), 3);

iter = 1;
for i=1:length(c_vals)
    for j=1:length(sigma_vals)
        model = svmTrain(Xval, yval, c_vals(i), @(x1, x2) gaussianKernel(Xval(:,1), Xval(:,2), sigma_vals(j)));
        predictions = svmPredict(model, Xval);
        
        results(iter,:) = [c_vals(i) sigma_vals(j) mean(double(predictions ~= yval))];
        iter += 1;
    endfor
endfor

min_result = min(results);
C = min_result(1);
sigma = min_result(2);

% =========================================================================

end