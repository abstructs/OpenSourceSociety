function [J, grad] = linearRegCostFunction(X, y, theta, lambda)
%LINEARREGCOSTFUNCTION Compute cost and gradient for regularized linear 
%regression with multiple variables
%   [J, grad] = LINEARREGCOSTFUNCTION(X, y, theta, lambda) computes the 
%   cost of using theta as the parameter for linear regression to fit the 
%   data points in X and y. Returns the cost in J and the gradient in grad

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost and gradient of regularized linear 
%               regression for a particular choice of theta.
%
%               You should set J to the cost and grad to the gradient.
%

for i=1:m
    J = J + (X(i, :) * theta - y(i)) .^ 2;
endfor
J = (1 / (2 * m)) * J;

% size = (n x 2)

% J = J + ((lambda ./ (2 * m)) * sum(theta(2:end,:)' .^ 2, 2));

acc = 0;
for j=2:size(theta)(1)
    acc = acc + theta(j) .^ 2;
endfor

J = J + (lambda / (2 * m)) * acc;


for j=1:size(theta)(1)
    acc = 0;
    for i=1:m
        acc = acc + ((X(i,:) * theta) - y(i)) * X(i, j);
    endfor
    grad(j) = (1 ./ m) * acc;
    if j == 1; 
        continue; 
    endif;
    grad(j) = grad(j) + (lambda ./ m) * theta(j);
endfor


% =========================================================================

grad = grad(:);

end
