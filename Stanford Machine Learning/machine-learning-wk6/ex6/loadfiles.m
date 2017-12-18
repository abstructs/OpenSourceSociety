function [X, y] = loadfiles(amount)
    % returns X and y matricies with features and their outputs 
    non_spam_files = glob('./email_training/easy_ham/*');
    spam_files = glob('./email_training/spam_2/*');
    n = length(non_spam_files);

    % 1899 is the amount of words we're keeping track of
    X = ones(1, 1899);
    % IMPORTANT: set the first param of ones() to the # of examples you plan to proccess
    y = -1 * ones(amount, 1);

    % Load up non-spam examples
    iter = 1;
    for i=1:(amount / 2)
        [file_path, filename, ext] = fileparts(non_spam_files{i});
        file = strcat(file_path, "/", filename, ext);
        
        file_content = readFile(file);
        fprintf("Processing Non-Spam Email...");
        word_indices = processEmail(file_content);
        fprintf("Email Processed, finding features...");
        X(i, :) = emailFeatures(word_indices);
        % IMPORTANT: set the y values to 0 for these
        y(i) = 0;
        fprintf("Done!");
        iter += 1;
    endfor

    % pick up where the last loop left off
    for i=iter:iter + (amount / 2) - 1
        [file_path, filename, ext] = fileparts(spam_files{i});
        file = strcat(file_path, "/", filename, ext);
        
        file_content = readFile(file);
        fprintf("Processing Spam Email...");
        word_indices = processEmail(file_content);
        fprintf("Email Processed, finding features...");
        X(i, :) = emailFeatures(word_indices);
        % IMPORTANT: set the y values to 1 for these
        y(i) = 1;
        fprintf("Done!");
    endfor
return;