CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS expenses (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        description VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    date DATETIME NOT NULL,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
    );

CREATE TABLE IF NOT EXISTS budgets (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       budget_year_month VARCHAR(7) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    user_id BIGINT NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id),
    UNIQUE (user_id, budget_year_month, category_id)
    );

-- Insert default categories
INSERT INTO categories (name, description) VALUES
                                               ('Food', 'Groceries and dining out'),
                                               ('Transportation', 'Gas, public transport, and car maintenance'),
                                               ('Housing', 'Rent, mortgage, and utilities'),
                                               ('Entertainment', 'Movies, events, and hobbies'),
                                               ('Shopping', 'Clothing and personal items'),
                                               ('Healthcare', 'Medical expenses and insurance'),
                                               ('Education', 'Tuition and learning materials'),
                                               ('Travel', 'Vacations and business trips'),
                                               ('Other', 'Miscellaneous expenses');